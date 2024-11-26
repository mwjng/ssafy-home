package ssafy.ssafyhome.article.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record ArticleCreateRequest(
    @NotBlank(message = "내용을 입력해주세요.")
    String content
) {
}
