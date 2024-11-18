package ssafy.ssafyhome.region.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController implements RegionControllerDocs{

    @Override
    public ResponseEntity<List<String>> searchSido() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> searchGugun(final String sido) {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> searchDong(final String sido, final String gugun) {
        return null;
    }

    @Override
    public ResponseEntity<Long> searchRegionId(final String sido, final String gugun, final String dong) {
        return null;
    }
}
