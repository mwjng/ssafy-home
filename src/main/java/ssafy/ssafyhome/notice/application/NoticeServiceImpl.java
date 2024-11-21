package ssafy.ssafyhome.notice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.notice.application.response.NoticeResponse;
import ssafy.ssafyhome.notice.application.response.NoticesResponse;
import ssafy.ssafyhome.notice.domain.Notice;
import ssafy.ssafyhome.notice.domain.repository.NoticeRepository;
import ssafy.ssafyhome.notice.exception.NoticeException;
import ssafy.ssafyhome.notice.presentation.request.NoticeCreateRequest;
import ssafy.ssafyhome.notice.presentation.request.NoticeUpdateRequest;

import java.util.List;
import java.util.Optional;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_NOTICE;
import static ssafy.ssafyhome.common.exception.ErrorCode.UNAUTHORIZED_NOTICE_ACCESS;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticesResponse searchAll(final int size, final Long cursorId) {
        PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        List<NoticeResponse> notices = noticeRepository.searchAll(cursorId, pageRequest).stream()
                .map(NoticeResponse::from)
                .toList();

        return new NoticesResponse(notices);
    }

    public NoticeResponse search(final Long noticeId) {
        return Optional.ofNullable(noticeRepository.search(noticeId))
                .map(NoticeResponse::from)
                .orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE));
    }

    @Transactional
    public void create(final Long adminId, final NoticeCreateRequest noticeCreateRequest) {
        Notice notice = Notice.create(
                noticeCreateRequest.title(),
                noticeCreateRequest.content(),
                Member.withId(adminId)
        );
        noticeRepository.save(notice);
    }

    @Transactional
    public void update(final Long adminId, final Long noticeId, final NoticeUpdateRequest noticeUpdateRequest) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE));

        if(!notice.getMember().getId().equals(adminId)){
            throw new NoticeException(UNAUTHORIZED_NOTICE_ACCESS);
        }

        notice.changeTitle(noticeUpdateRequest.title());
        notice.changeContent(noticeUpdateRequest.content());
    }

    @Transactional
    public void delete(final Long adminId, final Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE));

        if(!notice.getMember().getId().equals(adminId)){
            throw new NoticeException(UNAUTHORIZED_NOTICE_ACCESS);
        }

        noticeRepository.deleteById(noticeId);
    }
}
