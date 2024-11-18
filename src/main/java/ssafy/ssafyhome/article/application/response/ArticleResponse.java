package ssafy.ssafyhome.article.application.response;

public record ArticleResponse(Long articleId, Long memberId, String writer, String content) {
}
