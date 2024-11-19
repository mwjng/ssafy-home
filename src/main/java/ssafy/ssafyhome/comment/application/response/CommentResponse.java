package ssafy.ssafyhome.comment.application.response;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        Long memberId,
        String writer,
        String content,
        Long articleId,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
}
