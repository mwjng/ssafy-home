package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.member.domain.repository.DirectMessageRepository;
import ssafy.ssafyhome.member.presentation.request.SendMessageRequest;
import ssafy.ssafyhome.member.application.response.*;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.defaultSort;

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

    public DirectMessageResponse searchReceivedMessage(final Long memberId, final Long directMessageId) {
        return null;
    }

    public DirectMessageResponse searchSentMessage(final Long memberId, final Long directMessageId) {
        return null;
    }


    public UnreadMessageResponse searchUnRead(final Long memberId) {
        return null;
    }

    public void send(final Long memberId, final Long receiverId, final SendMessageRequest sendMessageRequest) {

    }

    public void delete(final Long memberId, final Long directMessageId) {

    }

    private PageRequest createPageRequest(final int size) {
        return PageRequest.of(0, size, defaultSort());
    }
}
