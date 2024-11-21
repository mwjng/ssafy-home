package ssafy.ssafyhome.like.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.like.application.response.LikeRegionResponse;
import ssafy.ssafyhome.like.application.response.LikeRegionsResponse;
import ssafy.ssafyhome.like.domain.LikeRegion;
import ssafy.ssafyhome.like.domain.repository.LikeRegionRepository;
import ssafy.ssafyhome.like.exception.LikeRegionException;
import ssafy.ssafyhome.like.presentation.request.CreateLikeRegionRequest;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.region.domain.Region;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeRegionServiceImpl implements LikeRegionService {

    private final LikeRegionRepository likeRegionRepository;

    public LikeRegionsResponse searchAll(final Long memberId, final int size, final Long cursorId) {
        PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        List<LikeRegionResponse> likeRegions = likeRegionRepository.searchAll(memberId, pageRequest, cursorId).stream()
                .map(LikeRegionResponse::from)
                .toList();

        return new LikeRegionsResponse(likeRegions);
    }

    @Transactional
    public void create(final Long memberId, final CreateLikeRegionRequest createLikeRegionRequest) {
        Member member = Member.withId(memberId);
        Region region = Region.create(
                createLikeRegionRequest.sido(),
                createLikeRegionRequest.gugun(),
                createLikeRegionRequest.dong());
        LikeRegion likeRegion = LikeRegion.create(region, member);
        likeRegionRepository.save(likeRegion);
    }

    @Transactional
    public void delete(final Long memberId, final Long likeRegionId) {
        Member member = likeRegionRepository.findMemberById(likeRegionId).orElseThrow(() -> new LikeRegionException(NOT_FOUND_LIKE_REGION));
        if (!member.getId().equals(memberId)) {
            throw new LikeRegionException(UNAUTHORIZED_LIKE_REGION_ACCESS);
        }
        likeRegionRepository.deleteById(likeRegionId);
    }
}
