package ssafy.ssafyhome.image.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class ImageException extends RuntimeException {

    private final ErrorCode errorCode;

    public ImageException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ImageException(ErrorCode errorCode, Throwable e) {
        super(e);
        this.errorCode = errorCode;
    }
}
