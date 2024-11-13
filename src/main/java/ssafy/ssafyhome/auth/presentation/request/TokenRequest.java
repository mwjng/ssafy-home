package ssafy.ssafyhome.auth.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest(
    @NotBlank(message = "인가 코드는 필수값입니다.")
    String code
) {
}
