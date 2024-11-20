package ssafy.ssafyhome.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(1000, BAD_REQUEST, "올바르지 않은 요청입니다."),
    INVALID_IMAGE_FORMAT(1001, BAD_REQUEST, "지원하지 않는 이미지 형식입니다."),
    EXCEED_IMAGE_CAPACITY(1002, BAD_REQUEST, "이미지 크기가 허용된 최대 용량을 초과했습니다."),
    FAIL_IMAGE_UPLOAD(1003, BAD_REQUEST, "이미지 업로드에 실패했습니다."),
    DUPLICATED_USER_NICKNAME(1004, BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    DUPLICATED_USER_LOGIN_ID(1005, BAD_REQUEST, "이미 존재하는 아이디입니다."),
    INVALID_VERIFICATION_CODE(1006, BAD_REQUEST, "올바르지 않은 인증 코드입니다."),

    INVALID_REGION_FIELD(1010, BAD_REQUEST, "유효하지 않은 지역 필드 요청입니다."),
    INVALID_REGION(1011, BAD_REQUEST, "유효하지 않은 지역 요청입니다."),

    INVALID_AUTHORITY(2001, FORBIDDEN, "해당 리소스에 접근할 권한이 없습니다."),
    INVALID_USER_ID(2002, UNAUTHORIZED, "아이디가 존재하지 않습니다."),
    INVALID_PASSWORD(2003, UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_ACCESS_TOKEN(2004, UNAUTHORIZED, "유효하지 않은 AccessToken 입니다."),
    INVALID_REFRESH_TOKEN(2005, UNAUTHORIZED, "유효하지 않은 RefreshToken 입니다."),
    INVALID_AUTHORIZATION_CODE(2006, UNAUTHORIZED, "유효하지 않은 인가 코드 입니다."),
    INVALID_OAUTH_PROVIDER(2007, UNAUTHORIZED, "지원하지 않는 OAuth 제공자 입니다."),
    EXPIRED_ACCESS_TOKEN(2008, UNAUTHORIZED, "만료된 AccessToken 입니다."),
    EXPIRED_REFRESH_TOKEN(2009, UNAUTHORIZED, "만료된 RefreshToken 입니다."),
    INVALID_MEMBER_ROLE(2010, UNAUTHORIZED, "유효하지 않은 권한 정보 입니다."),
    FAIL_OAUTH_USERINFO_RETRIEVAL(2011, UNAUTHORIZED, "회원 정보를 가져오는데 실패했습니다."),
    UNAUTHORIZED_FOLLOW_ACCESS(2040, FORBIDDEN, "팔로우 관계에 접근할 권한이 없습니다."),

    NOT_FOUND_USER_ID(3001, NOT_FOUND, "요청한 ID에 해당하는 사용자가 존재하지 않습니다."),
    NOT_FOUND_USER_LOGIN_ID(3002, NOT_FOUND, "요청한 Login ID에 해당하는 사용자가 존재하지 않습니다."),
    NOT_FOUND_USER_EMAIL(3003, NOT_FOUND, "요청한 이메일에 해당하는 사용자가 존재하지 않습니다."),
    NOT_FOUND_IMAGE_FILE(3004, NOT_FOUND, "요청한 이미지 파일을 찾을 수 없습니다."),
    NOT_FOUND_VERIFICATION_CODE(3005, NOT_FOUND, "요청에 해당하는 검증 코드가 존재하지 않습니다."),
    NOT_FOUND_MEMBER(3006, NOT_FOUND, "요청한 ID에 해당하는 Member가 없습니다."),

    NOT_FOUND_NOTICE(3020, NOT_FOUND, "요청한 ID에 해당하는 공지사항이 없습니다."),
    NOT_FOUND_LIKE_REGION(3030, NOT_FOUND, "요청한 ID에 해당하는 관심 지역이 없습니다."),
    NOT_FOUND_FOLLOW(3040, NOT_FOUND, "요청한 ID에 해당하는 Follow가 없습니다."),

    SERVER_ERROR(4000, INTERNAL_SERVER_ERROR, "서버 에러가 발생하였습니다."),
    DIRECTORY_ACCESS_ERROR(4001, INTERNAL_SERVER_ERROR, "디렉토리에서 파일을 읽는 중 문제가 발생했습니다.");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
