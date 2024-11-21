package ssafy.ssafyhome.likeregion.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.likeregion.domain.LikeRegion;
import ssafy.ssafyhome.member.domain.Member;

import java.util.Optional;

public interface LikeRegionRepository extends JpaRepository<LikeRegion, Long>, LikeRegionRepositoryCustom {
    Optional<LikeRegion> findByIdAndMember(Long regionId, Member member);

    @EntityGraph(attributePaths = {"member"})
    Optional<LikeRegion> findById(Long regionId);
}
