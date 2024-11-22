package ssafy.ssafyhome.article.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.response.ArticleResponse;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.domain.Article;
import ssafy.ssafyhome.article.domain.repository.ArticleRepository;
import ssafy.ssafyhome.article.presentation.request.ArticleUpdateRequest;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.common.exception.ErrorCode;
import ssafy.ssafyhome.image.application.ImageService;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_ARTICLE_ID;
import static ssafy.ssafyhome.image.application.ImageDirectory.ARTICLE;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ImageService imageService;

    public ArticlesResponse getMemberArticles(Long memberId, String baseUrl) {
        final List<Article> articles = articleRepository.findByMemberId(memberId);
        final List<ArticleResponse> articleResponses = articles.stream()
            .map(article -> ArticleResponse.of(
                article,
                getImageUrlList(baseUrl, article.getDirName(), ARTICLE.getDirectory())))
            .toList();
        return new ArticlesResponse(articleResponses);
    }

    private List<String> getImageUrlList(
        final String baseUrl,
        final String dirName,
        final String imgDir
    ) {
        final List<String> imageFileNames = imageService.getImageFileNames(dirName, imgDir);
        return imageService.getImageUrlList(baseUrl, imgDir, imageFileNames, dirName);
    }

    @Transactional
    public void updateArticle(
        final Long articleId,
        final ArticleUpdateRequest articleUpdateRequest,
        final List<MultipartFile> images
    ) {
        final Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_ARTICLE_ID));
    }
}
