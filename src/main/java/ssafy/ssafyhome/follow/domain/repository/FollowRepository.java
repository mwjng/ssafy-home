package ssafy.ssafyhome.follow.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.follow.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
