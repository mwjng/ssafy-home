package ssafy.ssafyhome.deal.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.house.domain.HouseType;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    @Query("""
        SELECT deal FROM Deal deal
        LEFT JOIN FETCH deal.house house
        LEFT JOIN FETCH house.region
        WHERE house.type = :houseType
        AND deal.house.id = :houseId
    """)
    List<Deal> findDealsByHouseId(final Long houseId,
                                  final HouseType houseType);
}
