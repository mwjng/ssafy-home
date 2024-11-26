package ssafy.ssafyhome.house.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.house.domain.House;

public interface HouseRepository extends JpaRepository<House, Long> {
}
