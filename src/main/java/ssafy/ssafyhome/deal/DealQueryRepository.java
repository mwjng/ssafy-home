package ssafy.ssafyhome.deal;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.deal.application.request.DealCondition;
import ssafy.ssafyhome.deal.application.response.DealQueryResponse;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.like.application.response.LikeDealQueryResponse;
import ssafy.ssafyhome.like.application.response.QLikeDealQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.deal.domain.QDeal.deal;
import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.like.domain.QLikeDeal.*;
import static ssafy.ssafyhome.member.domain.QMember.*;
import static ssafy.ssafyhome.region.domain.QRegion.region;

@RequiredArgsConstructor
@Repository
public class DealQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<DealQueryResponse> findDeals(DealCondition condition, Pageable pageable) {
//        queryFactory.select()
        return null;
    }

    public List<Deal> findByMemberId(final Long memberId, Pageable pageable, Long cursorId) {
        return queryFactory
                .selectFrom(deal)
                .join(deal.member, member).fetchJoin()
                .join(deal.house, house).fetchJoin()
                .join(house.region, region).fetchJoin()
                .where(toEqExpression(deal.member.id, memberId), cursorLtExpression(deal.id, cursorId))
                .orderBy(makeOrderSpecifiers(deal, pageable))
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<LikeDealQueryResponse> findLikeDealsByMemberId(final Long memberId, Pageable pageable, Long cursorId) {
        return queryFactory
                .select(new QLikeDealQueryResponse(deal, likeDeal.id))
                .from(likeDeal)
                .join(likeDeal.deal, deal).fetchJoin()
                .join(deal.member, member).fetchJoin()
                .join(deal.house, house).fetchJoin()
                .join(house.region, region).fetchJoin()
                .where(toEqExpression(likeDeal.member.id, memberId), cursorLtExpression(likeDeal.id, cursorId))
                .orderBy(makeOrderSpecifiers(likeDeal, pageable))
                .limit(pageable.getPageSize())
                .fetch();
    }

}
