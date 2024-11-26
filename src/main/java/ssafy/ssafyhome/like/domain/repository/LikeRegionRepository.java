package ssafy.ssafyhome.like.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.like.domain.LikeRegion;

public interface LikeRegionRepository extends JpaRepository<LikeRegion, Long> {
    boolean existsByRegionIdAndMemberId(Long regionId, Long memberId);
}
