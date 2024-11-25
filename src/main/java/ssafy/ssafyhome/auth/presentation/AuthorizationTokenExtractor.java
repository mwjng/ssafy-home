package ssafy.ssafyhome.auth.presentation;

import org.springframework.stereotype.Component;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.common.exception.BadRequestException;

import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_AUTHORITY;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_REQUEST;

@Component
public class AuthorizationTokenExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public String extract(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_TYPE)) {
            return authorizationHeader.substring(BEARER_TYPE.length()).trim();
        }
        throw new AuthException(INVALID_AUTHORITY);
    }
}
