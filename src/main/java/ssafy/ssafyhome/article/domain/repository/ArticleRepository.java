package ssafy.ssafyhome.article.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.ssafyhome.article.domain.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    boolean existsByMemberIdAndId(final Long memberId, final Long id);

    @Query("""
        SELECT article FROM Article article
        LEFT JOIN FETCH article.member member
        LEFT JOIN FETCH article.house house
        WHERE article.member.id = :memberId
    """)
    List<Article> findByMemberId(@Param("memberId") final Long memberId, final Pageable pageable);

    @Query("""
        SELECT article FROM Article article
        LEFT JOIN FETCH article.house house
        LEFT JOIN FETCH article.member member
        WHERE article.house.id = :houseId
    """)
    List<Article> findByHouseId(@Param("houseId") final Long houseId, final Pageable pageable);

    @Query("""
        SELECT article FROM LikeArticle likeArticle
        JOIN likeArticle.article article
        LEFT JOIN FETCH article.house house
        LEFT JOIN FETCH article.member member
        WHERE likeArticle.member.id = :memberId
    """)
    List<Article> findLikeArticlesByMemberId(final Long memberId, final Pageable pageable);
}
