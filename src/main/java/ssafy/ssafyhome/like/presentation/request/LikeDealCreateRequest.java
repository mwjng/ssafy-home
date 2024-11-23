package ssafy.ssafyhome.like.presentation.request;

import jakarta.validation.constraints.NotNull;

public record LikeDealCreateRequest(
        @NotNull(message = "dealId 값을 입력해주세요.")
        Long dealId) {
}
