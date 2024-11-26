package ssafy.ssafyhome.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssafy.ssafyhome.auth.exception.AuthException;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_OAUTH_PROVIDER;

@RequiredArgsConstructor
@Component
public class OAuthProviders {

    private final List<OAuthProvider> providers;

    public OAuthProvider getProvider(final String providerName) {
        return providers.stream()
            .filter(provider -> provider.is(providerName))
            .findAny()
            .orElseThrow(() -> new AuthException(INVALID_OAUTH_PROVIDER));
    }
}
