package ssafy.ssafyhome.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.member.application.DirectMessageService;
import ssafy.ssafyhome.member.application.response.*;
import ssafy.ssafyhome.member.presentation.request.SendMessageRequest;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/direct-messages")
@RestController
public class DirectMessageController implements DirectMessageControllerDocs{

    private final DirectMessageService directMessageService;

    @UserAccess
    @GetMapping("/received")
    public ResponseEntity<ReceivedMessagesResponse> searchReceivedMessages(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId) {
        ReceivedMessagesResponse response = directMessageService.searchReceivedMessages(accessContext.getMemberId(), size, cursorId);
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @GetMapping("/sent")
    public ResponseEntity<SentMessagesResponse> searchSentMessages(
            @AuthenticationPrincipal final AccessContext accessContext,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Long cursorId) {
        SentMessagesResponse response = directMessageService.searchSentMessages(accessContext.getMemberId(), size, cursorId);
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @GetMapping("/received/{directMessageId}")
    public ResponseEntity<ReceivedMessageResponse> searchReceivedMessage(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long directMessageId) {
        ReceivedMessageResponse response = directMessageService.searchReceivedMessage(accessContext.getMemberId(), directMessageId);
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @GetMapping("/sent/{directMessageId}")
    public ResponseEntity<SentMessageResponse> searchSentMessage(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long directMessageId) {
        SentMessageResponse response = directMessageService.searchSentMessage(accessContext.getMemberId(), directMessageId);
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @GetMapping("/search-unread")
    public ResponseEntity<UnreadMessageResponse> searchUnRead(@AuthenticationPrincipal final AccessContext accessContext) {
        UnreadMessageResponse response = directMessageService.searchUnRead(accessContext.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @UserAccess
    @PostMapping("/{receiverId}")
    public ResponseEntity<Void> send(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long receiverId,
            @RequestBody SendMessageRequest sendMessageRequest) {
        directMessageService.send(accessContext.getMemberId(), receiverId, sendMessageRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @UserAccess
    @DeleteMapping("/{directMessageId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long directMessageId) {
        directMessageService.delete(accessContext.getMemberId(), directMessageId);
        return ResponseEntity.noContent().build();
    }
}
