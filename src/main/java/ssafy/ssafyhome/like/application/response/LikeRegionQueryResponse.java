package ssafy.ssafyhome.like.application.response;

import com.querydsl.core.annotations.QueryProjection;

public record LikeRegionQueryResponse(
        Long likeRegionId,
        String sido,
        String gugun,
        String dong) {

    @QueryProjection
    public LikeRegionQueryResponse {
    }
}
