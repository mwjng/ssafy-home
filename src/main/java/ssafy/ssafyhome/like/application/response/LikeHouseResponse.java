package ssafy.ssafyhome.like.application.response;

import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;

import java.util.List;

public record LikeHouseResponse(
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
        RegionResponse region,
        List<String> imageUrl) {

    public static LikeHouseResponse from(LikeHouseQueryResponse likeHouse, List<String> imageUrl){
        return new LikeHouseResponse(
                likeHouse.likeHouseId(),
                likeHouse.houseId(),
                likeHouse.name(),
                likeHouse.buildYear(),
                likeHouse.jibun(),
                likeHouse.road(),
                likeHouse.bonbun(),
                likeHouse.bubun(),
                likeHouse.latitude(),
                likeHouse.longitude(),
                likeHouse.houseType(),
                RegionResponse.of(likeHouse.region()),
                imageUrl
        );
    }
}
