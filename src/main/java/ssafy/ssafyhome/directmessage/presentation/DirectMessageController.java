package ssafy.ssafyhome.directmessage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.directmessage.application.response.DirectMessageResponse;
import ssafy.ssafyhome.directmessage.application.response.ReceivedMessagesResponse;
import ssafy.ssafyhome.directmessage.application.response.SentMessagesResponse;
import ssafy.ssafyhome.directmessage.application.response.UnreadMessageResponse;

@RequiredArgsConstructor
@RequestMapping("/direct-messages")
@RestController
public class DirectMessageController implements DirectMessageControllerDocs{

    @Override
    @UserAccess
    @GetMapping("/received")
    public ResponseEntity<ReceivedMessagesResponse> searchReceivedMessages(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    @UserAccess
    @GetMapping("/sent")
    public ResponseEntity<SentMessagesResponse> searchSentMessages(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    @UserAccess
    @GetMapping("/{directMessageId}")
    public ResponseEntity<DirectMessageResponse> search(final AccessContext accessContext, final Long directMessageId) {
        return null;
    }

    @Override
    @UserAccess
    @GetMapping("/search-unread")
    public ResponseEntity<UnreadMessageResponse> searchUnRead(final AccessContext accessContext) {
        return null;
    }

    @Override
    @UserAccess
    @PostMapping("/{receiverId}")
    public ResponseEntity<Void> send(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long receiverId,
            @RequestBody final String content) {
        return null;
    }

    @Override
    @UserAccess
    @DeleteMapping("/{directMessageId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @PathVariable final Long directMessageId) {
        return null;
    }
}
