package ssafy.ssafyhome.member.application.response;

import ssafy.ssafyhome.member.domain.DirectMessage;
import ssafy.ssafyhome.member.domain.MessageStatus;

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

    public static SentMessageResponse from(DirectMessage directMessage){
        return new SentMessageResponse(
                directMessage.getId(),
                directMessage.getReceiver().getId(),
                directMessage.getReceiver().getNickname(),
                directMessage.getContent(),
                directMessage.getStatus(),
                directMessage.getCreatedAt(),
                directMessage.getModifiedAt()
        );
    }
}
