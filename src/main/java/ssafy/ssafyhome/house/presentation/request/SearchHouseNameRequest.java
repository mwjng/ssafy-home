package ssafy.ssafyhome.house.presentation.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SearchHouseNameRequest(
        @NotBlank(message = "house 이름을 입력해주세요.")
        String name,

        List<String> types
        ) {
}
