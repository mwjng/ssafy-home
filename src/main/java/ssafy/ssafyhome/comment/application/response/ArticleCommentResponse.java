package ssafy.ssafyhome.comment.application.response;

import java.time.LocalDateTime;

public record ArticleCommentResponse(
    Long commentId,
    Long memberId,
    String nickname,
    String content,
    Long articleId,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {
}
