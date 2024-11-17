package ssafy.ssafyhome.likeregion.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;
import ssafy.ssafyhome.likeregion.presentation.request.LikeRegionCreateRequest;

@RestController
@RequestMapping("/like/region")
public class LikeRegionController implements LikeRegionControllerDocs{
    @Override
    public ResponseEntity<LikeRegionsResponse> search(final AccessContext accessContext) {
        return null;
    }

    @Override
    public ResponseEntity<Void> create(final AccessContext accessContext, final LikeRegionCreateRequest likeRegionCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext) {
        return null;
    }
}
