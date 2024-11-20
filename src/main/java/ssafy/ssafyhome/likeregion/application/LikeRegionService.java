package ssafy.ssafyhome.likeregion.application;

import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;

public interface LikeRegionService {
    LikeRegionsResponse searchAll(final Long memberId, final int size, final Long cursorId);

    void create(final Long memberId, final String sido, final String gugun, String dong);

    void delete(final Long likeRegionId);
}
