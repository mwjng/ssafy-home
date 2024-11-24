package ssafy.ssafyhome.deal.infrastructure;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.deal.application.request.*;
import ssafy.ssafyhome.deal.application.response.DealQueryResponse;
import ssafy.ssafyhome.deal.application.response.LikeCountResponse;
import ssafy.ssafyhome.deal.application.response.QDealQueryResponse;
import ssafy.ssafyhome.deal.application.response.QLikeCountResponse;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.*;
import static com.querydsl.core.types.dsl.Expressions.FALSE;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.deal.domain.QDeal.deal;
import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.like.domain.QLikeDeal.likeDeal;
import static ssafy.ssafyhome.member.domain.QMember.member;
import static ssafy.ssafyhome.region.domain.QRegion.region;


@RequiredArgsConstructor
@Repository
public class DealQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<DealQueryResponse> findDeals(Long houseId, DealCondition condition, Pageable pageable, Long cursorId) {
        List<DealQueryResponse> deals = queryFactory
                .select(new QDealQueryResponse(deal, FALSE))
                .from(deal)
                .join(deal.member, member).fetchJoin()
                .where(getWhereClause(houseId, condition, cursorId))
                .orderBy(getOrderSpecifier(condition))
                .limit(pageable.getPageSize())
                .fetch();
        return new SliceImpl<>(deals);
    }

    public Slice<DealQueryResponse> findDealsOnLogin(Long houseId, Long memberId, DealCondition condition, Pageable pageable, Long cursorId) {
        List<DealQueryResponse> deals = queryFactory
                .select(new QDealQueryResponse(deal, likeDeal.id.isNotNull()))
                .from(deal)
                .join(deal.member, member).fetchJoin()
                .leftJoin(likeDeal).on(likeDeal.deal.eq(deal).and(likeDeal.member.id.eq(memberId)))
                .where(getWhereClause(houseId, condition, cursorId))
                .orderBy(getOrderSpecifier(condition))
                .limit(pageable.getPageSize())
                .fetch();
        return new SliceImpl<>(deals);
    }

    public Slice<LikeCountResponse> getCountByDealId(List<Long> dealsId) {
        List<LikeCountResponse> likeCounts = queryFactory
                .select(new QLikeCountResponse(likeDeal.deal.id, likeDeal.count()))
                .from(likeDeal)
                .where(likeDeal.deal.id.in(dealsId))
                .groupBy(likeDeal.deal.id)
                .fetch();
        return new SliceImpl<>(likeCounts);
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

    private BooleanExpression getLikeStatus() {
        return asBoolean(queryFactory
                .selectOne()
                .from(likeDeal)
                .where(likeDeal.deal.eq(deal))
                .fetchFirst() != null);
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

    private BooleanExpression statusEq(DealStatus status) {
        return status != null ? deal.status.eq(status) : null;
    }

    private BooleanExpression typeEq(DealType type) {
        return type != null ? deal.type.eq(type) : null;
    }
}
