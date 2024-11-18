package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdateRequest(
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
    String nickname,

    @Size(min = 2, max = 5, message = "이름은 2자 이상 5자 이하여야 합니다.")
    String name,

    @Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@(.+)$", message = "이메일 형식이 올바르지 않습니다.")
    String email
) {
}
