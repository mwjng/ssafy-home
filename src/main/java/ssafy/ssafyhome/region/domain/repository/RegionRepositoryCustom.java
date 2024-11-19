package ssafy.ssafyhome.region.domain.repository;

import ssafy.ssafyhome.region.application.response.RegionResponse;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;


public interface RegionRepositoryCustom {
    RegionResponse findBySearchCondition(RegionSearchCondition regionSearchCondition);
}
