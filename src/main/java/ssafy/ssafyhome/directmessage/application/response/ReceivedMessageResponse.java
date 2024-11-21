package ssafy.ssafyhome.directmessage.application.response;

import ssafy.ssafyhome.directmessage.domain.MessageStatus;

import java.time.LocalDateTime;

public record ReceivedMessageResponse(
        Long directMessageId,
        Long senderId,
        String senderName,
        String content,
        MessageStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public static ReceivedMessageResponse from(ReceivedMessageQueryResponse receivedMessageQueryResponse){
        return new ReceivedMessageResponse(
                receivedMessageQueryResponse.directMessageId(),
                receivedMessageQueryResponse.senderId(),
                receivedMessageQueryResponse.senderName(),
                receivedMessageQueryResponse.content(),
                receivedMessageQueryResponse.status(),
                receivedMessageQueryResponse.createdAt(),
                receivedMessageQueryResponse.modifiedAt()
        );
    }
}
