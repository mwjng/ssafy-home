package ssafy.ssafyhome.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.ssafyhome.question.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
