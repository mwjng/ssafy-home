package ssafy.ssafyhome.notice.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AdminAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.notice.application.NoticeService;
import ssafy.ssafyhome.notice.application.response.NoticeResponse;
import ssafy.ssafyhome.notice.application.response.NoticesResponse;
import ssafy.ssafyhome.notice.presentation.request.NoticeCreateRequest;
import ssafy.ssafyhome.notice.presentation.request.NoticeUpdateRequest;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/notices")
@RestController
public class NoticeController implements NoticeControllerDocs {

    private final NoticeService noticeService;

    @Override
    @GetMapping
    public ResponseEntity<NoticesResponse> searchAll(
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId) {
        NoticesResponse response = noticeService.searchAll(size, cursorId);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> search(@PathVariable final Long id) {
        NoticeResponse response = noticeService.search(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @AdminAccess
    @PostMapping
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal final AccessContext accessContext,
            @Valid @RequestBody final NoticeCreateRequest noticeCreateRequest) {
        noticeService.create(accessContext.getMemberId(), noticeCreateRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    @AdminAccess
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long id,
            @Valid @RequestBody final NoticeUpdateRequest noticeUpdateRequest) {
        noticeService.update(id, noticeUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    @AdminAccess
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        noticeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
