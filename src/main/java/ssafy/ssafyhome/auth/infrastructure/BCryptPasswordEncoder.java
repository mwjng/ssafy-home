package ssafy.ssafyhome.auth.infrastructure;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean matches(final String password, final String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
