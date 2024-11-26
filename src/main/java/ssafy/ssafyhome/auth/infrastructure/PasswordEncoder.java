package ssafy.ssafyhome.auth.infrastructure;

public interface PasswordEncoder {

    String encode(String password);

    boolean matches(String password, String hashed);
}
