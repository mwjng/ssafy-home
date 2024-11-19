package ssafy.ssafyhome.region.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.region.application.response.DongsResponse;
import ssafy.ssafyhome.region.application.response.GugunsResponse;
import ssafy.ssafyhome.region.application.response.RegionIdResponse;
import ssafy.ssafyhome.region.application.response.SidosResponse;

import java.util.List;

@RestController
@RequestMapping("/regions")
public class RegionController implements RegionControllerDocs{

    @Override
    public ResponseEntity<SidosResponse> searchSidos() {
        return null;
    }

    @Override
    public ResponseEntity<GugunsResponse> searchGuguns(final String sido) {
        return null;
    }

    @Override
    public ResponseEntity<DongsResponse> searchDongs(final String sido, final String gugun) {
        return null;
    }

    @Override
    public ResponseEntity<RegionIdResponse> searchRegionId(final String sido, final String gugun, final String dong) {
        return null;
    }
}
