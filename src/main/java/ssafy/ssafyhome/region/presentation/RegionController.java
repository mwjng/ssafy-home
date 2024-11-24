package ssafy.ssafyhome.region.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.region.application.RegionService;
import ssafy.ssafyhome.region.application.response.RegionSearchResponse;

@RequiredArgsConstructor
@RequestMapping("/regions")
@RestController
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/sidos")
    public ResponseEntity<RegionSearchResponse> searchSidos() {
        return ResponseEntity.ok(regionService.searchSidos());
    }

    @GetMapping("/{sido}/guguns")
    public ResponseEntity<RegionSearchResponse> searchGuguns(@PathVariable final String sido) {
        return ResponseEntity.ok(regionService.searchGuguns(sido));
    }

    @GetMapping("/{sido}/{gugun}/dongs")
    public ResponseEntity<RegionSearchResponse> searchDongs(@PathVariable final String sido,
                                                            @PathVariable final String gugun) {
        return ResponseEntity.ok(regionService.searchDongs(sido, gugun));
    }
}
