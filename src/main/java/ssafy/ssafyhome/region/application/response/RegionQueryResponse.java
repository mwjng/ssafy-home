package ssafy.ssafyhome.region.application.response;

import com.querydsl.core.annotations.QueryProjection;

public record RegionQueryResponse(
        String sido,
        String gugun,
        String dong) {

    @QueryProjection
    public RegionQueryResponse {
    }
}
