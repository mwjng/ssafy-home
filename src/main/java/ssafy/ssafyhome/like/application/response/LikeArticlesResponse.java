package ssafy.ssafyhome.like.application.response;

import ssafy.ssafyhome.article.application.response.ArticleResponse;

import java.util.List;

public record LikeArticlesResponse(List<ArticleResponse> likeArticles) {
}
