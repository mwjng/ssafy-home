package ssafy.ssafyhome.auth.application;

public record AuthToken(
    String accessToken,
    String refreshToken
) {
}
