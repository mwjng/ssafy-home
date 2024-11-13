package ssafy.ssafyhome.auth.infrastructure;

public interface OAuthProvider {

    OAuthUserInfo getOAuthUserInfo(String code);
}
