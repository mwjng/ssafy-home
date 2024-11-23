package ssafy.ssafyhome.notice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.domain.Authority;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.notice.application.response.NoticeResponse;
import ssafy.ssafyhome.notice.application.response.NoticesResponse;
import ssafy.ssafyhome.notice.domain.Notice;
import ssafy.ssafyhome.notice.domain.repository.NoticeRepository;
import ssafy.ssafyhome.notice.exception.NoticeException;
import ssafy.ssafyhome.notice.infrastructure.NoticeQueryRepository;
import ssafy.ssafyhome.notice.presentation.request.NoticeCreateRequest;
import ssafy.ssafyhome.notice.presentation.request.NoticeUpdateRequest;

import java.util.List;

import static ssafy.ssafyhome.auth.domain.Authority.*;
import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_NOTICE;
import static ssafy.ssafyhome.common.exception.ErrorCode.UNAUTHORIZED_NOTICE_ACCESS;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;

    public NoticesResponse searchAll(final int size, final Long cursorId) {
        final PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        final List<NoticeResponse> notices = noticeQueryRepository.searchAll(cursorId, pageRequest).stream()
                .map(NoticeResponse::from)
                .toList();

        return new NoticesResponse(notices);
    }

    public NoticeResponse search(final Long noticeId) {
        if(!noticeRepository.existsById(noticeId)){
            throw new NoticeException(NOT_FOUND_NOTICE);
        }
        return NoticeResponse.from(noticeQueryRepository.search(noticeId));
    }

    @Transactional
    public void create(final Long adminId, final NoticeCreateRequest noticeCreateRequest) {
        noticeRepository.save(makeNotice(adminId, noticeCreateRequest));
    }

    @Transactional
    public void update(final Long adminId, final Long noticeId, final NoticeUpdateRequest noticeUpdateRequest) {
        final Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE));

        if(!notice.getMember().getId().equals(adminId)){
            throw new NoticeException(UNAUTHORIZED_NOTICE_ACCESS);
        }

        notice.changeTitle(noticeUpdateRequest.title());
        notice.changeContent(noticeUpdateRequest.content());
    }

    @Transactional
    public void delete(final AccessContext accessContext, final Long noticeId) {
        final Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE));

        final Long adminId = accessContext.getMemberId();
        final Authority authority = accessContext.getAuthority();

        if(authority.equals(ADMIN) && !notice.getMember().getId().equals(adminId)){
            throw new NoticeException(UNAUTHORIZED_NOTICE_ACCESS);
        }

        noticeRepository.deleteById(noticeId);
    }

    private Notice makeNotice(final Long adminId, final NoticeCreateRequest noticeCreateRequest) {
        return Notice.create(
                noticeCreateRequest.title(),
                noticeCreateRequest.content(),
                Member.withId(adminId)
        );
    }
}
