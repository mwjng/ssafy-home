package ssafy.ssafyhome.member.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;
import java.util.UUID;

@Getter
@RedisHash(value = "VerificationCode", timeToLive = 180)
public class VerificationCode {

    @Id
    private String loginId;

    private String code;

    public VerificationCode(final String loginId) {
        this.loginId = loginId;
        this.code = generateRandomCode();
    }

    public boolean isValid(String code) {
        return Objects.equals(this.code, code);
    }

    private String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
