package ssafy.ssafyhome.region.domain.repository;

import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

import java.util.List;

public interface RegionRepositoryCustom {
    List<String> findBySearchCondition(RegionSearchCondition regionSearchCondition);

    Long findIdBySidoAndGugunAndDong(String sido, String gugun, String dong);
}
