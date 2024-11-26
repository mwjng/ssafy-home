package ssafy.ssafyhome.like.exception;

import lombok.Getter;
import ssafy.ssafyhome.common.exception.ErrorCode;

@Getter
public class LikeHouseException extends RuntimeException {
  private final ErrorCode errorCode;

  public LikeHouseException(final ErrorCode errorCode) {
    this.errorCode = errorCode;
  }
}
