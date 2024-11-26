package ssafy.ssafyhome.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class KakaoAccount {

    @JsonProperty("profile")
    private KakaoProfile profile;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;
}
