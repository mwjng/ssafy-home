package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.member.domain.DirectMessage;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.DirectMessageRepository;
import ssafy.ssafyhome.member.exception.DirectMessageException;
import ssafy.ssafyhome.member.infrastructure.DirectMessageQueryRepository;
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
    private final DirectMessageQueryRepository directMessageQueryRepository;

    public ReceivedMessagesResponse searchReceivedMessages(final Long memberId, final int size, final Long cursorId) {
        final PageRequest pageRequest = createPageRequest(size);
        final List<ReceivedMessageResponse> receivedMessages = directMessageQueryRepository.searchReceivedMessages(memberId, pageRequest, cursorId)
                .stream()
                .map(ReceivedMessageResponse::from)
                .toList();
        return new ReceivedMessagesResponse(receivedMessages);
    }

    public SentMessagesResponse searchSentMessages(final Long memberId, final int size, final Long cursorId) {
        final PageRequest pageRequest = createPageRequest(size);
        final List<SentMessageResponse> sentMessages = directMessageQueryRepository.searchSentMessages(memberId, pageRequest, cursorId)
                .stream()
                .map(SentMessageResponse::from)
                .toList();
        return new SentMessagesResponse(sentMessages);
    }

    @Transactional
    public ReceivedMessageResponse searchReceivedMessage(final Long memberId, final Long directMessageId) {
        final DirectMessage message = directMessageRepository.findById(directMessageId).orElseThrow(() -> new DirectMessageException(NOT_FOUND_DIRECT_MESSAGE));

        if (!message.getReceiver().getId().equals(memberId)) {
            throw new DirectMessageException(UNAUTHORIZED_DIRECT_MESSAGE_ACCESS);
        }
        message.changeStatus();
        return ReceivedMessageResponse.from(message);
    }

    public SentMessageResponse searchSentMessage(final Long memberId, final Long directMessageId) {
        final DirectMessage message = directMessageRepository.findById(directMessageId).orElseThrow(() -> new DirectMessageException(NOT_FOUND_DIRECT_MESSAGE));

        if (!message.getSender().getId().equals(memberId)) {
            throw new DirectMessageException(UNAUTHORIZED_DIRECT_MESSAGE_ACCESS);
        }

        return SentMessageResponse.from(message);
    }

    public UnreadMessageResponse searchUnRead(final Long memberId) {
        final long unreadCount = directMessageRepository.countByReceiverIdAndStatus(memberId, UNREAD);
        return new UnreadMessageResponse(unreadCount > 0, (int) unreadCount);
    }

    @Transactional
    public void send(final Long memberId, final Long receiverId, final SendMessageRequest sendMessageRequest) {
        directMessageRepository.save(makeDirectMessage(memberId, receiverId, sendMessageRequest));
    }

    @Transactional
    public void delete(final Long memberId, final Long directMessageId) {
        final DirectMessage directMessage = directMessageRepository.findById(directMessageId)
                .orElseThrow(() -> new DirectMessageException(NOT_FOUND_DIRECT_MESSAGE));

        if(!(directMessage.getSender().getId().equals(memberId) || directMessage.getReceiver().getId().equals(memberId))){
            throw new DirectMessageException(UNAUTHORIZED_DIRECT_MESSAGE_ACCESS);
        }

        directMessageRepository.deleteById(directMessageId);
    }

    private DirectMessage makeDirectMessage(final Long memberId, final Long receiverId, final SendMessageRequest sendMessageRequest) {
        return DirectMessage.create(sendMessageRequest.content(), Member.withId(memberId), Member.withId(receiverId));
    }

    private PageRequest createPageRequest(final int size) {
        return PageRequest.of(0, size, defaultSort());
    }
}
