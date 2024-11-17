package ssafy.ssafyhome.house.application.response;

import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;

public record HouseResponse(
        Long houseId,
        String name,
        String jibun,
        Long buildYear,
        String road,
        String bonbun,
        String bubun,
        String latitude,
        String longitude,
        HouseType houseType,
        RegionResponse region) {

}
