package ssafy.ssafyhome.comment.application.response;

import java.time.LocalDateTime;

public record CommentResponse(Long commentId, String writer, String content, LocalDateTime createdAt) {
}
