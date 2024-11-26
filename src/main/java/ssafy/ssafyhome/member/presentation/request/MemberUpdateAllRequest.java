package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdateAllRequest(
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
    String nickname,

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@(.+)$", message = "이메일 형식이 올바르지 않습니다.")
    String email,

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 15, message = "아이디는 5자 이상 15자 이하여야 합니다.")
    String loginId,

    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    @Size(min = 5, max = 15, message = "비밀번호는 5자 이상 15자 이하여야 합니다.")
    String currentPassword,

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Size(min = 5, max = 15, message = "비밀번호는 5자 이상 15자 이하여야 합니다.")
    String newPassword
) {
}
