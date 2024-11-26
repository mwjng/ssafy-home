package ssafy.ssafyhome.like.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.like.application.LikeDealServiceImpl;
import ssafy.ssafyhome.like.application.response.LikeDealsResponse;

import static org.springframework.http.HttpStatus.*;
import static ssafy.ssafyhome.common.util.UrlUtil.getBaseUrl;

@RequiredArgsConstructor
@RequestMapping("/deals/like")
@RestController
public class LikeDealController implements LikeDealControllerDocs{

    private final LikeDealServiceImpl likeDealService;

    @UserAccess
    @GetMapping
    public ResponseEntity<LikeDealsResponse> searchAll(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId,
            HttpServletRequest request) {

        LikeDealsResponse response = likeDealService.getLikeDeals(
                accessContext.getMemberId(),
                getBaseUrl(request),
                size,
                cursorId);
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @PostMapping("/{dealId}")
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long dealId) {
        likeDealService.create(accessContext.getMemberId(), dealId);
        return ResponseEntity.status(CREATED).build();
    }

    @UserAccess
    @DeleteMapping("/{dealId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long dealId) {
        likeDealService.delete(accessContext.getMemberId(), dealId);
        return ResponseEntity.noContent().build();
    }
}
