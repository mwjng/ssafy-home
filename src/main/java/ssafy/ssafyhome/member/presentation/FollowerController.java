package ssafy.ssafyhome.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.member.application.FollowService;
import ssafy.ssafyhome.member.application.response.FollowersResponse;

import static ssafy.ssafyhome.common.util.UrlUtil.*;

@RequiredArgsConstructor
@RequestMapping("/followers")
@RestController
public class FollowerController implements FollowerControllerDocs{

    private final FollowService followService;

    @UserAccess
    @GetMapping
    public ResponseEntity<FollowersResponse> searchFollowers(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId,
            HttpServletRequest request) {

        FollowersResponse response = followService.searchFollowers(accessContext.getMemberId(), size, cursorId, getBaseUrl(request));
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long followId) {
        followService.deleteFollower(accessContext.getMemberId(), followId);
        return ResponseEntity.noContent().build();
    }
}
