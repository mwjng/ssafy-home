package ssafy.ssafyhome.follow.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.follow.application.response.FollowerResponse;
import ssafy.ssafyhome.follow.application.response.FollowersResponse;
import ssafy.ssafyhome.follow.application.response.FollowingResponse;
import ssafy.ssafyhome.follow.application.response.FollowingsResponse;
import ssafy.ssafyhome.follow.domain.Follow;
import ssafy.ssafyhome.follow.domain.repository.FollowRepository;
import ssafy.ssafyhome.follow.exception.FollowException;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.exception.MemberException;

import java.util.List;
import java.util.Objects;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.defaultSort;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;

    private static final String PROFILE_IMG_DIR = "member/";

    public FollowersResponse searchFollowers(final Long memberId, final int size, final Long cursorId, final String baseUrl) {
        PageRequest pageRequest = createPageRequest(size);
        List<FollowerResponse> followers = followRepository.searchFollowers(memberId, pageRequest, cursorId)
                .stream()
                .map(q -> {
                    String dirName = q.dirName();
                    List<String> imageFilePaths = imageService.getImageFilePaths(dirName, PROFILE_IMG_DIR);
                    List<String> imageUrl = imageService.getImageUrlList(baseUrl, PROFILE_IMG_DIR, imageFilePaths, dirName);
                    return FollowerResponse.from(q, imageUrl);
                })
                .toList();
        return new FollowersResponse(followers);
    }

    public FollowingsResponse searchFollowings(final Long memberId, final int size, final Long cursorId, final String baseUrl) {
        PageRequest pageRequest = createPageRequest(size);
        List<FollowingResponse> followings = followRepository.searchFollowings(memberId, pageRequest, cursorId)
                .stream()
                .map(q -> {
                    String dirName = q.dirName();
                    List<String> imageFilePaths = imageService.getImageFilePaths(dirName, PROFILE_IMG_DIR);
                    List<String> imageUrl = imageService.getImageUrlList(baseUrl, PROFILE_IMG_DIR, imageFilePaths, dirName);
                    return FollowingResponse.from(q, imageUrl);
                })
                .toList();
        return new FollowingsResponse(followings);
    }

    @Transactional
    public void deleteFollower(final Long memberId, final Long followId) {
        Member follower = followRepository.findFollowerById(followId).orElseThrow(() -> new FollowException(NOT_FOUND_FOLLOW));

        if(!follower.getId().equals(memberId)){
            throw new FollowException(UNAUTHORIZED_FOLLOW_ACCESS);
        }

        followRepository.deleteById(followId);
    }

    @Transactional
    public void deleteFollowing(final Long memberId, final Long followId) {
        Member following = followRepository.findFollowingById(followId).orElseThrow(() -> new FollowException(NOT_FOUND_FOLLOW));

        if(!following.getId().equals(memberId)){
            throw new FollowException(UNAUTHORIZED_FOLLOW_ACCESS);
        }

        followRepository.deleteById(followId);
    }

    @Transactional
    public void createFollow(final Long followerId, final Long followingId) {
        if(Objects.equals(followerId, followingId)){
            throw new FollowException(INVALID_SELF_FOLLOW_REQUEST);
        }

        if(!memberRepository.existsById(followerId) || !memberRepository.existsById(followingId)){
            throw new MemberException(NOT_FOUND_USER_ID);
        }

        Member follower = Member.withId(followerId);
        Member following = Member.withId(followingId);
        Follow follow = Follow.create(follower, following);
        followRepository.save(follow);
    }

    private PageRequest createPageRequest(final int size) {
        return PageRequest.of(0, size, defaultSort());
    }
}
