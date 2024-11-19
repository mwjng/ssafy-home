package ssafy.ssafyhome.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;

import java.util.UUID;

import static ssafy.ssafyhome.member.domain.SocialType.NAVER;

public class NaverOAuthUserInfo implements OAuthUserInfo {

    @JsonProperty("id")
    private String socialLoginId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("profile_image")
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
            .socialType(NAVER)
            .build();
    }
}
