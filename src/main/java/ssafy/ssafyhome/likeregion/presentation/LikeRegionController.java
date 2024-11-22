package ssafy.ssafyhome.likeregion.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;

@RestController
@RequestMapping("/like/regions")
public class LikeRegionController implements LikeRegionControllerDocs{

    @Override
    public ResponseEntity<LikeRegionsResponse> searchAll(final AccessContext accessContext, final int size, final Long cursorId) {
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
