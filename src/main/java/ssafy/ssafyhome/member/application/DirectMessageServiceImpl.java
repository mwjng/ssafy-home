package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.member.domain.DirectMessage;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.DirectMessageRepository;
import ssafy.ssafyhome.member.exception.DirectMessageException;
import ssafy.ssafyhome.member.presentation.request.SendMessageRequest;
import ssafy.ssafyhome.member.application.response.*;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_DIRECT_MESSAGE;
import static ssafy.ssafyhome.common.exception.ErrorCode.UNAUTHORIZED_DIRECT_MESSAGE_ACCESS;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.defaultSort;
import static ssafy.ssafyhome.member.domain.MessageStatus.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DirectMessageServiceImpl implements DirectMessageService {

    private final DirectMessageRepository directMessageRepository;

    public ReceivedMessagesResponse searchReceivedMessages(final Long memberId, final int size, final Long cursorId) {
        PageRequest pageRequest = createPageRequest(size);
        List<ReceivedMessageResponse> receivedMessages = directMessageRepository.searchReceivedMessages(memberId, pageRequest, cursorId)
                .stream()
                .map(ReceivedMessageResponse::from)
                .toList();
        return new ReceivedMessagesResponse(receivedMessages);
    }

    public SentMessagesResponse searchSentMessages(final Long memberId, final int size, final Long cursorId) {
        PageRequest pageRequest = createPageRequest(size);
        List<SentMessageResponse> sentMessages = directMessageRepository.searchSentMessages(memberId, pageRequest, cursorId)
                .stream()
                .map(SentMessageResponse::from)
                .toList();
        return new SentMessagesResponse(sentMessages);
    }

    public ReceivedMessageResponse searchReceivedMessage(final Long memberId, final Long directMessageId) {
        DirectMessage message = directMessageRepository.findById(directMessageId).orElseThrow(() -> new DirectMessageException(NOT_FOUND_DIRECT_MESSAGE));

        if (!message.getReceiver().getId().equals(memberId)) {
            throw new DirectMessageException(UNAUTHORIZED_DIRECT_MESSAGE_ACCESS);
        }
        return ReceivedMessageResponse.from(message);
    }

    public SentMessageResponse searchSentMessage(final Long memberId, final Long directMessageId) {
        DirectMessage message = directMessageRepository.findById(directMessageId).orElseThrow(() -> new DirectMessageException(NOT_FOUND_DIRECT_MESSAGE));

        if (!message.getSender().getId().equals(memberId)) {
            throw new DirectMessageException(UNAUTHORIZED_DIRECT_MESSAGE_ACCESS);
        }

        return SentMessageResponse.from(message);
    }

    public UnreadMessageResponse searchUnRead(final Long memberId) {
        long unreadCount = directMessageRepository.countByReceiverIdAndStatus(memberId, UNREAD);
        return new UnreadMessageResponse(unreadCount > 0, (int) unreadCount);
    }

    public void send(final Long memberId, final Long receiverId, final SendMessageRequest sendMessageRequest) {
        Member sender = Member.withId(memberId);
        Member receiver = Member.withId(receiverId);
    }

    public void delete(final Long memberId, final Long directMessageId) {
        Member sender = directMessageRepository.findSenderById(directMessageId).orElseThrow(() -> new DirectMessageException(NOT_FOUND_DIRECT_MESSAGE));

        if(!sender.getId().equals(memberId)){
            throw new DirectMessageException(UNAUTHORIZED_DIRECT_MESSAGE_ACCESS);
        }

        directMessageRepository.deleteById(directMessageId);
    }

    private PageRequest createPageRequest(final int size) {
        return PageRequest.of(0, size, defaultSort());
    }
}
