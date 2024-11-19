package ssafy.ssafyhome.house.presentation.request;

import ssafy.ssafyhome.house.domain.HouseType;

public record HouseCreateRequest(
        String name,
        String jibum,
        Long buildYear,
        String road,
        String bonbun,
        String bubun,
        String latitude,
        String longitude,
        HouseType houseType,
        Long regionId
) {
}
