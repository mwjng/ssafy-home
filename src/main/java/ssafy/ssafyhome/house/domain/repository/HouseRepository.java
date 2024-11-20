package ssafy.ssafyhome.house.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.house.domain.House;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    @EntityGraph(attributePaths = {"region"})
    @Query("SELECT h FROM House h")
    List<House> fetchAll();
}
