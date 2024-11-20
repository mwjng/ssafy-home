package ssafy.ssafyhome.likeregion.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionQueryResponse;

public interface LikeRegionRepositoryCustom {

    Slice<LikeRegionQueryResponse> searchAll(final Long memberId, final Pageable pageable, final Long cursorId);
}
