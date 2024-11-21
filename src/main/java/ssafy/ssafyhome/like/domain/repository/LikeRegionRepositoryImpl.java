package ssafy.ssafyhome.like.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ssafy.ssafyhome.like.application.response.LikeRegionQueryResponse;
import ssafy.ssafyhome.likeregion.application.response.QLikeRegionQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.likeregion.domain.QLikeRegion.*;
import static ssafy.ssafyhome.region.domain.QRegion.*;

@RequiredArgsConstructor
public class LikeRegionRepositoryImpl implements LikeRegionRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<LikeRegionQueryResponse> searchAll(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<LikeRegionQueryResponse> likeRegions = queryFactory
                .select(new QLikeRegionQueryResponse(likeRegion.id, region.sido, region.gugun, region.dong))
                .from(likeRegion.region, region).fetchJoin()
                .where(toEqExpression(likeRegion.member.id, memberId), cursorLtExpression(likeRegion.id, cursorId))
                .orderBy(makeOrderSpecifiers(likeRegion, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(likeRegions);
    }
}
