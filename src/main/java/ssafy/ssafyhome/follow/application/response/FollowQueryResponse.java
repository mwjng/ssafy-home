package ssafy.ssafyhome.follow.application.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import ssafy.ssafyhome.member.domain.MemberRole;

@Getter
public class FollowQueryResponse {
    private Long followId;
    private Long memberId;
    private String nickname;
    private MemberRole role;
    private String dirName;

    @QueryProjection
    public FollowQueryResponse(final Long followId, final Long memberId, final String nickname, final MemberRole role, final String dirName) {
        this.followId = followId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.role = role;
        this.dirName = dirName;
    }
}
