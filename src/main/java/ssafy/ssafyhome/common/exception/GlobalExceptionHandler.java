package ssafy.ssafyhome.common.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.member.exception.DirectMessageException;
import ssafy.ssafyhome.member.exception.FollowException;
import ssafy.ssafyhome.image.exception.ImageException;
import ssafy.ssafyhome.like.exception.LikeRegionException;
import ssafy.ssafyhome.like.exception.LikeHouseException;
import ssafy.ssafyhome.member.exception.UserNotFoundException;
import ssafy.ssafyhome.notice.exception.NoticeException;
import ssafy.ssafyhome.region.exception.RegionException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_REQUEST;
import static ssafy.ssafyhome.common.exception.ErrorCode.SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        log.warn(e.getMessage(), e);
        String errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(INVALID_REQUEST, errorMessage));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(final UserNotFoundException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.status(NOT_FOUND)
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handleAuthException(final AuthException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<ExceptionResponse> handleImageException(final ImageException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(RegionException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRegionFieldException(final RegionException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(NoticeException.class)
    public ResponseEntity<ExceptionResponse> handleNoticeException(final NoticeException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(FollowException.class)
    public ResponseEntity<ExceptionResponse> handleFollowException(final FollowException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(DirectMessageException.class)
    public ResponseEntity<ExceptionResponse> handleDirectMessageException(final DirectMessageException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(LikeRegionException.class)
    public ResponseEntity<ExceptionResponse> handleLikeRegionException(final LikeRegionException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(LikeHouseException.class)
    public ResponseEntity<ExceptionResponse> handleLikeHouseException(final LikeHouseException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getErrorCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(final ConstraintViolationException e) {
        log.warn(e.getMessage(), e);
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("메시지 추출 도중 에러 발생"));
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(INVALID_REQUEST, errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(SERVER_ERROR));
    }
}
