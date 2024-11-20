package ssafy.ssafyhome.directmessage.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record SendMessageRequest(
        @NotBlank(message = "내용을 입력해주세요.")
        String content) {
}
