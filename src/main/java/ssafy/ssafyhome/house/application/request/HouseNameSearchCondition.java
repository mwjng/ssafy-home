package ssafy.ssafyhome.house.application.request;

import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.house.presentation.request.SearchHouseNameRequest;

import java.util.List;

public record HouseNameSearchCondition(
        String name,
        List<HouseType> types) {
    public static HouseNameSearchCondition from(SearchHouseNameRequest request){
        return new HouseNameSearchCondition(
                request.name(),
                getTypes(request)
        );
    }

    private static List<HouseType> getTypes(final SearchHouseNameRequest request) {
        List<String> types = request.types();
        return types != null ? types.stream().map(HouseType::getHouseType).toList() : null;
    }
}
