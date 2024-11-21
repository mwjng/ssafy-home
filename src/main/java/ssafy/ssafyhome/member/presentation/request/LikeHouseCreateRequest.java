package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotNull;

public record LikeHouseCreateRequest(
        @NotNull(message = "houseId 값을 입력해주세요.")
        Long houseId) {
}
