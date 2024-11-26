package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ssafy.ssafyhome.auth.infrastructure.PasswordEncoder;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;

import static ssafy.ssafyhome.member.domain.MemberRole.*;
import static ssafy.ssafyhome.member.domain.MemberRole.MASTER;
import static ssafy.ssafyhome.member.domain.SocialType.NONE;

public record MemberCreateRequest(
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
    String nickname,

    @Size(min = 2, max = 5, message = "이름은 2자 이상 5자 이하여야 합니다.")
    String name,

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@(.+)$", message = "이메일 형식이 올바르지 않습니다.")
    String email,

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 15, message = "아이디는 5자 이상 15자 이하여야 합니다.")
    String loginId,

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 5, max = 15, message = "비밀번호는 5자 이상 15자 이하여야 합니다.")
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
