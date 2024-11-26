package ssafy.ssafyhome.deal.infrastructure;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.deal.application.request.*;
import ssafy.ssafyhome.deal.application.response.*;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.asBoolean;
import static java.time.LocalDate.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.deal.domain.DealStatus.*;
import static ssafy.ssafyhome.deal.domain.QDeal.deal;
import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.like.domain.QLikeDeal.likeDeal;
import static ssafy.ssafyhome.member.domain.QMember.member;
import static ssafy.ssafyhome.region.domain.QRegion.region;


@RequiredArgsConstructor
@Repository
public class DealQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<DealQueryResponse> findDeals(Long houseId, Long memberId, DealCondition condition, Pageable pageable, Long cursorId) {
        List<DealQueryResponse> deals = createBaseQuery(memberId)
                .select(new QDealQueryResponse(deal, getLikeStatus(memberId)))
                .where(getWhereClause(houseId, condition, cursorId))
                .orderBy(getOrderSpecifier(condition))
                .limit(pageable.getPageSize())
                .fetch();
        return new SliceImpl<>(deals);
    }

    public DealQueryResponse findDeal(final Long dealId, final Long memberId){
        return createBaseQuery(memberId)
                .select(new QDealQueryResponse(deal, getLikeStatus(memberId)))
                .where(idEq(dealId))
                .fetchOne();
    }

    private JPAQuery<?> createBaseQuery(Long memberId) {
        JPAQuery<?> query = queryFactory.from(deal).join(deal.member).fetchJoin();
        if (memberId != null) {
            query.leftJoin(likeDeal).on(likeDeal.deal.eq(deal).and(likeDeal.member.id.eq(memberId)));
        }
        return query;
    }

    public List<LikeCountResponse> getCountsByDealId(List<Long> dealsId) {
        return queryFactory
                .select(new QLikeCountResponse(
                        likeDeal.deal.id,
                        likeDeal.count()))
                .from(likeDeal)
                .where(likeDeal.deal.id.in(dealsId))
                .groupBy(likeDeal.deal.id)
                .fetch();
    }

    public LikeCountResponse getCountByDealId(Long dealId) {
        return queryFactory
                .select(new QLikeCountResponse(
                        likeDeal.deal.id,
                        likeDeal.id.count()))
                .from(likeDeal)
                .where(likeDeal.deal.id.eq(dealId))
                .groupBy(likeDeal.deal.id)
                .fetchOne();
    }

    public Slice<Deal> findByMemberId(final Long memberId, Pageable pageable, Long cursorId) {
        List<Deal> deals = queryFactory
                .selectFrom(deal)
                .join(deal.member, member).fetchJoin()
                .join(deal.house, house).fetchJoin()
                .join(house.region, region).fetchJoin()
                .where(
                        toEqExpression(deal.member.id, memberId),
                        cursorLtExpression(deal.id, cursorId)
                )
                .orderBy(makeOrderSpecifiers(deal, pageable))
                .limit(pageable.getPageSize())
                .fetch();
        return new SliceImpl<>(deals);
    }

    public List<AverageQueryResponse> getAverageByHouseId(final Long houseId) {
        return queryFactory
                .select(new QAverageQueryResponse(
                        deal.exclusiveArea,
                        deal.type,
                        deal.price.avg()
                ))
                .from(deal)
                .where(
                        houseIdEq(houseId),
                        statusEq(COMPLETED),
                        dealDateLt(now(), 5L)
                )
                .groupBy(deal.exclusiveArea, deal.type)
                .fetch();
    }

    private BooleanExpression getWhereClause(final Long houseId, final DealCondition condition, final Long cursorId) {
        BooleanExpression whereClause = houseIdEq(houseId).and(cursorLtExpression(deal.id, cursorId));

        if (condition != null) {
            if (condition.getExclusiveAreaRange() != null) {
                whereClause = whereClause.and(betweenExclusiveAreaRange(condition.getExclusiveAreaRange()));
            }
            if (condition.getDepositRange() != null) {
                whereClause = whereClause.and(betweenDepositRange(condition.getDepositRange()));
            }
            if (condition.getPriceRange() != null) {
                whereClause = whereClause.and(betweenPriceRange(condition.getPriceRange()));
            }
            if (condition.getDealStatus() != null) {
                whereClause = whereClause.and(statusEq(condition.getDealStatus()));
            }
            if (condition.getDealType() != null) {
                whereClause = whereClause.and(typeEq(condition.getDealType()));
            }
        }
        return whereClause;
    }

    private OrderSpecifier<?> getOrderSpecifier(DealCondition dealCondition) {
        if (dealCondition == null) {
            return deal.id.desc();
        }

        DealSortCondition sortCondition = dealCondition.getDealSortCondition();

        if (sortCondition == null) {
            return deal.id.desc();
        }

        switch (sortCondition) {
            case DEAL_DATE_NEWEST_FIRST:
                return deal.dealDate.desc();
            case NEWEST_FIRST:
                return deal.createdAt.desc();
            case OLDEST_FIRST:
                return deal.createdAt.asc();
            case HIGHEST_DEPOSIT:
                return deal.deposit.desc();
            case LOWEST_DEPOSIT:
                return deal.deposit.asc();
            case HIGHEST_PRICE:
                return deal.price.desc();
            case LOWEST_PRICE:
                return deal.price.asc();
            case HIGHEST_FLOOR:
                return deal.floor.desc();
            case LOWEST_FLOOR:
                return deal.floor.asc();
            case LARGEST_AREA:
                return deal.exclusiveArea.desc();
            case SMALLEST_AREA:
                return deal.exclusiveArea.asc();
            default:
                return deal.id.desc();
        }
    }

    private BooleanExpression getLikeStatus(final Long memberId) {
        return memberId != null ? likeDeal.id.isNotNull() : asBoolean(false);
    }

    private BooleanExpression betweenExclusiveAreaRange(ExclusiveAreaRange exclusiveArea) {
        return exclusiveArea != null ? deal.exclusiveArea.between(exclusiveArea.min(), exclusiveArea.max()) : null;
    }

    private BooleanExpression betweenDepositRange(DepositRange deposit) {
        return deposit != null ? deal.deposit.between(deposit.min(), deposit.max()) : null;
    }

    private BooleanExpression betweenPriceRange(PriceRange price) {
        return price != null ? deal.price.between(price.min(), price.max()) : null;
    }

    private BooleanExpression houseIdEq(Long id) {
        return id != null ? deal.house.id.eq(id) : null;
    }

    private BooleanExpression idEq(Long id) {
        return id != null ? deal.id.eq(id) : null;
    }

    private BooleanExpression statusEq(DealStatus status) {
        return status != null ? deal.status.eq(status) : null;
    }

    private BooleanExpression typeEq(DealType type) {
        return type != null ? deal.type.eq(type) : null;
    }

    private BooleanExpression dealDateLt(final LocalDate date, final Long year) {
        return date != null ?
                year != null ? deal.dealDate.after(date.minusYears(year).atStartOfDay()) : deal.dealDate.after(date.atStartOfDay()) : null;
    }
}
