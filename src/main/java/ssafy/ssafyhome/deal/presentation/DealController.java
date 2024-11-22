package ssafy.ssafyhome.deal.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AgentAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.deal.application.DealService;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static ssafy.ssafyhome.common.util.UrlUtil.getBaseUrl;

@RequiredArgsConstructor
@RequestMapping("/deals")
@RestController
public class DealController implements DealControllerDocs{

    private final DealService dealService;

    @GetMapping("/{houseId}")
    public ResponseEntity<DealsResponse> getDeals(
            @PathVariable final Long houseId,
            @RequestParam(defaultValue = "PENDING") final String dealStatus,
            @RequestParam(defaultValue = "SALE") final String dealType,
            @RequestParam(defaultValue = "APT") final String houseType,
            @PageableDefault final Pageable pageable,
            final HttpServletRequest request) {
        return null;
    }

    @PostMapping
    @AgentAccess
    public ResponseEntity<Void> createDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestPart final DealCreateRequest dealCreateRequest,
        @RequestPart final List<MultipartFile> images) {
        dealService.createDeal(accessContext.getMemberId(), dealCreateRequest, images);
        return ResponseEntity.status(CREATED).build();
    }

    // TODO update
    @PutMapping("/{dealId}")
    @AgentAccess
    public ResponseEntity<Void> updateDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long dealId,
        @RequestPart final DealUpdateRequest dealUpdateRequest,
        @RequestPart final List<MultipartFile> images
    ) {

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{dealId}")
    @AgentAccess
    public ResponseEntity<Void> deleteDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long dealId) {

        dealService.deleteDeal(dealId);
        return ResponseEntity.noContent().build();
    }
}
