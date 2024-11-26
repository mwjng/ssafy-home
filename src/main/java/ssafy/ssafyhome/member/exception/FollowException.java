package ssafy.ssafyhome.member.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class FollowException extends RuntimeException {
  private final ErrorCode errorCode;

  public FollowException(final ErrorCode errorCode) {
    this.errorCode = errorCode;
  }
}
