package ssafy.ssafyhome.member.application;

import ssafy.ssafyhome.member.application.response.FollowersResponse;
import ssafy.ssafyhome.member.application.response.FollowingsResponse;

public interface FollowService {
    FollowersResponse searchFollowers(final Long memberId, final int size, final Long cursorId, final String baseUrl);

    FollowingsResponse searchFollowings(final Long memberId, final int size, final Long cursorId, final String baseUrl);

    void deleteFollower(final Long memberId, final Long followId);

    void deleteFollowing(final Long memberId, final Long followId);

    void createFollow(Long followerId, Long followingId);
}
