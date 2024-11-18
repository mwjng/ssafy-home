package ssafy.ssafyhome.likearticle.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.likearticle.application.response.LikeArticlesResponse;

@RestController
@RequestMapping("/like/articles")
public class LikeArticleController implements LikeArticleControllerDocs{

    @Override
    public ResponseEntity<LikeArticlesResponse> searchAll(final AccessContext accessContext, final int size, final Long cursorId) {
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
