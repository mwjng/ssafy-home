package ssafy.ssafyhome.like.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;

public record LikeHouseQueryResponse(
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
        String dirName) {

    @QueryProjection
    public LikeHouseQueryResponse {
    }
}
