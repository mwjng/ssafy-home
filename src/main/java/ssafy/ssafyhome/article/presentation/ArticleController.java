package ssafy.ssafyhome.article.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.ArticleService;
import ssafy.ssafyhome.article.application.response.ArticleResponse;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.presentation.request.ArticleCreateRequest;
import ssafy.ssafyhome.article.presentation.request.ArticleUpdateRequest;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.comment.application.CommentService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static ssafy.ssafyhome.common.util.UrlUtil.getBaseUrl;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class ArticleController implements ArticleControllerDocs {

    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping("/me/articles")
    @UserAccess
    public ResponseEntity<ArticlesResponse> getMyArticles(
        @AuthenticationPrincipal final AccessContext accessContext,
        final Pageable pageable,
        final HttpServletRequest request
    ) {
        return ResponseEntity.ok(articleService.getMemberArticles(
            accessContext.getMemberId(),
            pageable,
            getBaseUrl(request)));
    }

    @GetMapping("/me/liked-articles")
    @UserAccess
    public ResponseEntity<ArticlesResponse> getLikeArticles(
        @AuthenticationPrincipal final AccessContext accessContext,
        final Pageable pageable,
        final HttpServletRequest request
    ) {
        return ResponseEntity.ok(articleService.getLikeArticles(
            accessContext.getMemberId(),
            pageable,
            getBaseUrl(request)));
    }

    @GetMapping("/houses/{houseId}/articles")
    public ResponseEntity<ArticlesResponse> getArticles(
        @PathVariable final Long houseId,
        final Pageable pageable,
        final HttpServletRequest request
    ) {
        return ResponseEntity.ok(articleService.getHouseArticles(
            houseId,
            pageable,
            getBaseUrl(request)));
    }

    @PostMapping("/houses/{houseId}/articles")
    @UserAccess
    public ResponseEntity<Void> createArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long houseId,
        @Valid @RequestPart ArticleCreateRequest articleCreateRequest,
        @RequestPart(required = false) final List<MultipartFile> images
    ) {
        articleService.createArticle(
            accessContext.getMemberId(),
            houseId,
            articleCreateRequest.content(),
            images
        );
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(
        @PathVariable final Long articleId,
        final HttpServletRequest request
    ) {
        return ResponseEntity.ok(articleService.getArticle(articleId, getBaseUrl(request)));
    }

    @PutMapping("/articles/{articleId}")
    @UserAccess
    public ResponseEntity<Void> updateArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long articleId,
        @Valid @RequestPart final ArticleUpdateRequest articleUpdateRequest,
        @RequestPart(required = false) final List<MultipartFile> images
    ) {
        articleService.validateArticleByMember(accessContext.getMemberId(), articleId);
        articleService.updateArticle(articleId, articleUpdateRequest.content(), images);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/articles/{articleId}")
    @UserAccess
    public ResponseEntity<Void> deleteArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long articleId
    ) {
        if(!accessContext.isMaster()) {
            articleService.validateArticleByMember(accessContext.getMemberId(), articleId);
        }
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }
}
