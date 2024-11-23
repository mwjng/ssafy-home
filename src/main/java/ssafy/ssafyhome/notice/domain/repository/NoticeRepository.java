package ssafy.ssafyhome.notice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
