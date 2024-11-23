package ssafy.ssafyhome.like.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.like.application.LikeRegionService;
import ssafy.ssafyhome.like.application.response.LikeRegionsResponse;
import ssafy.ssafyhome.like.presentation.request.LikeRegionCreateRequest;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/regions/like")
@RestController
public class LikeRegionController implements LikeRegionControllerDocs {

    private final LikeRegionService likeRegionService;

    @UserAccess
    @GetMapping
    public ResponseEntity<LikeRegionsResponse> searchAll(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId) {

        LikeRegionsResponse response = likeRegionService.searchAll(
                accessContext.getMemberId(),
                size,
                cursorId);

        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @PostMapping
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal final AccessContext accessContext,
            @Valid @RequestBody LikeRegionCreateRequest likeRegionCreateRequest) {

        likeRegionService.create(accessContext.getMemberId(), likeRegionCreateRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @UserAccess
    @DeleteMapping("/{likeRegionId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long likeRegionId) {

        likeRegionService.delete(accessContext.getMemberId(), likeRegionId);
        return ResponseEntity.noContent().build();
    }
}
