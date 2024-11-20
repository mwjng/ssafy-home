package ssafy.ssafyhome.follow.application.response;

import ssafy.ssafyhome.member.domain.MemberRole;

import java.util.List;

public record FollowerResponse(
        Long followId,
        Long memberId,
        String nickname,
        MemberRole role,
        List<String> imageUrl) {

    public static FollowerResponse from(FollowQueryResponse queryResponse, List<String> imageUrl){
        return new FollowerResponse(
                queryResponse.getFollowId(),
                queryResponse.getMemberId(),
                queryResponse.getNickname(),
                queryResponse.getRole(),
                imageUrl
        );
    }
}
