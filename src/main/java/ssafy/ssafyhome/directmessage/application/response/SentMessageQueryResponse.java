package ssafy.ssafyhome.directmessage.application.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import ssafy.ssafyhome.directmessage.domain.MessageStatus;

import java.time.LocalDateTime;

public record SentMessageQueryResponse(
        Long directMessageId,
        Long receiverId,
        String receiverName,
        String content,
        MessageStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    @QueryProjection
    public SentMessageQueryResponse {
    }
}
