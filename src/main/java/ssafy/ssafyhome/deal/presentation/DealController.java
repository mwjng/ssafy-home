package ssafy.ssafyhome.deal.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AgentAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.deal.application.DealService;
import ssafy.ssafyhome.deal.application.request.DealCondition;
import ssafy.ssafyhome.deal.application.response.AverageResponses;
import ssafy.ssafyhome.deal.application.response.DealResponse;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.presentation.request.DealSearchCondition;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;

import java.util.List;

import static ssafy.ssafyhome.common.util.UrlUtil.getBaseUrl;

@RequiredArgsConstructor
@RequestMapping("/deals")
@RestController
public class DealController implements DealControllerDocs{

    private final DealService dealService;

    @GetMapping("/house/{houseId}")
    public ResponseEntity<DealsResponse> getDeals(
            @PathVariable final Long houseId,
            @ModelAttribute DealSearchCondition dealSearchCondition,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId,
            final HttpServletRequest request) {

        DealsResponse response = dealService.getDealsByHouseId(
                houseId,
                null,
                DealCondition.from(dealSearchCondition),
                size,
                cursorId,
                getBaseUrl(request));
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @GetMapping("/house/login/{houseId}")
    public ResponseEntity<DealsResponse> getDealsOnLogin(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long houseId,
            @ModelAttribute DealSearchCondition dealSearchCondition,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId,
            final HttpServletRequest request) {

        Long memberId = getMemberId(accessContext);

        DealsResponse response = dealService.getDealsByHouseId(
                houseId,
                memberId,
                DealCondition.from(dealSearchCondition),
                size,
                cursorId,
                getBaseUrl(request));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{dealId}")
    public ResponseEntity<DealResponse> searchDeal(
            @PathVariable final Long dealId,
            HttpServletRequest request){
        DealResponse response = dealService.getDealByDealId(dealId, null, getBaseUrl(request));
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @GetMapping("/login/{dealId}")
    public ResponseEntity<DealResponse> searchDealOnLogin(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long dealId,
            HttpServletRequest request
        ){
        DealResponse response = dealService.getDealByDealId(dealId, accessContext.getMemberId(), getBaseUrl(request));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/average/{houseId}")
    public ResponseEntity<AverageResponses> getAverage(@PathVariable final Long houseId){
        AverageResponses response = dealService.getAverageByHouseId(houseId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{dealId}")
    @AgentAccess
    public ResponseEntity<Void> updateDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long dealId,
        @RequestPart final DealUpdateRequest dealUpdateRequest,
        @RequestPart(required = false) final List<MultipartFile> images) {

        dealService.updateDeal(accessContext.getMemberId(), dealId, dealUpdateRequest, images);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{dealId}")
    @AgentAccess
    public ResponseEntity<Void> deleteDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long dealId) {

        dealService.deleteDeal(accessContext, dealId);
        return ResponseEntity.noContent().build();
    }

    private Long getMemberId(final AccessContext accessContext) {
        return accessContext != null ? accessContext.getMemberId() : null;
    }
}
