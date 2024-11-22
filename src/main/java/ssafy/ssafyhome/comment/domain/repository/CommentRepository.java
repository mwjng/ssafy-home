package ssafy.ssafyhome.comment.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.comment.application.response.CommentResponse;
import ssafy.ssafyhome.comment.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        SELECT new ssafy.ssafyhome.comment.application.response.CommentResponse (
            comment.id,
            comment.member.id,
            comment.content,
            comment.article.id,
            comment.createdAt,
            comment.modifiedAt
        )
        FROM Comment comment
        WHERE comment.member.id = :memberId
    """)
    List<CommentResponse> findCommentsByMemberId(Long memberId, Pageable pageable);
}
