package ssafy.ssafyhome.like.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.member.domain.Member;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class LikeDeal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "like_deal_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "deal_id")
    private Deal deal;

    public static LikeDeal of(Member member, Deal deal){
        LikeDeal likeDeal = new LikeDeal();
        likeDeal.member = member;
        likeDeal.deal = deal;
        return likeDeal;
    }
}
