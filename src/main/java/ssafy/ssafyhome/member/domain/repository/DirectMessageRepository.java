package ssafy.ssafyhome.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.member.domain.DirectMessage;

import java.util.Optional;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long>, DirectMessageRepositoryCustom {
    Optional<DirectMessage> findByIdAndSenderId(Long directMessageId, Long senderId);
    Optional<DirectMessage> findByIdAndReceiverId(Long directMessageId, Long receiverId);
}
