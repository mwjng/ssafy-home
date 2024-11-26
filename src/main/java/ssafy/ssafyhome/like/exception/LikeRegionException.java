package ssafy.ssafyhome.like.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class LikeRegionException extends RuntimeException{
    private final ErrorCode errorCode;

    public LikeRegionException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
