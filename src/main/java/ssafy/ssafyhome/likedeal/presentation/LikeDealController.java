package ssafy.ssafyhome.likedeal.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.likedeal.application.response.LikeDealsResponse;

@RestController
@RequestMapping("/like/deals")
public class LikeDealController implements LikeDealControllerDocs{

    @Override
    public ResponseEntity<LikeDealsResponse> searchAll(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> create(final AccessContext accessContext, final Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long id) {
        return null;
    }
}
