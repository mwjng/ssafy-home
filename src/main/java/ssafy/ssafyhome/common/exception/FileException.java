package ssafy.ssafyhome.common.exception;

import lombok.Getter;

@Getter
public class FileException extends RuntimeException {

    private final ErrorCode errorCode;

    public FileException(final ErrorCode errorCode, Throwable e) {
        super(e);
        this.errorCode = errorCode;
    }

    public FileException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
