package ssafy.ssafyhome.notice.application;

import java.time.LocalDateTime;

public record NoticeResponse(Long noticeId, String writer, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
