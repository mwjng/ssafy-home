package ssafy.ssafyhome.region.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Column(nullable = false)
    private String sido;

    @Column(nullable = false)
    private String gugun;

    @Column(nullable = false)
    private String dong;

    public static Region create(final String sido, final String gugun, final String dong) {
        Region region = new Region();
        region.sido = sido;
        region.gugun = gugun;
        region.dong = dong;
        return region;
    }
}
