package ssafy.ssafyhome.like.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.like.domain.LikeDeal;

public interface LikeDealRepository extends JpaRepository<LikeDeal, Long> {
    boolean existsByMemberIdAndDealId(Long memberId, Long dealId);

    LikeDeal findByMemberIdAndDealId(Long memberId, Long dealId);
}
