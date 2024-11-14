package ssafy.ssafyhome.likearticle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.likearticle.domain.LikeArticle;

public interface LikeArticleRepository extends JpaRepository<LikeArticle, Long> {
}
