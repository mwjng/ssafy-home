package ssafy.ssafyhome.deal.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.deal.domain.Deal;

import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {
    @EntityGraph(attributePaths = "{member}")
    Optional<Deal> findMemberAndDealById(Long id);
}
