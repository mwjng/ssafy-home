package ssafy.ssafyhome.notice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.notice.application.response.NoticeResponse;
import ssafy.ssafyhome.notice.application.response.NoticesResponse;
import ssafy.ssafyhome.notice.presentation.request.NoticeCreateRequest;
import ssafy.ssafyhome.notice.presentation.request.NoticeUpdateRequest;

@RestController
@RequestMapping("/notice")
public class NoticeController implements NoticeControllerDocs{
    @Override
    public ResponseEntity<NoticesResponse> searchAll(final int size, final Long cursorId) {
        return null;
    }

    @Override
    public ResponseEntity<NoticeResponse> search(final Long noticeId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> create(final AccessContext accessContext, final NoticeCreateRequest noticeCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(final AccessContext accessContext, final NoticeUpdateRequest noticeUpdateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext) {
        return null;
    }
}
