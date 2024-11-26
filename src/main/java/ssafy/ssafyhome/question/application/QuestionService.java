package ssafy.ssafyhome.question.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.question.application.response.QuestionResponse;
import ssafy.ssafyhome.question.application.response.QuestionsResponse;
import ssafy.ssafyhome.question.domain.Question;
import ssafy.ssafyhome.question.domain.repository.AnswerRepository;
import ssafy.ssafyhome.question.domain.repository.QuestionRepository;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    public void validateQuestionByMember(final Long memberId, final Long questionId) {
        if(!questionRepository.existsByMemberIdAndId(memberId, questionId)) {
            throw new AuthException(INVALID_QUESTION_WITH_MEMBER);
        }
    }

    public QuestionsResponse getQuestions(final Pageable pageable) {
        List<Question> questions = questionRepository.findQuestions(pageable.previousOrFirst());
        return new QuestionsResponse(questions.stream()
            .map(QuestionResponse::of)
            .toList());
    }

    public QuestionResponse getQuestionById(final Long questionId) {
        final Question question = questionRepository.findByQuestionId(questionId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_QUESTION_ID));
        return QuestionResponse.of(question);
    }

    @Transactional
    public void saveQuestion(Long memberId, String title, String content) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_ID));
        questionRepository.save(Question.builder()
            .title(title)
            .content(content)
            .member(member)
            .build());
    }

    @Transactional
    public void updateQuestion(Long questionId, String title, String content) {
        final Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_QUESTION_ID));
        question.changeQuestion(title, content);
    }

    public QuestionsResponse getQuestionsByMemberId(final Long memberId, final Pageable pageable) {
        final List<Question> questions = questionRepository.findByMemberId(memberId, pageable.previousOrFirst());
        return new QuestionsResponse(questions.stream()
            .map(QuestionResponse::of)
            .toList());
    }

    @Transactional
    public void deleteQuestion(final Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new BadRequestException(NOT_FOUND_QUESTION_ID);
        }
        questionRepository.deleteById(questionId);
    }
}
