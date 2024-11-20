package ssafy.ssafyhome.notice.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ssafy.ssafyhome.notice.application.response.NoticeQueryResponse;

public interface NoticeRepositoryCustom {
    Slice<NoticeQueryResponse> searchAll(final Long cursorId, final Pageable pageable);

    NoticeQueryResponse search(final Long noticeId);
}
