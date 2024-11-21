package ssafy.ssafyhome.deal.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.deal.domain.Deal;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    @Query("""
        SELECT deal FROM Deal deal
        LEFT JOIN FETCH deal.house house
        LEFT JOIN FETCH house.region
        WHERE deal.member.id = :memberId
    """)
    List<Deal> findDealsByMemberId(Long memberId, Pageable pageable);
}
