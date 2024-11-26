package ssafy.ssafyhome.comment.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(
    @NotBlank(message = "내용을 입력하세요.")
    String content
) {
}
