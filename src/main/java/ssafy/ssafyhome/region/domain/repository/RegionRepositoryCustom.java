package ssafy.ssafyhome.region.domain.repository;

import ssafy.ssafyhome.region.domain.Region;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

import java.util.List;
import java.util.Optional;

public interface RegionRepositoryCustom {

    List<String> findBySearchCondition(RegionSearchCondition regionSearchCondition);

    Optional<Region> findBySidoAndGugunAndDong(String sido, String gugun, String dong);
}
