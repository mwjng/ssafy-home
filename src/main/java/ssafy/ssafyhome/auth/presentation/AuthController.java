package ssafy.ssafyhome.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.application.AccessTokenResponse;
import ssafy.ssafyhome.auth.application.AuthService;
import ssafy.ssafyhome.auth.application.AuthToken;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.request.LoginRequest;
import ssafy.ssafyhome.common.validation.BasicLoginCheck;
import ssafy.ssafyhome.common.validation.OAuthLoginCheck;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth-login")
    public ResponseEntity<AccessTokenResponse> oauthLogin(
        @Validated(OAuthLoginCheck.class) @RequestBody final LoginRequest loginRequest
    ) {
        final AuthToken authToken = authService.socialLogin(loginRequest, now());
        final ResponseCookie cookie = ResponseCookie.from("refreshToken", authToken.refreshToken())
            .maxAge(3600)
            .httpOnly(true)
            .sameSite("none")
            .secure(true)
            .build();

        return ResponseEntity.status(CREATED)
            .header(SET_COOKIE, cookie.toString())
            .body(new AccessTokenResponse(authToken.accessToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
        @Validated(BasicLoginCheck.class) @RequestBody final LoginRequest loginRequest
    ) {
        final AuthToken authToken = authService.login(loginRequest, now());
        final ResponseCookie cookie = ResponseCookie.from("refreshToken", authToken.refreshToken())
            .maxAge(3600)
            .httpOnly(true)
            .sameSite("none")
            .secure(true)
            .build();

        return ResponseEntity.status(CREATED)
            .header(SET_COOKIE, cookie.toString())
            .body(new AccessTokenResponse(authToken.accessToken()));
    }

    @GetMapping("/reissue")
    public ResponseEntity<AccessTokenResponse> reissueAccessToken(
        @CookieValue("refreshToken") final String refreshToken
    ) {
        return ResponseEntity.status(CREATED)
            .body(authService.renewalAccessToken(refreshToken));
    }

    @PostMapping("/logout")
    @UserAccess
    public ResponseEntity<Void> logout(
        @AuthenticationPrincipal final AccessContext accessContext,
        @CookieValue("refreshToken") final String refreshToken
    ) {
        authService.removeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
