package ssafy.ssafyhome.member.application.response;

import ssafy.ssafyhome.member.domain.DirectMessage;
import ssafy.ssafyhome.member.domain.MessageStatus;

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

    public static ReceivedMessageResponse from(DirectMessage directMessage){
        return new ReceivedMessageResponse(
                directMessage.getId(),
                directMessage.getSender().getId(),
                directMessage.getSender().getNickname(),
                directMessage.getContent(),
                directMessage.getStatus(),
                directMessage.getCreatedAt(),
                directMessage.getModifiedAt()
        );
    }
}
