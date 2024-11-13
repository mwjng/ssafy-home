package ssafy.ssafyhome.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.member.domain.Member;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class KakaoOAuthUserInfo implements OAuthUserInfo {

    @JsonProperty("id")
    private Long userId;

    @JsonProperty("kakao_account")
    private KakaoAccount account;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getNickname() {
        return account.getProfile().getNickname();
    }

    @Override
    public String getImageUrl() {
        return account.getProfile().getImageUrl();
    }

    @Override
    public String getName() {
        return account.getName();
    }

    @Override
    public String getEmail() {
        return account.getEmail();
    }

    public Member toMember() {
        return Member.builder()
            .nickname(getNickname())
            .name(getName())
            .email(getEmail())
            .userId(userId)
            .imageUrl(getImageUrl())
            .build();
    }
}
