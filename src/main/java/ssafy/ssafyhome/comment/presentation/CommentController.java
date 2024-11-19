package ssafy.ssafyhome.comment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.comment.application.response.CommentResponse;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentCreateRequest;
import ssafy.ssafyhome.comment.presentation.request.CommentSearchCondition;
import ssafy.ssafyhome.comment.presentation.request.CommentUpdateRequest;

@RestController
@RequestMapping("/comments")
public class CommentController implements CommentControllerDocs{

    @Override
    public ResponseEntity<CommentsResponse> searchMyComment(final AccessContext accessContext, final CommentSearchCondition commentSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<CommentResponse> search(final Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(final AccessContext accessContext, final Long id, final CommentUpdateRequest commentUpdateRequest, final MultipartFile image) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long id) {
        return null;
    }
}
