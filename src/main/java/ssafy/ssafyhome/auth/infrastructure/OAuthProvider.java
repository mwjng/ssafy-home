package ssafy.ssafyhome.auth.infrastructure;

public interface OAuthProvider {

    boolean is(String name);

    OAuthUserInfo getOAuthUserInfo(String code);
}
