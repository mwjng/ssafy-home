package ssafy.ssafyhome.house.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.house.presentation.request.HouseRequest;
import ssafy.ssafyhome.region.domain.Region;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class House extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "house_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long buildYear;

    @Column(nullable = false)
    private String jibun;

    @Column(nullable = false)
    private String road;

    @Column(nullable = false)
    private String bonbun;

    @Column(nullable = false)
    private String bubun;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    private String dirName;

    @Enumerated(STRING)
    @Column(nullable = false)
    private HouseType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Builder
    public House(
        final String name, final Long buildYear,
        final String jibun, final String road,
        final String bonbun, final String bubun,
        final String latitude, final String longitude,
        final String dirName, final HouseType type,
        final Region region
    ) {
        this.name = name;
        this.buildYear = buildYear;
        this.jibun = jibun;
        this.road = road;
        this.bonbun = bonbun;
        this.bubun = bubun;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dirName = dirName;
        this.type = type;
        this.region = region;
    }

    public void updateHouseInfo(final HouseRequest request, final Region region, final String imagePath) {
        this.name = request.name();
        this.buildYear = request.buildYear();
        this.jibun = request.jibun();
        this.road = request.road();
        this.bonbun = request.bonbun();
        this.bubun = request.bubun();
        this.latitude = request.latitude();
        this.longitude = request.longitude();
        this.dirName = imagePath;
        this.type = HouseType.getHouseType(request.houseType());
        this.region = region;
    }

    public static House withId(Long id) {
        House house = new House();
        house.id = id;
        return house;
    }
}
