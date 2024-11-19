package ssafy.ssafyhome.region.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class RegionException extends RuntimeException {
    private final ErrorCode errorCode;

    public RegionException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
