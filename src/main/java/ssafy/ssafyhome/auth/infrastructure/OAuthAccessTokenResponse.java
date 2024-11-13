package ssafy.ssafyhome.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthAccessTokenResponse(
    @JsonProperty("access_token")
    String accessToken
) {
}
