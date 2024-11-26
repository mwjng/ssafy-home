package ssafy.ssafyhome.comment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.article.domain.Article;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.member.domain.Member;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Comment(final String content, final Article article, final Member member) {
        this.content = content;
        this.article = article;
        this.member = member;
    }
}
