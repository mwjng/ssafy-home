package ssafy.ssafyhome.like.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class LikeDealException extends RuntimeException {
    private final ErrorCode errorCode;

    public LikeDealException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
