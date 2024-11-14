package ssafy.ssafyhome.directmessage.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.member.domain.Member;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class DirectMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "direct_message_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private MessageStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender")
    private Member sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver")
    private Member receiver;
}
