package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record NicknameUpdateRequest(
    @NotBlank(message = "변경할 닉네임을 입력해주세요.")
    String nickname
) {
}
