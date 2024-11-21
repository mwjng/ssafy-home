package ssafy.ssafyhome.like.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.like.domain.LikeArticle;

public interface LikeArticleRepository extends JpaRepository<LikeArticle, Long> {
}
