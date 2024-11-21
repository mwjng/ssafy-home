package ssafy.ssafyhome.house.application.request;

import ssafy.ssafyhome.house.domain.HouseType;

public record HouseSearchCondition(
    String sido,
    String gugun,
    String dong,
    HouseType type
) {
}
