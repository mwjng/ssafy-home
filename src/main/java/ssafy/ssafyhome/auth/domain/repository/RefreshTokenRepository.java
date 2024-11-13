package ssafy.ssafyhome.auth.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ssafy.ssafyhome.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
