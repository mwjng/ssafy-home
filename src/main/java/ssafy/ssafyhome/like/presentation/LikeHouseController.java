package ssafy.ssafyhome.like.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.like.application.LikeHouseService;
import ssafy.ssafyhome.like.application.response.LikeHousesResponse;

import static org.springframework.http.HttpStatus.*;
import static ssafy.ssafyhome.common.util.UrlUtil.*;

@RequiredArgsConstructor
@RequestMapping("/houses/like")
@RestController
public class LikeHouseController implements LikeHouseControllerDocs{

    private final LikeHouseService likeHouseService;

    @UserAccess
    @GetMapping
    public ResponseEntity<LikeHousesResponse> searchAll(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId,
            HttpServletRequest request) {
        LikeHousesResponse response = likeHouseService.searchAll(accessContext.getMemberId(), size, cursorId, getBaseUrl(request));
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @PostMapping("/{houseId}")
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long houseId) {
        likeHouseService.create(accessContext.getMemberId(), houseId);
        return ResponseEntity.status(CREATED).build();
    }

    @UserAccess
    @DeleteMapping("/{houseId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long houseId) {
        likeHouseService.delete(accessContext.getMemberId(), houseId);
        return ResponseEntity.noContent().build();
    }
}
