package ssafy.ssafyhome.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    // 팔로우 하는 사용자
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follower")
    private Member follower;

    // 팔로우 당하는 사용자
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following")
    private Member following;

    public static Follow create(Member follower, Member following){
        Follow follow = new Follow();
        follow.follower = follower;
        follow.following = following;
        return follow;
    }
}
