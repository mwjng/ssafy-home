package ssafy.ssafyhome.question.presentation.request;

import jakarta.validation.constraints.NotBlank;
import ssafy.ssafyhome.question.domain.Answer;
import ssafy.ssafyhome.question.domain.Question;

public record AnswerRequest(
    @NotBlank(message = "답글을 입력해주세요.")
    String content
) {
    public Answer toAnswer() {
        return new Answer(this.content);
    }
}
