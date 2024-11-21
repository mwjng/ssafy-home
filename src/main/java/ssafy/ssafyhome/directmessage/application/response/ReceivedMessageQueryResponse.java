package ssafy.ssafyhome.directmessage.application.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import ssafy.ssafyhome.directmessage.domain.MessageStatus;

import java.time.LocalDateTime;

public record ReceivedMessageQueryResponse(
        Long directMessageId,
        Long senderId,
        String senderName,
        String content,
        MessageStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    @QueryProjection
    public ReceivedMessageQueryResponse {
    }
}
