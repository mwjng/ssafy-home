package ssafy.ssafyhome.notice.application.response;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long noticeId,
        String writer,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public static NoticeResponse from(NoticeQueryResponse queryResponse) {
        return new NoticeResponse(
                queryResponse.getNoticeId(),
                queryResponse.getWriter(),
                queryResponse.getTitle(),
                queryResponse.getContent(),
                queryResponse.getCreatedAt(),
                queryResponse.getModifiedAt()
        );
    }
}
