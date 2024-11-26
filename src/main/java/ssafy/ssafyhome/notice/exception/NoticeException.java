package ssafy.ssafyhome.notice.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class NoticeException extends RuntimeException {
    private final ErrorCode errorCode;

    public NoticeException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
