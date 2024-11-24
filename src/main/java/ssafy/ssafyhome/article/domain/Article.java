package ssafy.ssafyhome.article.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.comment.domain.Comment;
import ssafy.ssafyhome.common.auditing.BaseEntity;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.like.domain.LikeArticle;
import ssafy.ssafyhome.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    private String dirName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
    private List<LikeArticle> likeArticles = new ArrayList<>();

    public Article(final String content, final String dirName, final Member member, final House house) {
        this.content = content;
        this.dirName = dirName;
        this.member = member;
        this.house = house;
    }

    public void updateArticle(String content, String dirName) {
        this.content = content;
        this.dirName = dirName;
    }
}
