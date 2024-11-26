package ssafy.ssafyhome.house.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionQueryResponse;

import java.time.LocalDateTime;

public record HouseQueryResponse(
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
        RegionQueryResponse region,
        String dirName,
        LocalDateTime createAt,
        LocalDateTime modifiedAt) {

    @QueryProjection
    public HouseQueryResponse {
    }
}
