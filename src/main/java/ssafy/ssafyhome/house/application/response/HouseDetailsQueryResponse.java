package ssafy.ssafyhome.house.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.house.domain.House;

public record HouseDetailsQueryResponse(
        House house,
        Boolean likeStatus) {

    @QueryProjection
    public HouseDetailsQueryResponse {
    }
}
