package ssafy.ssafyhome.auth.exception;

import ssafy.ssafyhome.common.exception.ErrorCode;

public class InvalidTokenException extends AuthException {

    public InvalidTokenException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
