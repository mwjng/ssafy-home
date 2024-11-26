package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginIdUpdateRequest(
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 15, message = "아이디는 5자 이상 15자 이하여야 합니다.")
    String loginId
) {
}
