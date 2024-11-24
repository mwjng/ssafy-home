package ssafy.ssafyhome.question.application.response;

import ssafy.ssafyhome.question.domain.Question;

public record QuestionResponse(
    Long questionId,
    String title,
    String content,
    String nickname,
    String answer
) {
    public static QuestionResponse of(Question question) {
        return new QuestionResponse(
            question.getId(),
            question.getTitle(),
            question.getContent(),
            question.getMember().getNickname(),
            question.getAnswer().getContent()
        );
    }
}
