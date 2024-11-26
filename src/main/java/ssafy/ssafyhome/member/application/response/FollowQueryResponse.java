package ssafy.ssafyhome.member.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.member.domain.MemberRole;

public record FollowQueryResponse(
        Long followId,
        Long memberId,
        String nickname,
        MemberRole role,
        String dirName) {

    @QueryProjection
    public FollowQueryResponse {
    }
}
