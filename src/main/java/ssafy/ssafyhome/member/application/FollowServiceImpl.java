package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.member.application.response.*;
import ssafy.ssafyhome.member.domain.Follow;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.FollowRepository;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.exception.FollowException;
import ssafy.ssafyhome.member.exception.MemberException;
import ssafy.ssafyhome.member.infrastructure.FollowQueryRepository;

import java.util.List;
import java.util.Objects;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.defaultSort;
import static ssafy.ssafyhome.image.application.ImageDirectory.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final FollowQueryRepository followQueryRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;

    public FollowersResponse searchFollowers(final Long memberId, final int size, final Long cursorId, final String baseUrl) {
        final PageRequest pageRequest = createPageRequest(size);
        final List<FollowerResponse> followers = followQueryRepository.searchFollowers(memberId, pageRequest, cursorId)
                .stream()
                .map(follower -> getFollowerResponse(baseUrl, follower))
                .toList();
        return new FollowersResponse(followers);
    }

    public FollowingsResponse searchFollowings(final Long memberId, final int size, final Long cursorId, final String baseUrl) {
        final PageRequest pageRequest = createPageRequest(size);
        final List<FollowingResponse> followings = followQueryRepository.searchFollowings(memberId, pageRequest, cursorId)
                .stream()
                .map(following -> getFollowingResponse(baseUrl, following))
                .toList();
        return new FollowingsResponse(followings);
    }

    @Transactional
    public void deleteFollower(final Long memberId, final Long followId) {
        final Follow follow = followRepository.findById(followId).orElseThrow(() -> new FollowException(NOT_FOUND_FOLLOWER));

        if(!follow.getFollower().getId().equals(memberId)){
            throw new FollowException(UNAUTHORIZED_FOLLOW_ACCESS);
        }

        followRepository.deleteById(followId);
    }

    @Transactional
    public void deleteFollowing(final Long memberId, final Long followId) {
        final Follow follow = followRepository.findById(followId).orElseThrow(() -> new FollowException(NOT_FOUND_FOLLOWING));

        if(!follow.getFollowing().getId().equals(memberId)){
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

        if(followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)){
            throw new FollowException(DUPLICATED_FOLLOWING);
        }

        followRepository.save(makeFollow(followerId, followingId));
    }

    private FollowerResponse getFollowerResponse(final String baseUrl, final FollowQueryResponse follower) {
        final String dirName = follower.dirName();
        final List<String> imageFileNames = getFileNames(dirName);
        final List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
        return FollowerResponse.from(follower, imageUrl);
    }

    private FollowingResponse getFollowingResponse(final String baseUrl, final FollowQueryResponse following) {
        final String dirName = following.dirName();
        final List<String> imageFileNames = getFileNames(dirName);
        final List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
        return FollowingResponse.from(following, imageUrl);
    }

    private List<String> getFileNames(final String dirName) {
        return imageService.getImageFileNames(dirName, PROFILE.getDirectory());
    }

    private List<String> getImageUrl(final String baseUrl, final List<String> imageFileNames, final String dirName) {
        return imageService.getImageUrlList(baseUrl, PROFILE.getDirectory(), imageFileNames, dirName);
    }

    private Follow makeFollow(final Long followerId, final Long followingId) {
        return Follow.create(Member.withId(followerId), Member.withId(followingId));
    }

    private PageRequest createPageRequest(final int size) {
        return PageRequest.of(0, size, defaultSort());
    }
}