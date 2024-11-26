package ssafy.ssafyhome.house.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;
import ssafy.ssafyhome.region.domain.Region;

public record HouseNameQueryResponse(
        Long houseId,
        String name,
        Region region,
        String road,
        String jibun,
        String bonbun,
        String bubun,
        HouseType type) {

    @QueryProjection
    public HouseNameQueryResponse {
    }
}
