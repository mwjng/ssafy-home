package ssafy.ssafyhome.article.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.ArticleService;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.presentation.request.ArticleUpdateRequest;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.comment.application.CommentService;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentCreateRequest;

import java.util.List;

import static ssafy.ssafyhome.common.util.UrlUtil.getBaseUrl;

@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController implements ArticleControllerDocs{

    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping
    @UserAccess
    public ResponseEntity<ArticlesResponse> getMyArticles(
        @AuthenticationPrincipal final AccessContext accessContext,
        final Pageable pageable,
        final HttpServletRequest request
    ) {
        return ResponseEntity.ok(articleService.getMemberArticles(
            accessContext.getMemberId(),
            getBaseUrl(request)));
    }

    @PutMapping("/{articleId}")
    @UserAccess
    public ResponseEntity<Void> updateArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long articleId,
        @Valid @RequestPart final ArticleUpdateRequest articleUpdateRequest,
        @RequestPart final List<MultipartFile> images
    ) {
        articleService.updateArticle(articleId, articleUpdateRequest.content(), images);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{articleId}")
    @UserAccess
    public ResponseEntity<Void> deleteArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long articleId
    ) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<CommentsResponse> getComments(
        @PathVariable final Long articleId,
        final Pageable pageable
    ) {
        return null;
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Void> createComment(
        @AuthenticationPrincipal AccessContext accessContext,
        @PathVariable Long articleId,
        @RequestBody final CommentCreateRequest commentCreateRequest
    ) {
        return null;
    }
}
