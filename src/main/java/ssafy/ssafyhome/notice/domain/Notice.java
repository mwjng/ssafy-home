package ssafy.ssafyhome.notice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.member.domain.Member;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Notice create(final String title, final String content, final Member member) {
        Notice notice = new Notice();
        notice.title = title;
        notice.content = content;
        notice.member = member;
        return notice;
    }

    public void changeTitle(final String title){
        this.title = title;
    }

    public void changeContent(final String content){
        this.content = content;
    }
}
