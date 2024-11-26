package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordUpdateRequest(
    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    @Size(min = 5, max = 15, message = "비밀번호는 5자 이상 15자 이하여야 합니다.")
    String currentPassword,

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Size(min = 5, max = 15, message = "비밀번호는 5자 이상 15자 이하여야 합니다.")
    String newPassword
) {
}
