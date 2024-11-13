package ssafy.ssafyhome.auth.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
