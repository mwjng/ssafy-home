package ssafy.ssafyhome.directmessage.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.directmessage.domain.DirectMessage;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
}
