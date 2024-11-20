package ssafy.ssafyhome.directmessage.application.response;

import ssafy.ssafyhome.directmessage.domain.MessageStatus;

import java.time.LocalDateTime;

public record SentMessageResponse(
        Long directMessageId,
        Long receiverId,
        String receiverName,
        String content,
        MessageStatus status,
        LocalDateTime createAt,
        LocalDateTime modifiedAt) {
}
