package ssafy.ssafyhome.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.member.domain.DirectMessage;
import ssafy.ssafyhome.member.domain.MessageStatus;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    long countByReceiverIdAndStatus(Long receiverId, MessageStatus status);
}

