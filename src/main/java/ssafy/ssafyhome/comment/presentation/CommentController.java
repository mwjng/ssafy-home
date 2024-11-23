package ssafy.ssafyhome.comment.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.comment.application.CommentService;
import ssafy.ssafyhome.comment.application.response.ArticleCommentsResponse;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentCreateRequest;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class CommentController implements CommentControllerDocs{

    private final CommentService commentService;

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<ArticleCommentsResponse> getComments(
        @PathVariable final Long articleId,
        final Pageable pageable
    ) {
        return ResponseEntity.ok(
            commentService.getArticleComments(articleId, pageable)
        );
    }

    @GetMapping("/me/comments")
    @UserAccess
    public ResponseEntity<CommentsResponse> getMyComments(
        @AuthenticationPrincipal final AccessContext accessContext,
        final Pageable pageable
    ) {
        return ResponseEntity.ok(
            commentService.getMemberComments(accessContext.getMemberId(), pageable)
        );
    }

    @PostMapping("/articles/{articleId}/comments")
    @UserAccess
    public ResponseEntity<Void> createComment(
        @AuthenticationPrincipal AccessContext accessContext,
        @PathVariable Long articleId,
        @Valid @RequestBody final CommentCreateRequest commentCreateRequest
    ) {
        commentService.createComment(
            accessContext.getMemberId(),
            articleId,
            commentCreateRequest.content()
        );
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/comments/{commentId}")
    @UserAccess
    public ResponseEntity<Void> deleteComment(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long commentId
    ) {
        commentService.validateCommentByMember(accessContext.getMemberId(), commentId);
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
