package ssafy.ssafyhome.house.presentation.request;

import jakarta.validation.constraints.NotBlank;
import ssafy.ssafyhome.house.application.request.HouseNameSearchCondition;
import ssafy.ssafyhome.house.domain.HouseType;

import java.util.List;
import java.util.Objects;

public record SearchHouseNameRequest(
        @NotBlank(message = "house 이름을 입력해주세요.")
        String name,
        String sido,
        String gugun,
        String dong,
        List<String> types) {

    public HouseNameSearchCondition toHouseNameSearchCondition() {
        if (types == null || types.isEmpty()) {
            return new HouseNameSearchCondition(name, sido, gugun, dong, null);
        }
        return new HouseNameSearchCondition(name, sido, gugun, dong, getHouseType());
    }

    private List<HouseType> getHouseType() {
        return types.stream()
                .map(HouseType::getHouseType)
                .filter(Objects::nonNull)
                .toList();
    }
}
