package ssafy.ssafyhome.like.application;

import ssafy.ssafyhome.like.application.response.LikeRegionsResponse;
import ssafy.ssafyhome.like.presentation.request.LikeRegionCreateRequest;

public interface LikeRegionService {
    LikeRegionsResponse searchAll(final Long memberId, final int size, final Long cursorId);

    void create(final Long memberId, final LikeRegionCreateRequest likeRegionCreateRequest);

    void delete(final Long memberId, final Long likeRegionId);
}
