package ssafy.ssafyhome.likeregion.application;

import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;
import ssafy.ssafyhome.likeregion.presentation.request.CreateLikeRegionRequest;

public interface LikeRegionService {
    LikeRegionsResponse searchAll(final Long memberId, final int size, final Long cursorId);

    void create(final Long memberId, final CreateLikeRegionRequest createLikeRegionRequest);

    void delete(final Long memberId, final Long likeRegionId);
}
