package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotBlank;
import ssafy.ssafyhome.auth.infrastructure.PasswordEncoder;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;

import static ssafy.ssafyhome.member.domain.MemberRole.*;
import static ssafy.ssafyhome.member.domain.MemberRole.MASTER;
import static ssafy.ssafyhome.member.domain.SocialType.NONE;

public record MemberCreateRequest(
    @NotBlank(message = "닉네임을 입력해주세요.")
    String nickname,

    String name,

    String email,

    @NotBlank(message = "아이디를 입력해주세요.")
    String loginId,

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password,

    @NotBlank(message = "회원 권한은 필수값입니다.")
    String memberRole
){
    public Member toMember(PasswordEncoder encoder) {
        return Member.builder()
            .nickname(nickname)
            .name(name)
            .email(email)
            .socialLoginId(loginId)
            .password(encoder.encode(password))
            .memberRole(getMemberRole(memberRole))
            .socialType(NONE)
            .build();
    }

    public boolean isAdmin() {
        MemberRole memberRole = getMemberRole(memberRole());
        return ADMIN.equals(memberRole) || MASTER.equals(memberRole);
    }
}
