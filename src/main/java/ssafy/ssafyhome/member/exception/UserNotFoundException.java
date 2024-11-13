package ssafy.ssafyhome.member.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserNotFoundException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
