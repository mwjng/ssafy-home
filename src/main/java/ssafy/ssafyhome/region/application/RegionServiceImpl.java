package ssafy.ssafyhome.region.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.region.application.response.RegionIdResponse;
import ssafy.ssafyhome.region.application.response.RegionResponse;
import ssafy.ssafyhome.region.application.response.RegionSearchResponse;
import ssafy.ssafyhome.region.domain.repository.RegionRepository;
import ssafy.ssafyhome.region.exception.RegionException;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

import java.util.Optional;

import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_REGION;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public RegionSearchResponse search(final RegionSearchCondition regionSearchCondition) {
//        return new RegionSearchResponse(regionRepository.findBySearchCondition(regionSearchCondition));
        return null;
    }

    @Override
    public RegionIdResponse searchId(final String sido, final String gugun, final String dong) {
//        Long id = Optional.ofNullable(regionRepository.findIdBySidoAndGugunAndDong(sido, gugun, dong))
//                .orElseThrow(() -> new RegionException(INVALID_REGION));

        return null;
    }
}
