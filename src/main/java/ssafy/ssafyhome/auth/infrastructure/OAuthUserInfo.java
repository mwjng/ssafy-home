package ssafy.ssafyhome.auth.infrastructure;

import ssafy.ssafyhome.member.domain.Member;

public interface OAuthUserInfo {

    Long getUserId();
    String getNickname();
    String getImageUrl();
    String getName();
    String getEmail();
    Member toMember();
}
