package ssafy.ssafyhome.ai.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest (
        @NotBlank(message = "내용을 입력해주세요.")
        String message){
}
