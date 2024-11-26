package ssafy.ssafyhome.member.presentation.request;

import jakarta.validation.constraints.NotNull;

public record FollowCreateRequest(
        @NotNull(message = "id값을 입력해주세요.")
        Long memberId) {
}
