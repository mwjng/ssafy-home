package ssafy.ssafyhome.member.application.response;

import ssafy.ssafyhome.member.domain.MessageStatus;

import java.time.LocalDateTime;

public record DirectMessageResponse(
        Long directMessageId,
        Long memberId,
        String nickname,
        String content,
        MessageStatus status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public static DirectMessageResponse from(DirectMessageQueryResponse directMessageQueryResponse){
        return new DirectMessageResponse(
                directMessageQueryResponse.directMessageId(),
                directMessageQueryResponse.memberId(),
                directMessageQueryResponse.nickname(),
                directMessageQueryResponse.content(),
                directMessageQueryResponse.status(),
                directMessageQueryResponse.createdAt(),
                directMessageQueryResponse.modifiedAt()
        );
    }
}
