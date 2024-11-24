package ssafy.ssafyhome.question.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.question.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    boolean existsByMemberIdAndId(final Long memberId, final Long questionId);

    @Query("""
        SELECT question FROM Question question
        LEFT JOIN FETCH question.member
        LEFT JOIN FETCH question.answer
    """)
    List<Question> findQuestions(final Pageable pageable);

    @Query("""
        SELECT question FROM Question question
        LEFT JOIN FETCH question.member member
        LEFT JOIN FETCH question.answer answer
        WHERE member.id = :memberId
    """)
    List<Question> findByMemberId(final Long memberId, final Pageable pageable);

    @Query("""
        SELECT question FROM Question question
        LEFT JOIN FETCH question.member member
        LEFT JOIN FETCH question.answer answer
        WHERE question.id = :id
    """)
    Optional<Question> findByQuestionId(final Long id);
}
