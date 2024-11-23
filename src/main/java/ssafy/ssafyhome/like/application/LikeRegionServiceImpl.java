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
import ssafy.ssafyhome.like.infrastructure.LikeRegionQueryRepository;
import ssafy.ssafyhome.like.presentation.request.LikeRegionCreateRequest;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.region.application.RegionService;
import ssafy.ssafyhome.region.domain.Region;
import ssafy.ssafyhome.region.domain.repository.RegionRepository;
import ssafy.ssafyhome.region.exception.RegionException;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeRegionServiceImpl implements LikeRegionService {

    private final RegionRepository regionRepository;
    private final LikeRegionRepository likeRegionRepository;
    private final LikeRegionQueryRepository likeRegionQueryRepository;

    public LikeRegionsResponse searchAll(final Long memberId, final int size, final Long cursorId) {
        PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        List<LikeRegionResponse> likeRegions = likeRegionQueryRepository.searchAll(memberId, pageRequest, cursorId).stream()
                .map(LikeRegionResponse::from)
                .toList();

        return new LikeRegionsResponse(likeRegions);
    }

    @Transactional
    public void create(final Long memberId, final LikeRegionCreateRequest likeRegionCreateRequest) {
        Region region = regionRepository.findBySidoAndGugunAndDong(
                likeRegionCreateRequest.sido(),
                likeRegionCreateRequest.gugun(),
                likeRegionCreateRequest.dong()).orElseThrow(() -> new RegionException(NOT_FOUND_REGION));

        if(likeRegionRepository.existsByRegionAndMemberId(region, memberId)){
            throw new LikeRegionException(DUPLICATED_LIKE_REGION);
        }

        likeRegionRepository.save(LikeRegion.create(region, Member.withId(memberId)));
    }

    @Transactional
    public void delete(final Long memberId, final Long likeRegionId) {
        Member member = likeRegionRepository.findMemberById(likeRegionId)
                .orElseThrow(() -> new LikeRegionException(NOT_FOUND_LIKE_REGION));

        if (!member.getId().equals(memberId)) {
            throw new LikeRegionException(UNAUTHORIZED_LIKE_REGION_ACCESS);
        }

        likeRegionRepository.deleteById(likeRegionId);
    }
}
