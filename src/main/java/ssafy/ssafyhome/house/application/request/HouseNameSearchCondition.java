package ssafy.ssafyhome.house.application.request;

import ssafy.ssafyhome.house.domain.HouseType;
import ssafy.ssafyhome.house.presentation.request.SearchHouseNameRequest;

import java.util.List;

public record HouseNameSearchCondition(
        String name,
        List<HouseType> types) {
}
