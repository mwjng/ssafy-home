package ssafy.ssafyhome.auth.exception;

import ssafy.ssafyhome.common.exception.ErrorCode;

public class ExpiredTokenException extends AuthException {

    public ExpiredTokenException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
