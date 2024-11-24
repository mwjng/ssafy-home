package ssafy.ssafyhome.house.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.house.application.request.HouseSearchCondition;
import ssafy.ssafyhome.house.application.response.HouseAllQueryResponse;
import ssafy.ssafyhome.house.application.response.HouseDetailsQueryResponse;
import ssafy.ssafyhome.house.application.response.QHouseAllQueryResponse;
import ssafy.ssafyhome.house.application.response.QHouseDetailsQueryResponse;
import ssafy.ssafyhome.house.domain.HouseType;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.asBoolean;
import static ssafy.ssafyhome.deal.domain.QDeal.*;
import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.like.domain.QLikeHouse.*;
import static ssafy.ssafyhome.region.domain.QRegion.region;

@RequiredArgsConstructor
@Repository
public class HouseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<HouseAllQueryResponse> findAllWithLikeStatus(
        final Long memberId,
        final HouseSearchCondition condition,
        final Pageable pageable
    ) {
        return createBaseQuery(memberId)
            .select(new QHouseAllQueryResponse(
                house,
                region,
                getLikeStatus(memberId)))
            .where(
                sidoEq(condition.sido()),
                gugunEq(condition.gugun()),
                dongEq(condition.dong()),
                typeEq(condition.type())
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    public List<Deal> findLatestDealsByHouseIds(final List<Long> houseIds) {
        return queryFactory
                .selectFrom(deal)
                .where(
                        deal.house.id.in(houseIds),
                        deal.status.eq(DealStatus.COMPLETED)
                )
                .orderBy(deal.house.id.asc(), deal.dealDate.desc())
                .distinct()
                .fetch();
    }

    public HouseDetailsQueryResponse findOne(Long memberId, Long houseId) {
        return createBaseQuery(memberId)
                .select(new QHouseDetailsQueryResponse(house, getLikeStatus(memberId)))
                .from(house).leftJoin(house.region)
                .where(idEq(houseId))
                .fetchOne();
    }

    private JPAQuery<?> createBaseQuery(Long memberId) {
        JPAQuery<?> query = queryFactory.from(house).leftJoin(house.region);
        if (memberId != null) {
            query.leftJoin(likeHouse)
                    .on(likeHouse.house.eq(house).and(likeHouse.member.id.eq(memberId)));
        }
        return query;
    }

    private BooleanExpression getLikeStatus(final Long memberId) {
        return memberId != null ? likeHouse.id.isNotNull() : asBoolean(false);
    }

    private BooleanExpression idEq(final Long houseId) {
        return houseId != null ? house.id.eq(houseId) : null;
    }

    private BooleanExpression sidoEq(final String sido) {
        return sido != null ? region.sido.eq(sido) : null;
    }

    private BooleanExpression gugunEq(final String gugun) {
        return gugun != null ? region.gugun.eq(gugun) : null;
    }

    private BooleanExpression dongEq(final String dong) {
        return dong != null ? region.dong.eq(dong) : null;
    }

    private BooleanExpression typeEq(final List<HouseType> types) {
        return (types != null && !types.isEmpty()) ? house.type.in(types) : null;
    }

}