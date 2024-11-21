package ssafy.ssafyhome.directmessage.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.directmessage.domain.MessageStatus;

import java.time.LocalDateTime;

public record DirectMessageQueryResponse(
        Long directMessageId,
        Long memberId,
        String nickname,
        String content,
        MessageStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    @QueryProjection
    public DirectMessageQueryResponse{
    }
}
