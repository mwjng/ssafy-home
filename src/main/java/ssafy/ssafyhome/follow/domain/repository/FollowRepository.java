package ssafy.ssafyhome.follow.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.follow.domain.Follow;
import ssafy.ssafyhome.member.domain.Member;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom{
    @EntityGraph(attributePaths = {"follower"})
    Optional<Member> findFollowerById(Long id);

    @EntityGraph(attributePaths = {"following"})
    Optional<Member> findFollowingById(Long id);
}
