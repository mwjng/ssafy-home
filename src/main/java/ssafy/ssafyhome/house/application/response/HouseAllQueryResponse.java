package ssafy.ssafyhome.house.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.region.domain.Region;

public record HouseAllQueryResponse(
        House house,
        Region region,
        Boolean likeStatus
) {
    @QueryProjection
    public HouseAllQueryResponse {
    }
}
