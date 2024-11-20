package ssafy.ssafyhome.likeregion.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.likeregion.application.LikeRegionService;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/regions/like")
@RestController
public class LikeRegionController implements LikeRegionControllerDocs {

    private final LikeRegionService likeRegionService;

    @Override
    @UserAccess
    @GetMapping
    public ResponseEntity<LikeRegionsResponse> searchAll(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId) {
        LikeRegionsResponse response = likeRegionService.searchAll(accessContext.getMemberId(), size, cursorId);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @UserAccess
    @PostMapping
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam final String sido,
            @RequestParam final String gugun,
            @RequestParam final String dong) {
        likeRegionService.create(accessContext.getMemberId(), sido, gugun, dong);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    @UserAccess
    @DeleteMapping("/{likeRegionId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal final AccessContext accessContext, @PathVariable final Long likeRegionId) {
        likeRegionService.delete(likeRegionId);
        return ResponseEntity.noContent().build();
    }
}
