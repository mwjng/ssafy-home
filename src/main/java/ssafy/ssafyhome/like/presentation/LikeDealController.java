package ssafy.ssafyhome.like.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.like.application.response.LikeDealsResponse;

// TODO
@RequiredArgsConstructor
@RequestMapping("/deals/like")
@RestController
public class LikeDealController implements LikeDealControllerDocs{

    @Override
    @UserAccess
    public ResponseEntity<LikeDealsResponse> searchAll(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> create(final AccessContext accessContext, final Long id) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long id) {
        return null;
    }
}
