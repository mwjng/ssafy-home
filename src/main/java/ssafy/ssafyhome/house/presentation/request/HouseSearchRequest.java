package ssafy.ssafyhome.house.presentation.request;

import jakarta.validation.constraints.NotBlank;
import ssafy.ssafyhome.house.domain.HouseType;

public record HouseSearchRequest(
    @NotBlank(message = "시를 입력해주세요.")
    String sido,

    @NotBlank(message = "구를 입력해주세요.")
    String gugun,

    @NotBlank(message = "동을 입력해주세요.")
    String dong,

    String type
) {
    public HouseType getType() {
        return HouseType.getHouseType(type);
    }
}
