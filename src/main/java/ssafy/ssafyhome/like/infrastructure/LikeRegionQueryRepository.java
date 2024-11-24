package ssafy.ssafyhome.like.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.like.application.response.LikeRegionQueryResponse;
import ssafy.ssafyhome.like.application.response.QLikeRegionQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.like.domain.QLikeRegion.likeRegion;
import static ssafy.ssafyhome.region.domain.QRegion.region;

@RequiredArgsConstructor
@Repository
public class LikeRegionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<LikeRegionQueryResponse> searchAll(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<LikeRegionQueryResponse> likeRegions = queryFactory
                .select(new QLikeRegionQueryResponse(likeRegion.id, region.sido, region.gugun, region.dong))
                .from(likeRegion)
                .join(likeRegion.region, region)
                .where(toEqExpression(likeRegion.member.id, memberId), cursorLtExpression(likeRegion.id, cursorId))
                .orderBy(makeOrderSpecifiers(likeRegion, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(likeRegions);
    }
}
