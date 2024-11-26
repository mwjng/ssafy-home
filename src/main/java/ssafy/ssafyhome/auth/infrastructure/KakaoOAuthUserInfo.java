package ssafy.ssafyhome.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static ssafy.ssafyhome.member.domain.SocialType.KAKAO;

@NoArgsConstructor(access = PRIVATE)
public class KakaoOAuthUserInfo implements OAuthUserInfo {

    @JsonProperty("id")
    private String socialLoginId;

    @JsonProperty("kakao_account")
    private KakaoAccount account;

    @Override
    public String  getSocialLoginId() {
        return socialLoginId;
    }

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

    @Override
    public Member toMember(MemberRole memberRole) {
        return Member.builder()
            .nickname(UUID.randomUUID().toString().substring(0, 8))
            .name(getNickname())
            .email(getEmail())
            .socialLoginId(socialLoginId)
            .memberRole(memberRole)
            .socialType(KAKAO)
            .build();
    }
}
