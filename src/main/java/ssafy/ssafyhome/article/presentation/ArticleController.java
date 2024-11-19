package ssafy.ssafyhome.article.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.presentation.request.ArticleSearchCondition;
import ssafy.ssafyhome.article.presentation.request.ArticleUpdateRequest;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentCreateRequest;
import ssafy.ssafyhome.comment.presentation.request.CommentSearchCondition;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController implements ArticleControllerDocs{

    @Override
    public ResponseEntity<ArticlesResponse> searchMyArticles(final AccessContext accessContext, final ArticleSearchCondition articleSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(final AccessContext accessContext, final Long articleId, final ArticleUpdateRequest articleUpdateRequest, final List<MultipartFile> images) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommentsResponse> searchComments(final Long articleId, final CommentSearchCondition commentSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createComment(final AccessContext accessContext, final Long articleId, final CommentCreateRequest commentCreateRequest, final MultipartFile image) {
        return null;
    }
}
