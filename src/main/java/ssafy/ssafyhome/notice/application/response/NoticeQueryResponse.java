package ssafy.ssafyhome.notice.application.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeQueryResponse {
    private Long noticeId;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @QueryProjection
    public NoticeQueryResponse(final Long noticeId, final String writer, final String title, final String content, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.noticeId = noticeId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
