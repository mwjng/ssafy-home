package ssafy.ssafyhome.deal.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class DealException extends RuntimeException {
    private final ErrorCode errorCode;

    public DealException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
