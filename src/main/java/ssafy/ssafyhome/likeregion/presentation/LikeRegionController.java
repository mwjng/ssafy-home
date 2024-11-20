package ssafy.ssafyhome.likeregion.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;

@RestController
@RequestMapping("/regions/like")
public class LikeRegionController implements LikeRegionControllerDocs {

    @Override
    @UserAccess
    public ResponseEntity<LikeRegionsResponse> searchAll(final AccessContext accessContext, final int size, final Long cursorId) {
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
