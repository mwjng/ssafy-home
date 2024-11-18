package ssafy.ssafyhome.directmessage.application.response;

import ssafy.ssafyhome.directmessage.domain.MessageStatus;

import java.time.LocalDateTime;

public record DirectMessageResponse(
        Long directMessageId,
        Long senderId,
        String senderName,
        String content,
        MessageStatus status,
        LocalDateTime createAt,
        LocalDateTime modifiedAt) {
}
