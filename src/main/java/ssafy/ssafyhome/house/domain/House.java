package ssafy.ssafyhome.house.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
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
}
