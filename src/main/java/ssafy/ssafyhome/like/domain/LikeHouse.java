package ssafy.ssafyhome.like.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.member.domain.Member;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class LikeHouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "like_house_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    public static LikeHouse create(Member member, House house){
        LikeHouse likeHouse = new LikeHouse();
        likeHouse.member = member;
        likeHouse.house = house;
        return likeHouse;
    }
}
