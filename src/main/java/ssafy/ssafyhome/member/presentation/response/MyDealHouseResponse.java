package ssafy.ssafyhome.member.presentation.response;

import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;

public record MyDealHouseResponse(
    Long houseId,
    String name,
    Long buildYear,
    String jibun,
    String road,
    String bonbun,
    String bubun,
    String latitude,
    String longitude,
    HouseType houseType,
    RegionResponse region
) {
    public static MyDealHouseResponse of(final House house) {
        return new MyDealHouseResponse(
            house.getId(),
            house.getName(),
            house.getBuildYear(),
            house.getJibun(),
            house.getRoad(),
            house.getBonbun(),
            house.getBubun(),
            house.getLatitude(),
            house.getLongitude(),
            house.getType(),
            RegionResponse.of(house.getRegion())
        );
    }
}
