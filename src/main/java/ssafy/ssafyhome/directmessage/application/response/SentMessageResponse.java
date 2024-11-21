package ssafy.ssafyhome.directmessage.application.response;

import ssafy.ssafyhome.directmessage.domain.MessageStatus;

import java.time.LocalDateTime;

public record SentMessageResponse(
        Long directMessageId,
        Long receiverId,
        String receiverName,
        String content,
        MessageStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public static SentMessageResponse from(SentMessageQueryResponse sentMessageQueryResponse){
        return new SentMessageResponse(
                sentMessageQueryResponse.directMessageId(),
                sentMessageQueryResponse.receiverId(),
                sentMessageQueryResponse.receiverName(),
                sentMessageQueryResponse.content(),
                sentMessageQueryResponse.status(),
                sentMessageQueryResponse.createdAt(),
                sentMessageQueryResponse.modifiedAt()
        );
    }
}
