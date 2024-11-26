package ssafy.ssafyhome.house.application.request;

import ssafy.ssafyhome.house.domain.HouseType;

import java.util.List;

public record HouseNameSearchCondition(
        String name,
        String sido,
        String gugun,
        String dong,
        List<HouseType> types) {
}
