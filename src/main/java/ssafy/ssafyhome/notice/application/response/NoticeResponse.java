package ssafy.ssafyhome.notice.application.response;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long noticeId,
        Long memberId,
        String writer,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public static NoticeResponse from(NoticeQueryResponse queryResponse) {
        return new NoticeResponse(
                queryResponse.noticeId(),
                queryResponse.memberId(),
                queryResponse.writer(),
                queryResponse.title(),
                queryResponse.content(),
                queryResponse.createdAt(),
                queryResponse.modifiedAt()
        );
    }
}
