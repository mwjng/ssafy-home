package ssafy.ssafyhome.article.application.response;

import ssafy.ssafyhome.article.domain.Article;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(
        Long articleId,
        Long houseId,
        Long memberId,
        String houseName,
        String nickname,
        String content,
        Long likeCount,
        Long commentCount,
        List<String> imageUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleResponse of(Article article, Long likeCount, Long commentCount, List<String> imageUrl) {
        return new ArticleResponse(
            article.getId(),
            article.getHouse().getId(),
            article.getMember().getId(),
            article.getHouse().getName(),
            article.getMember().getNickname(),
            article.getContent(),
            likeCount,
            commentCount,
            imageUrl,
            article.getCreatedAt(),
            article.getModifiedAt()
        );
    }
}
