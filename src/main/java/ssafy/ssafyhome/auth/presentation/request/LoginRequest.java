package ssafy.ssafyhome.auth.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "인가 코드는 필수값입니다.")
    String code,

    @NotBlank(message = "OAuth 제공자는 필수값입니다.")
    String provider,

    @NotBlank(message = "회원 권한은 필수값입니다.")
    String memberRole
) {
}
