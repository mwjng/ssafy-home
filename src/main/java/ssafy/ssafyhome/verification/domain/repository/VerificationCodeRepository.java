package ssafy.ssafyhome.verification.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ssafy.ssafyhome.verification.domain.VerificationCode;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
