package ssafy.ssafyhome.article.application.response;

import java.util.List;

public record ArticleResponse(
        Long articleId,
        Long memberId,
        String writer,
        String content,
        List<String> imageUrl) {
}
