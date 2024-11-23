package ssafy.ssafyhome.notice.application.response;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record NoticeQueryResponse(
        Long noticeId,
        Long memberId,
        String writer,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    @QueryProjection
    public NoticeQueryResponse {
    }
}
