package ssafy.ssafyhome.house.application.response;

import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;

public record HouseNameResponse(
        Long houseId,
        String name,
        RegionResponse region,
        String road,
        String jibun,
        String bonbun,
        String bubun,
        HouseType type) {

    public static HouseNameResponse from(HouseNameQueryResponse queryResponse){
        return new HouseNameResponse(
                queryResponse.houseId(),
                queryResponse.name(),
                RegionResponse.of(queryResponse.region()),
                queryResponse.road(),
                queryResponse.jibun(),
                queryResponse.bonbun(),
                queryResponse.bubun(),
                queryResponse.type()
        );
    }
}
