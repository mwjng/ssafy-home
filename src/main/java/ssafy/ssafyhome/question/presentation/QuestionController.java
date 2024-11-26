package ssafy.ssafyhome.question.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.question.application.QuestionService;
import ssafy.ssafyhome.question.application.response.QuestionResponse;
import ssafy.ssafyhome.question.application.response.QuestionsResponse;
import ssafy.ssafyhome.question.presentation.request.QuestionRequest;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questions")
    public ResponseEntity<QuestionsResponse> getAllQuestions(final Pageable pageable) {
        return ResponseEntity.ok(questionService.getQuestions(pageable));
    }

    @GetMapping("/me/questions")
    @UserAccess
    public ResponseEntity<QuestionsResponse> getAllQuestionsByUser(
        @AuthenticationPrincipal final AccessContext accessContext,
        final Pageable pageable
    ) {
        return ResponseEntity.ok(
            questionService.getQuestionsByMemberId(accessContext.getMemberId(), pageable)
        );
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestionById(final @PathVariable Long questionId) {
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

    @PostMapping("/questions")
    @UserAccess
    public ResponseEntity<Void> createQuestion(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestBody final QuestionRequest questionRequest
    ) {
        questionService.saveQuestion(
            accessContext.getMemberId(), questionRequest.title(), questionRequest.content()
        );
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/questions/{questionId}")
    @UserAccess
    public ResponseEntity<Void> updateQuestion(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long questionId,
        @RequestBody final QuestionRequest questionRequest
    ) {
        questionService.validateQuestionByMember(accessContext.getMemberId(), questionId);
        questionService.updateQuestion(
            questionId, questionRequest.title(), questionRequest.content()
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/questions/{questionId}")
    @UserAccess
    public ResponseEntity<Void> deleteQuestion(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long questionId
    ) {
        if(!accessContext.isMaster()) {
            questionService.validateQuestionByMember(accessContext.getMemberId(), questionId);
        }
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
