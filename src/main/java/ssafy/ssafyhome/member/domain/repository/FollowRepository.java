package ssafy.ssafyhome.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.member.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
