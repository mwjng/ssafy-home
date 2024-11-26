package ssafy.ssafyhome.like.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record LikeRegionCreateRequest(
        @NotBlank(message = "시 주소를 입력해주세요.")
        String sido,
        @NotBlank(message = "구군 주소를 입력해주세요.")
        String gugun,
        @NotBlank(message = "동 주소를 입력해주세요.")
        String dong) {
}
