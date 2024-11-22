package ssafy.ssafyhome.article.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.ssafyhome.article.domain.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("""
        SELECT article FROM Article article
        LEFT JOIN FETCH article.member m
        WHERE article.member.id = :memberId
    """)
    List<Article> findByMemberId(@Param("memberId") Long memberId);
}
