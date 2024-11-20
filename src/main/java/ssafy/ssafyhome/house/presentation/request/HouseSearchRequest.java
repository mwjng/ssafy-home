package ssafy.ssafyhome.house.presentation.request;

import ssafy.ssafyhome.house.domain.HouseType;

public record HouseSearchRequest(
    String sido,
    String gugun,
    String dong,
    HouseType type
) {
}
