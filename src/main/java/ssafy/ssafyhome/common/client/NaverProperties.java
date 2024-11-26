package ssafy.ssafyhome.common.client;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "oauth.naver")
public class NaverProperties {

    private final String grantType;
    private final String clientId;
    private final String state;
    private final String clientSecret;
    private final String tokenUri;
    private final String infoUri;

    @ConstructorBinding
    public NaverProperties(
        final String grantType,
        final String clientId,
        final String state,
        final String clientSecret,
        final String tokenUri,
        final String infoUri
    ) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.state = state;
        this.clientSecret = clientSecret;
        this.tokenUri = tokenUri;
        this.infoUri = infoUri;
    }
}
