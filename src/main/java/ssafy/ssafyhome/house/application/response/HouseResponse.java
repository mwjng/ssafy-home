package ssafy.ssafyhome.house.application.response;

import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;

import java.util.List;

public record HouseResponse(
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
        RegionResponse region,
        List<String> imageUrl
) {
    public static HouseResponse of(House house, List<String> imageUrl) {
        return new HouseResponse(
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
            RegionResponse.of(house.getRegion()),
            imageUrl
        );
    }
}
