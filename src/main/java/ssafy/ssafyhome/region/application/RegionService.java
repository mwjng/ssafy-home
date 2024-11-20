package ssafy.ssafyhome.region.application;

import ssafy.ssafyhome.region.application.response.RegionIdResponse;
import ssafy.ssafyhome.region.application.response.RegionSearchResponse;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

public interface RegionService {

    RegionSearchResponse search(final RegionSearchCondition regionSearchCondition);

    RegionIdResponse searchId(final String sido, final String gugun, final String dong);
}
