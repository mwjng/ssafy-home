package ssafy.ssafyhome.follow.application.response;

import ssafy.ssafyhome.member.domain.MemberRole;

public record FollowerResponse(
        Long memberId,
        String name,
        String nickname,
        MemberRole role,
        String imageUrl) {
}
