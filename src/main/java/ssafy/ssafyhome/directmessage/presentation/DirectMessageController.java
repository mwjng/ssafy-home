package ssafy.ssafyhome.directmessage.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.directmessage.application.response.DirectMessageResponse;
import ssafy.ssafyhome.directmessage.application.response.DirectMessagesResponse;

@RestController
@RequestMapping("/direct-messages")
public class DirectMessageController implements DirectMessageControllerDocs{

    @Override
    @UserAccess
    public ResponseEntity<DirectMessagesResponse> searchAll(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<DirectMessageResponse> search(final AccessContext accessContext, final Long directMessageId) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Boolean> searchUnRead(final AccessContext accessContext) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> send(final AccessContext accessContext, final Long receiverId, final String content) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long directMessageId) {
        return null;
    }
}
