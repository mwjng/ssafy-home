package ssafy.ssafyhome.like.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.like.domain.LikeArticle;

import java.util.Optional;

public interface LikeArticleRepository extends JpaRepository<LikeArticle, Long> {

    boolean existsByMemberIdAndArticleId(Long memberId, Long articleId);

    Optional<LikeArticle> findByMemberIdAndArticleId(Long memberId, Long articleId);
}
