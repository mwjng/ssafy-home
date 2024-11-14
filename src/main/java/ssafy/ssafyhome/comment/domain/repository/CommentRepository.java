package ssafy.ssafyhome.comment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
