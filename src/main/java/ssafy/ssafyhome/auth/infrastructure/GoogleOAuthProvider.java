package ssafy.ssafyhome.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.common.client.GoogleProperties;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static ssafy.ssafyhome.common.exception.ErrorCode.FAIL_OAUTH_USERINFO_RETRIEVAL;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_AUTHORIZATION_CODE;

@RequiredArgsConstructor
@Component
public class GoogleOAuthProvider implements OAuthProvider {

    private static final String PROVIDER_NAME = "google";

    private final RestClient restClient;
    private final GoogleProperties properties;

    @Override
    public boolean is(final String name) {
        return PROVIDER_NAME.equals(name);
    }

    @Override
    public OAuthUserInfo getOAuthUserInfo(final String code) {
        final String accessToken = requestGoogleAccessToken(code);
        final ResponseEntity<GoogleOAuthUserInfo> response = restClient.get()
            .uri(properties.getInfoUri())
            .header(AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .toEntity(GoogleOAuthUserInfo.class);

        if(response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new AuthException(FAIL_OAUTH_USERINFO_RETRIEVAL);
    }

    private String requestGoogleAccessToken(final String code) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", properties.getGrantType());
        params.add("client_id", properties.getClientId());
        params.add("redirect_uri", properties.getRedirectUri());
        params.add("client_secret", properties.getClientSecret());

        final ResponseEntity<OAuthAccessTokenResponse> response = restClient.post()
            .uri(properties.getTokenUri())
            .contentType(APPLICATION_FORM_URLENCODED)
            .body(params)
            .retrieve()
            .toEntity(OAuthAccessTokenResponse.class);

        return Optional.ofNullable(response.getBody())
            .orElseThrow(() -> new AuthException(INVALID_AUTHORIZATION_CODE))
            .accessToken();
    }
}
