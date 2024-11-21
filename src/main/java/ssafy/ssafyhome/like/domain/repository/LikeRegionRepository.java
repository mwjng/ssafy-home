package ssafy.ssafyhome.like.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.like.domain.LikeRegion;
import ssafy.ssafyhome.member.domain.Member;

import java.util.Optional;

public interface LikeRegionRepository extends JpaRepository<LikeRegion, Long> {
    Optional<LikeRegion> findByIdAndMember(Long regionId, Member member);

    @EntityGraph(attributePaths = {"member"})
    Optional<Member> findMemberById(Long regionId);
}
