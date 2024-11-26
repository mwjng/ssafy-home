package ssafy.ssafyhome.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static ssafy.ssafyhome.member.domain.SocialType.GOOGLE;

@NoArgsConstructor(access = PRIVATE)
public class GoogleOAuthUserInfo implements OAuthUserInfo {

    @JsonProperty("id")
    private String socialLoginId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String imageUrl;

    @Override
    public String getSocialLoginId() {
        return socialLoginId;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Member toMember(MemberRole memberRole) {
        return Member.builder()
            .nickname(UUID.randomUUID().toString().substring(0, 8))
            .name(name)
            .email(email)
            .socialLoginId(socialLoginId)
            .memberRole(memberRole)
            .socialType(GOOGLE)
            .build();
    }
}