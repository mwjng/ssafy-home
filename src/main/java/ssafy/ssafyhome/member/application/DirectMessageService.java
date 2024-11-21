package ssafy.ssafyhome.member.application;

import ssafy.ssafyhome.member.application.response.DirectMessageResponse;
import ssafy.ssafyhome.member.application.response.ReceivedMessagesResponse;
import ssafy.ssafyhome.member.application.response.SentMessagesResponse;
import ssafy.ssafyhome.member.application.response.UnreadMessageResponse;
import ssafy.ssafyhome.member.presentation.request.SendMessageRequest;

public interface DirectMessageService {
    ReceivedMessagesResponse searchReceivedMessages(Long memberId, int size, Long cursorId);

    SentMessagesResponse searchSentMessages(Long memberId, int size, Long cursorId);

    UnreadMessageResponse searchUnRead(Long memberId);

    void send(Long memberId, Long receiverId, SendMessageRequest sendMessageRequest);

    void delete(Long memberId, Long directMessageId);

    DirectMessageResponse searchReceivedMessage(Long memberId, Long directMessageId);

    DirectMessageResponse searchSentMessage(Long memberId, Long directMessageId);
}
