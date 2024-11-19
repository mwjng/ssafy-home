package ssafy.ssafyhome.region.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.region.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long>, RegionRepositoryCustom {
    Long findIdBySidoAndGugunAndDong(String sido, String gugun, String dong);
}
