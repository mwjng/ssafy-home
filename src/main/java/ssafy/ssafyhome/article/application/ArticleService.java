package ssafy.ssafyhome.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.response.ArticleCount;
import ssafy.ssafyhome.article.application.response.ArticleResponse;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.domain.Article;
import ssafy.ssafyhome.article.domain.repository.ArticleRepository;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.image.application.ImageDirectory.ARTICLE;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;
    private final ImageService imageService;
    private final ApplicationEventPublisher eventPublisher;

    public void validateArticleByMember(final Long memberId, final Long articleId) {
        if (!articleRepository.existsByMemberIdAndId(memberId, articleId)) {
            throw new AuthException(INVALID_ARTICLE_WITH_MEMBER);
        }
    }

    public ArticlesResponse getMemberArticles(final Long memberId, final Pageable pageable, final String baseUrl) {
        final List<Article> articles = articleRepository.findByMemberId(memberId, pageable.previousOrFirst());
        return getArticlesResponse(baseUrl, articles);
    }

    public ArticlesResponse getHouseArticles(final Long houseId, final Pageable pageable, final String baseUrl) {
        final List<Article> articles = articleRepository.findByHouseId(houseId, pageable.previousOrFirst());
        return getArticlesResponse(baseUrl, articles);
    }

    private Map<Long, Long> getCountMap(final List<ArticleCount> articleRepository) {
        return articleRepository.stream()
            .collect(toMap(ArticleCount::articleId, ArticleCount::count));
    }

    private List<Long> getArticleIds(final List<Article> articles) {
        return articles.stream()
            .map(Article::getId)
            .toList();
    }

    public ArticlesResponse getLikeArticles(final Long memberId, final Pageable pageable, final String baseUrl) {
        final List<Article> articles = articleRepository.findLikeArticlesByMemberId(memberId, pageable);
        return getArticlesResponse(baseUrl, articles);
    }

    private ArticlesResponse getArticlesResponse(final String baseUrl, final List<Article> articles) {
        final List<Long> articleIds = getArticleIds(articles);

        final Map<Long, Long> commentCountMap = getCountMap(articleRepository.countArticleCommentsByArticleIds(articleIds));
        final Map<Long, Long> likeCountMap = getCountMap(articleRepository.countArticleLikesByArticleIds(articleIds));

        return new ArticlesResponse(articles.stream()
            .map(article -> getArticleResponse(article, baseUrl, likeCountMap, commentCountMap))
            .toList());
    }

    private ArticleResponse getArticleResponse(final Article article, final String baseUrl, final Map<Long, Long> likeCountMap, final Map<Long, Long> commentCountMap) {
        return ArticleResponse.of(
            article,
            likeCountMap.getOrDefault(article.getId(), 0L),
            commentCountMap.getOrDefault(article.getId(), 0L),
            getImageUrlList(baseUrl, article.getDirName(), ARTICLE.getDirectory()));
    }

    private List<String> getImageUrlList(
        final String baseUrl,
        final String dirName,
        final String imgDir
    ) {
        final List<String> imageFileNames = imageService.getImageFileNames(dirName, imgDir);
        return imageService.getImageUrlList(baseUrl, imgDir, imageFileNames, dirName);
    }

    public ArticleResponse getArticle(final Long articleId, final String baseUrl) {
        final Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_ARTICLE_ID));

        final Map<Long, Long> commentCountMap = getCountMap(articleRepository.countArticleCommentsByArticleIds(List.of(articleId)));
        final Map<Long, Long> likeCountMap = getCountMap(articleRepository.countArticleLikesByArticleIds(List.of(articleId)));

        return getArticleResponse(article, baseUrl, likeCountMap, commentCountMap);
    }

    @Transactional
    public void updateArticle(
        final Long articleId,
        final String content,
        final List<MultipartFile> images
    ) {
        final Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_ARTICLE_ID));
        final String imagePath = imageService.save(images, ARTICLE.getDirectory());
        deleteImages(article.getDirName(), ARTICLE.getDirectory());
        article.updateArticle(content, imagePath);
    }

    @Transactional
    public void deleteArticle(final Long articleId) {
        final Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_ARTICLE_ID));
        deleteImages(article.getDirName(), ARTICLE.getDirectory());
        articleRepository.deleteById(articleId);
    }

    private void deleteImages(final String dirName, String imgDir) {
        final List<String> imageFilePaths = imageService.getImageFilePaths(dirName, imgDir);
        final String imageFileDirPath = imageService.getImageFileDirPath(dirName, imgDir);
        eventPublisher.publishEvent(new ImageEvent(imageFileDirPath, imageFilePaths));
    }

    @Transactional
    public void createArticle(
        final Long memberId,
        final Long houseId,
        final String content,
        final List<MultipartFile> images
    ) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_ID));
        final House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_HOUSE_ID));
        final String imagePath = imageService.save(images, ARTICLE.getDirectory());

        articleRepository.save(new Article(content, imagePath, member, house));
    }
}
