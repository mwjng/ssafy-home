package ssafy.ssafyhome.notice.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.notice.application.response.NoticeResponse;
import ssafy.ssafyhome.notice.domain.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Override
    @EntityGraph(attributePaths = "{member}")
    Optional<Notice> findById(Long noticeId);
}
