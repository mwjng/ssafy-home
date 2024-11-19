package ssafy.ssafyhome.region.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.region.application.response.RegionIdResponse;
import ssafy.ssafyhome.region.application.response.RegionResponse;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

@RestController
@RequestMapping("/regions")
public class RegionController implements RegionControllerDocs{

    @Override
    public ResponseEntity<RegionResponse> search(final RegionSearchCondition regionSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<RegionIdResponse> searchId(final String sido, final String gugun, final String dong) {
        return null;
    }
}
