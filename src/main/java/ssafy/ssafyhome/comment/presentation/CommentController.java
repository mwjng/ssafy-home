package ssafy.ssafyhome.comment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.comment.application.response.CommentResponse;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentRequest;

@RestController
@RequestMapping("/comment")
public class CommentController implements CommentControllerDocs{

    @Override
    public ResponseEntity<CommentsResponse> searchAll(final Long articleId, final int size, final Long cursorId) {
        return null;
    }

    @Override
    public ResponseEntity<CommentsResponse> searchMyComment(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    public ResponseEntity<CommentResponse> search(final Long commentId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> create(final AccessContext accessContext, final Long articleId, final CommentRequest commentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(final AccessContext accessContext, final Long commentId, final CommentRequest commentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long commentId) {
        return null;
    }
}
