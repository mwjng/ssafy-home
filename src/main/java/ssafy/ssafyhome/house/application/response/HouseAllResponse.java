package ssafy.ssafyhome.house.application.response;

import ssafy.ssafyhome.deal.application.response.RepresentedDealResponse;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.region.application.response.RegionResponse;
import ssafy.ssafyhome.region.domain.Region;

import java.util.List;

public record HouseAllResponse(
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
        List<String> imageUrl,
        RepresentedDealResponse deal,
        Boolean likeStatus
) {
    public static HouseAllResponse of(House house, Region region, Deal deal, List<String> imageUrl, Boolean likeStatus) {
        return new HouseAllResponse(
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
                RegionResponse.of(region),
                imageUrl,
                RepresentedDealResponse.from(deal),
                likeStatus
        );
    }
}
