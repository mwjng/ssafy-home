package ssafy.ssafyhome.comment.application.response;

import java.time.LocalDateTime;

public record CommentResponse(
    Long commentId,
    Long memberId,
    String content,
    Long articleId,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {
}
