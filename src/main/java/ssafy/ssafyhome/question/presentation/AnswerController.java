package ssafy.ssafyhome.question.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AdminAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.question.application.AnswerService;
import ssafy.ssafyhome.question.application.QuestionService;
import ssafy.ssafyhome.question.presentation.request.AnswerRequest;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/questions/{questionId}/answer")
    @AdminAccess
    public ResponseEntity<Void> createAnswer(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long questionId,
        @Valid @RequestBody final AnswerRequest answerRequest
    ) {
        answerService.createAnswer(questionId, answerRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/questions/{questionId}/answer")
    @AdminAccess
    public ResponseEntity<Void> updateAnswer(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long questionId,
        @Valid @RequestBody final AnswerRequest answerRequest
    ) {
        answerService.updateAnswer(questionId, answerRequest);
        return ResponseEntity.noContent().build();
    }
}
