package ssafy.ssafyhome.question.application;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.question.domain.Answer;
import ssafy.ssafyhome.question.domain.Question;
import ssafy.ssafyhome.question.domain.repository.AnswerRepository;
import ssafy.ssafyhome.question.domain.repository.QuestionRepository;
import ssafy.ssafyhome.question.presentation.request.AnswerRequest;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_QUESTION_ID;

@Transactional
@RequiredArgsConstructor
@Service
public class AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public void createAnswer(final Long questionId, final AnswerRequest request) {
        final Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_QUESTION_ID));
        final Answer answer = request.toAnswer();
        question.registerAnswer(answer);
    }

    public void updateAnswer(final Long questionId, final AnswerRequest request) {
        final Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_QUESTION_ID));
        final Answer answer = question.getAnswer();
        answer.updateAnswer(request.content());
    }
}
