package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotBlank;
import ssafy.ssafyhome.auth.infrastructure.PasswordEncoder;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;

import static ssafy.ssafyhome.member.domain.MemberRole.ADMIN;
import static ssafy.ssafyhome.member.domain.MemberRole.getMemberRole;

public record AdminCreateRequest(
    @NotBlank(message = "닉네임을 입력해주세요.")
    String nickname,

    String name,

    String email,

    @NotBlank(message = "아이디를 입력해주세요.")
    String loginId,

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password
) {
    public Member toMember(PasswordEncoder encoder) {
        return Member.builder()
            .nickname(nickname)
            .name(name)
            .email(email)
            .socialLoginId(loginId)
            .password(encoder.encode(password))
            .memberRole(ADMIN)
            .build();
    }
}
