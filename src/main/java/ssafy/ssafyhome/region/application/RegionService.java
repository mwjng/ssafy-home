package ssafy.ssafyhome.region.application;

import ssafy.ssafyhome.region.application.response.RegionIdResponse;
import ssafy.ssafyhome.region.application.response.RegionSearchResponse;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

public interface RegionService {

    RegionSearchResponse search(RegionSearchCondition regionSearchCondition);

    RegionIdResponse searchId(String sido, String gugun, String dong);
}
