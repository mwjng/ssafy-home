package ssafy.ssafyhome.member.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.member.domain.DirectMessage;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MessageStatus;

import java.util.Optional;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    Optional<DirectMessage> findByIdAndSenderId(Long directMessageId, Long senderId);
    Optional<DirectMessage> findByIdAndReceiverId(Long directMessageId, Long receiverId);

    @Override
    @EntityGraph(attributePaths = "{sender, receiver}")
    Optional<DirectMessage> findById(Long directMessageId);

    long countByReceiverIdAndStatus(Long receiverId, MessageStatus status);

    @EntityGraph(attributePaths = "{sender}")
    Optional<Member> findSenderById(Long directMessageId);
}

