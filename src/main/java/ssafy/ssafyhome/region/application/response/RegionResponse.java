package ssafy.ssafyhome.region.application.response;

import ssafy.ssafyhome.region.domain.Region;

public record RegionResponse(
    String sido,
    String gugun,
    String dong
) {
    public static RegionResponse of(Region region) {
        return new RegionResponse(
            region.getSido(),
            region.getGugun(),
            region.getDong()
        );
    }

    public static RegionResponse from (RegionQueryResponse region){
        return new RegionResponse(
                region.sido(),
                region.gugun(),
                region.dong()
        );
    }
}
