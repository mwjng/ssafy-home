package ssafy.ssafyhome.auth.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 3600)
public class RefreshToken {

    @Id
    private String token;

    private Long memberId;

    public RefreshToken(final String token, final Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }
}
