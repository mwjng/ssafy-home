package ssafy.ssafyhome.common.client;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "oauth")
public class KakaoProperties {

    private final String grantType;
    private final String clientId;
    private final String redirectUri;
    private final String clientSecret;
    private final String tokenUrl;
    private final String infoUrl;

    @ConstructorBinding
    public KakaoProperties(
        final String grantType,
        final String clientId,
        final String redirectUri,
        final String clientSecret,
        final String tokenUrl,
        final String infoUrl
    ) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
        this.infoUrl = infoUrl;
    }
}
