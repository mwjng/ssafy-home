package ssafy.ssafyhome.article.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
