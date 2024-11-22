package ssafy.ssafyhome.comment.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.comment.application.CommentService;
import ssafy.ssafyhome.comment.application.response.CommentResponse;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentUpdateRequest;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController implements CommentControllerDocs{

    private final CommentService commentService;

    @GetMapping
    @UserAccess
    public ResponseEntity<CommentsResponse> getMyComments(
        @AuthenticationPrincipal final AccessContext accessContext,
        final Pageable pageable
    ) {
        return ResponseEntity.ok(
            commentService.getMemberComments(accessContext.getMemberId(), pageable)
        );
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable final Long commentId) {
        return null;
    }

    @PatchMapping("/{commentId}")
    @UserAccess
    public ResponseEntity<Void> updateComment(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long commentId,
        @RequestBody final CommentUpdateRequest commentUpdateRequest
    ) {
        return null;
    }

    @DeleteMapping("/{commentId}")
    @UserAccess
    public ResponseEntity<Void> deleteComment(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
