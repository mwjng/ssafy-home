package ssafy.ssafyhome.house.presentation.request;

import jakarta.validation.constraints.NotBlank;
import ssafy.ssafyhome.house.application.request.HouseSearchCondition;
import ssafy.ssafyhome.house.domain.HouseType;

import java.util.List;
import java.util.Objects;

public record HouseSearchRequest(
    @NotBlank(message = "시를 입력해주세요.")
    String sido,

    String gugun,

    String dong,

    List<String> houseType
) {
    public HouseSearchCondition toHouseSearchCondition() {
        if(houseType == null || houseType.isEmpty()) {
            return new HouseSearchCondition(sido, gugun, dong, null);
        }
        return new HouseSearchCondition(sido, gugun, dong, getHouseType());
    }

    private List<HouseType> getHouseType() {
        return houseType.stream()
            .map(HouseType::getHouseType)
            .filter(Objects::nonNull)
            .toList();
    }
}
