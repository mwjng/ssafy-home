package ssafy.ssafyhome.directmessage.application;

import ssafy.ssafyhome.directmessage.application.response.DirectMessageResponse;
import ssafy.ssafyhome.directmessage.application.response.ReceivedMessagesResponse;
import ssafy.ssafyhome.directmessage.application.response.SentMessagesResponse;
import ssafy.ssafyhome.directmessage.application.response.UnreadMessageResponse;
import ssafy.ssafyhome.directmessage.presentation.request.SendMessageRequest;

public interface DirectMessageService {
    ReceivedMessagesResponse searchReceivedMessages(Long memberId, int size, Long cursorId);

    SentMessagesResponse searchSentMessages(Long memberId, int size, Long cursorId);

    UnreadMessageResponse searchUnRead(Long memberId);

    void send(Long memberId, Long receiverId, SendMessageRequest sendMessageRequest);

    void delete(Long memberId, Long directMessageId);

    DirectMessageResponse searchReceivedMessage(Long memberId, Long directMessageId);

    DirectMessageResponse searchSentMessage(Long memberId, Long directMessageId);
}
