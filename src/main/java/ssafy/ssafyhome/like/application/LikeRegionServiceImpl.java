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
        final PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        final List<LikeRegionResponse> likeRegions = likeRegionQueryRepository.searchAll(memberId, pageRequest, cursorId).stream()
                .map(LikeRegionResponse::from)
                .toList();

        return new LikeRegionsResponse(likeRegions);
    }

    @Transactional
    public void create(final Long memberId, final LikeRegionCreateRequest likeRegionCreateRequest) {
        final Region region = regionRepository.findBySidoAndGugunAndDong(
                likeRegionCreateRequest.sido(),
                likeRegionCreateRequest.gugun(),
                likeRegionCreateRequest.dong()).orElseThrow(() -> new RegionException(NOT_FOUND_REGION));

        if(likeRegionRepository.existsByRegionIdAndMemberId(region.getId(), memberId)){
            throw new LikeRegionException(DUPLICATED_LIKE_REGION);
        }

        likeRegionRepository.save(LikeRegion.create(region, Member.withId(memberId)));
    }

    @Transactional
    public void delete(final Long memberId, final Long likeRegionId) {
        final LikeRegion likeRegion = likeRegionRepository.findById(likeRegionId)
                .orElseThrow(() -> new LikeRegionException(NOT_FOUND_LIKE_REGION));

        if (!likeRegion.getMember().getId().equals(memberId)) {
            throw new LikeRegionException(UNAUTHORIZED_LIKE_REGION_ACCESS);
        }

        likeRegionRepository.deleteById(likeRegionId);
    }
}
