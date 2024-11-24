package ssafy.ssafyhome.question.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.member.domain.Member;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "question", cascade = ALL, orphanRemoval = true)
    private Answer answer;

    @Builder
    public Question(final String title, final String content, final Member member, final Answer answer) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.answer = answer;
    }

    public void changeQuestion(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void registerAnswer(Answer answer) {
        this.answer = answer;
    }
}
