package ssafy.ssafyhome.member.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ssafy.ssafyhome.member.domain.VerificationCode;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
