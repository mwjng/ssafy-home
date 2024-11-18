package ssafy.ssafyhome.follow.application.response;

import ssafy.ssafyhome.member.domain.MemberRole;

public record FollowingResponse(
        Long memberId,
        String name,
        String nickname,
        MemberRole role,
        String profileImageUrl) {
}
