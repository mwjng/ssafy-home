package ssafy.ssafyhome.member.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class MemberException extends RuntimeException {
    private final ErrorCode errorCode;

    public MemberException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
