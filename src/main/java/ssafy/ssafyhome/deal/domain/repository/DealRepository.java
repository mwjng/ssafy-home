package ssafy.ssafyhome.deal.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.deal.domain.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {
}
