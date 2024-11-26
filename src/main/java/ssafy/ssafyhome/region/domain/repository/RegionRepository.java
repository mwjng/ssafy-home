package ssafy.ssafyhome.region.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.region.domain.Region;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findBySidoAndGugunAndDong(final String sido, final String gugun, final String dong);

    @Query("SELECT DISTINCT region.sido FROM Region region")
    List<String> findAllSidos();

    @Query("SELECT DISTINCT region.gugun FROM Region region WHERE region.sido = :sido")
    List<String> findGugunsBySido(@Param("sido") final String sido);

    @Query("SELECT region.dong FROM Region region WHERE region.sido = :sido AND region.gugun = :gugun")
    List<String> findDongsBySidoAndGuguns(final String sido, final String gugun);
}
