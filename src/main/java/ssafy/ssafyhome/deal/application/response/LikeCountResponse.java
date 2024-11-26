package ssafy.ssafyhome.deal.application.response;

import com.querydsl.core.annotations.QueryProjection;

public record LikeCountResponse(
        Long dealId,
        Long count) {

    @QueryProjection
    public LikeCountResponse {
    }
}
