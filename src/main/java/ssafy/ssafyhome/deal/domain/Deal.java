package ssafy.ssafyhome.deal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.member.domain.Member;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Deal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @Column(nullable = false)
    private BigDecimal exclusiveArea;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private int views;

    private Integer deposit;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(STRING)
    private DealStatus status;

    @Enumerated(STRING)
    private DealType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
