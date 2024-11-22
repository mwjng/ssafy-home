package ssafy.ssafyhome.like.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.like.domain.LikeHouse;

import java.util.Optional;

public interface LikeHouseRepository extends JpaRepository<LikeHouse, Long> {

    @Override
    @EntityGraph(attributePaths = "{member}")
    Optional<LikeHouse> findById(Long id);

    Boolean existsByMemberIdAndHouseId(Long memberId, Long houseId);
}
