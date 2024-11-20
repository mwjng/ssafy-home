package ssafy.ssafyhome.follow.application;

import ssafy.ssafyhome.follow.application.response.FollowersResponse;
import ssafy.ssafyhome.follow.application.response.FollowingsResponse;

public interface FollowService {
    FollowersResponse searchFollowers(final Long memberId, final int size, final Long cursorId, final String baseUrl);

    FollowingsResponse searchFollowings(final Long memberId, final int size, final Long cursorId, final String baseUrl);

    void deleteFollower(final Long memberId, final Long followId);

    void deleteFollowing(final Long memberId, final Long followId);

    void createFollow(Long followerId, Long followingId);
}
