package ssafy.ssafyhome.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static ssafy.ssafyhome.member.domain.MessageStatus.*;

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
    @Enumerated(EnumType.STRING)
    private MessageStatus status = UNREAD;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender")
    private Member sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver")
    private Member receiver;

    public static DirectMessage create(String content, Member sender, Member receiver){
        DirectMessage directMessage = new DirectMessage();
        directMessage.content = content;
        directMessage.sender = sender;
        directMessage.receiver = receiver;
        return directMessage;
    }

    public void changeStatus(){
        this.status = READ;
    }
}
