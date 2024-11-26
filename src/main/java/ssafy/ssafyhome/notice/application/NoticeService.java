package ssafy.ssafyhome.notice.application;

import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.notice.application.response.NoticeResponse;
import ssafy.ssafyhome.notice.application.response.NoticesResponse;
import ssafy.ssafyhome.notice.presentation.request.NoticeCreateRequest;
import ssafy.ssafyhome.notice.presentation.request.NoticeUpdateRequest;

public interface NoticeService {
    NoticesResponse searchAll(final int size, final Long cursorId);

    NoticeResponse search(final Long noticeId);

    void create(final Long adminId, final NoticeCreateRequest noticeCreateRequest);

    void update(final Long adminId, final Long noticeId, final NoticeUpdateRequest noticeUpdateRequest);

    void delete(final AccessContext accessContext, final Long noticeId);
}
