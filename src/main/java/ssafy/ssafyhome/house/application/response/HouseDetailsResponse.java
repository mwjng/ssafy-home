package ssafy.ssafyhome.house.application.response;

import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.HouseType;

import java.util.List;

public record HouseDetailsResponse(
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
        List<String> imageUrl,
        Boolean likeStatus) {

    public static HouseDetailsResponse of(House house, List<String> imageUrl, Boolean likeStatus){
        return new HouseDetailsResponse(
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
                imageUrl,
                likeStatus
        );
    }
}
