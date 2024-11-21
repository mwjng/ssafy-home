package ssafy.ssafyhome.deal.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AgentAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.deal.application.DealService;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/deals")
@RestController
public class DealController implements DealControllerDocs{

    private final DealService dealService;

    @PostMapping
    @AgentAccess
    public ResponseEntity<Void> createDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestPart final DealCreateRequest dealCreateRequest,
        @RequestPart final List<MultipartFile> images
    ) {
        dealService.createDeal(accessContext.getMemberId(), dealCreateRequest, images);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{dealId}")
    @AgentAccess
    public ResponseEntity<Void> updateDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long dealId,
        @RequestPart final DealUpdateRequest dealUpdateRequest,
        @RequestPart final List<MultipartFile> images
    ) {
        return null;
    }

    @DeleteMapping("/{dealId}")
    @AgentAccess
    public ResponseEntity<Void> deleteDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long dealId
    ) {
        return null;
    }
}
