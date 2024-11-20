package ssafy.ssafyhome.notice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticesResponse searchAll(final int size, final Long cursorId) {
        PageRequest pageRequest = PageRequest.of(0, size, Sort.by(
                Sort.Order.desc("createdAt"),
                Sort.Order.desc("id")
        ));
        List<NoticeResponse> notices = noticeRepository.searchAll(cursorId, pageRequest).stream()
                .map(NoticeResponse::from)
                .toList();

        if (notices.isEmpty()) {
            throw new NoticeException(NOT_FOUND_NOTICE);
        }

        return new NoticesResponse(notices);
    }

    @Override
    public NoticeResponse search(final Long noticeId) {
        return Optional.ofNullable(noticeRepository.search(noticeId))
                .map(NoticeResponse::from)
                .orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE));
    }

    @Override
    @Transactional
    public void create(final Long adminId, final NoticeCreateRequest noticeCreateRequest) {
        Member admin = Member.withId(adminId);
        Notice notice = Notice.builder()
                .title(noticeCreateRequest.title())
                .content(noticeCreateRequest.content())
                .member(admin)
                .build();
        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public void update(final Long noticeId, final NoticeUpdateRequest noticeUpdateRequest) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeException(NOT_FOUND_NOTICE));
        notice.changeTitle(noticeUpdateRequest.title());
        notice.changeContent(noticeUpdateRequest.content());
    }

    @Override
    @Transactional
    public void delete(final Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }
}