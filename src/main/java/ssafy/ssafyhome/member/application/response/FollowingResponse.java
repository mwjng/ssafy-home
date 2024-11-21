package ssafy.ssafyhome.member.application.response;

import ssafy.ssafyhome.member.domain.MemberRole;

import java.util.List;

public record FollowingResponse(
        Long followId,
        Long memberId,
        String nickname,
        MemberRole role,
        List<String> imageUrl) {

    public static FollowingResponse from(FollowQueryResponse queryResponse, List<String> imageUrl){
        return new FollowingResponse(
                queryResponse.followId(),
                queryResponse.memberId(),
                queryResponse.nickname(),
                queryResponse.role(),
                imageUrl
        );
    }
}