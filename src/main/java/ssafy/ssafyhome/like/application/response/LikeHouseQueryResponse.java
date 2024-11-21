package ssafy.ssafyhome.like.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.domain.Region;

public record LikeHouseQueryResponse(
        Long likeHouseId,
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
        Region region,
        String dirName) {

    @QueryProjection
    public LikeHouseQueryResponse {
    }
}
