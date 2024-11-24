package ssafy.ssafyhome.like.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.like.domain.LikeHouse;

public interface LikeHouseRepository extends JpaRepository<LikeHouse, Long> {
    Boolean existsByMemberIdAndHouseId(Long memberId, Long houseId);

    LikeHouse findByMemberIdAndHouseId(Long memberId, Long houseId);
}
