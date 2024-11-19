package ssafy.ssafyhome.region.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.region.application.RegionService;
import ssafy.ssafyhome.region.application.response.RegionIdResponse;
import ssafy.ssafyhome.region.application.response.RegionSearchResponse;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

@RequiredArgsConstructor
@RequestMapping("/regions")
@RestController
public class RegionController implements RegionControllerDocs{

    private final RegionService regionService;

    @Override
    @GetMapping
    public ResponseEntity<RegionSearchResponse> search(final RegionSearchCondition regionSearchCondition) {
        final RegionSearchResponse response = regionService.search(regionSearchCondition);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/id")
    public ResponseEntity<RegionIdResponse> searchId(
            @RequestParam final String sido,
            @RequestParam final String gugun,
            @RequestParam final String dong) {
        final RegionIdResponse response = regionService.searchId(sido, gugun, dong);
        return ResponseEntity.ok().body(response);
    }
}
