package ssafy.ssafyhome.auth.infrastructure;

import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;

public interface OAuthUserInfo {

    String getSocialLoginId();

    String getImageUrl();

    String getName();

    String getEmail();

    Member toMember(MemberRole memberRole);
}
