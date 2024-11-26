package ssafy.ssafyhome.question.application.response;

import java.util.List;

public record QuestionsResponse(
    List<QuestionResponse> questions
) {
}
