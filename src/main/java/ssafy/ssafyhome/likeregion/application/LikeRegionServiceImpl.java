package ssafy.ssafyhome.likeregion.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionResponse;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;
import ssafy.ssafyhome.likeregion.domain.LikeRegion;
import ssafy.ssafyhome.likeregion.domain.repository.LikeRegionRepository;
import ssafy.ssafyhome.likeregion.exception.LikeRegionException;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.region.domain.Region;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeRegionServiceImpl implements LikeRegionService{

    private final LikeRegionRepository likeRegionRepository;

    @Override
    public LikeRegionsResponse searchAll(final Long memberId, final int size, final Long cursorId) {
        PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        List<LikeRegionResponse> likeRegions = likeRegionRepository.searchAll(memberId, pageRequest, cursorId).stream()
                .map(LikeRegionResponse::from)
                .toList();

        return new LikeRegionsResponse(likeRegions);
    }

    @Override
    @Transactional
    public void create(final Long memberId, final String sido, final String gugun, final String dong) {
        Member member = Member.withId(memberId);
        Region region = Region.create(sido, gugun, dong);
        LikeRegion likeRegion = LikeRegion.create(region, member);
        likeRegionRepository.save(likeRegion);
    }

    @Override
    @Transactional
    public void delete(final Long likeRegionId) {
        if(!likeRegionRepository.existsById(likeRegionId)){
            throw new LikeRegionException(NOT_FOUND_LIKE_REGION);
        }
        likeRegionRepository.deleteById(likeRegionId);
    }
}
