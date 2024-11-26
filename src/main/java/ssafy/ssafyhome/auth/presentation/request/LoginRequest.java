package ssafy.ssafyhome.auth.presentation.request;

import jakarta.validation.constraints.NotBlank;
import ssafy.ssafyhome.common.validation.BasicLoginCheck;
import ssafy.ssafyhome.common.validation.OAuthLoginCheck;
import ssafy.ssafyhome.member.domain.MemberRole;

import static ssafy.ssafyhome.member.domain.MemberRole.*;

public record LoginRequest(
    @NotBlank(message = "인가 코드는 필수값입니다.", groups = OAuthLoginCheck.class)
    String code,

    @NotBlank(message = "OAuth 제공자는 필수값입니다.", groups = OAuthLoginCheck.class)
    String provider,

    @NotBlank(message = "회원 권한은 필수값입니다.", groups = OAuthLoginCheck.class)
    String memberRole,

    @NotBlank(message = "아이디를 입력하세요.", groups = BasicLoginCheck.class)
    String loginId,

    @NotBlank(message = "비밀번호를 입력하세요.", groups = BasicLoginCheck.class)
    String password
) {
    public boolean isAdmin() {
        MemberRole memberRole = getMemberRole();
        return ADMIN.equals(memberRole) || MASTER.equals(memberRole);
    }

    public MemberRole getMemberRole() {
        return MemberRole.getMemberRole(memberRole);
    }
}
