package ssafy.ssafyhome.region.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.region.application.response.RegionSearchResponse;
import ssafy.ssafyhome.region.domain.repository.RegionRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionSearchResponse searchSidos() {
        return new RegionSearchResponse(regionRepository.findAllSidos());
    }

    public RegionSearchResponse searchGuguns(final String sido) {
        return new RegionSearchResponse(regionRepository.findGugunsBySido(sido));
    }

    public RegionSearchResponse searchDongs(final String sido, final String gugun) {
        return new RegionSearchResponse(regionRepository.findDongsBySidoAndGuguns(sido, gugun));
    }
}
