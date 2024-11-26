package ssafy.ssafyhome.like.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.like.application.response.LikeDealQueryResponse;
import ssafy.ssafyhome.like.application.response.QLikeDealQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.deal.domain.QDeal.deal;
import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.like.domain.QLikeDeal.likeDeal;
import static ssafy.ssafyhome.member.domain.QMember.member;
import static ssafy.ssafyhome.region.domain.QRegion.region;

@RequiredArgsConstructor
@Repository
public class LikeDealQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<LikeDealQueryResponse> findLikeDealsByMemberId(final Long memberId, Pageable pageable, Long cursorId) {
        List<LikeDealQueryResponse> likeDeals = queryFactory
                .select(new QLikeDealQueryResponse(deal, likeDeal.id))
                .from(likeDeal)
                .join(likeDeal.deal, deal)
                .join(deal.member, member)
                .join(deal.house, house)
                .join(house.region, region)
                .where(
                        toEqExpression(likeDeal.member.id, memberId),
                        cursorLtExpression(likeDeal.id, cursorId)
                )
                .orderBy(makeOrderSpecifiers(likeDeal, pageable))
                .limit(pageable.getPageSize())
                .fetch();
        return new SliceImpl<>(likeDeals);
    }
}
