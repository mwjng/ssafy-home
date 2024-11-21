package ssafy.ssafyhome.directmessage.domain.repository;

import org.springframework.data.domain.Pageable;
import ssafy.ssafyhome.directmessage.application.response.ReceivedMessageQueryResponse;
import ssafy.ssafyhome.directmessage.application.response.SentMessageQueryResponse;

import java.util.List;

public interface DirectMessageRepositoryCustom {
    List<ReceivedMessageQueryResponse> searchReceivedMessages(Long memberId, Pageable pageable, Long cursorId);

    List<SentMessageQueryResponse> searchSentMessages(Long memberId, Pageable pageable, Long cursorId);
}
