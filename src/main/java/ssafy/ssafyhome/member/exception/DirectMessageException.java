package ssafy.ssafyhome.member.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class DirectMessageException extends RuntimeException {
    private final ErrorCode errorCode;

    public DirectMessageException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
