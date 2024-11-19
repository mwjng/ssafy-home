package ssafy.ssafyhome.region.application;

import ssafy.ssafyhome.region.application.response.RegionIdResponse;
import ssafy.ssafyhome.region.application.response.RegionResponse;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

public interface RegionService {

    RegionResponse search(RegionSearchCondition regionSearchCondition);

    RegionIdResponse searchId(String sido, String gugun, String dong);
}
