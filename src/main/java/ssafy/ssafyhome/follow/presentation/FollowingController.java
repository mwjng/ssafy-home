package ssafy.ssafyhome.follow.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.follow.application.FollowService;
import ssafy.ssafyhome.follow.application.response.FollowingsResponse;

import static org.springframework.http.HttpStatus.*;
import static ssafy.ssafyhome.common.util.UrlUtil.*;

@RequiredArgsConstructor
@RequestMapping("/followings")
@RestController
public class FollowingController implements FollowingControllerDocs{

    private final FollowService followService;

    @Override
    @UserAccess
    @GetMapping
    public ResponseEntity<FollowingsResponse> searchFollowings(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId,
            HttpServletRequest request) {
        FollowingsResponse response = followService.searchFollowings(accessContext.getMemberId(), size, cursorId, getBaseUrl(request));
        return ResponseEntity.ok().body(response);
    }

    @Override
    @UserAccess
    @PostMapping("/{memberId}")
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long memberId) {
        followService.createFollow(accessContext.getMemberId(), memberId);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    @UserAccess
    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long followId) {
        followService.deleteFollowing(accessContext.getMemberId(), followId);
        return ResponseEntity.noContent().build();
    }
}
