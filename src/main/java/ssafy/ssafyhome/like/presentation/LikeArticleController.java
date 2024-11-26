package ssafy.ssafyhome.like.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.like.application.LikeArticleService;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class LikeArticleController implements LikeArticleControllerDocs {

    private final LikeArticleService likeArticleService;

    @PostMapping("/articles/{articleId}/like")
    @UserAccess
    public ResponseEntity<Void> createLikeArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long articleId
    ) {
        likeArticleService.createLikeArticle(accessContext.getMemberId(), articleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/articles/{articleId}/like")
    @UserAccess
    public ResponseEntity<Void> deleteLikeArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long articleId
    ) {
        likeArticleService.deleteLikeArticle(accessContext.getMemberId(), articleId);
        return ResponseEntity.noContent().build();
    }
}
