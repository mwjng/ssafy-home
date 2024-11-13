package ssafy.ssafyhome.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class KakaoProfile {

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("profile_image_url")
    private String imageUrl;
}
