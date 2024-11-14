package ssafy.ssafyhome.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.application.AccessTokenResponse;
import ssafy.ssafyhome.auth.application.AuthService;
import ssafy.ssafyhome.auth.application.AuthToken;
import ssafy.ssafyhome.auth.presentation.request.LoginMember;
import ssafy.ssafyhome.auth.presentation.request.LoginRequest;
import ssafy.ssafyhome.member.application.MemberService;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
        @Valid @RequestBody final LoginRequest loginRequest
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

    @GetMapping("/member")
    public ResponseEntity<MemberNicknameResponse> getMemberNickname(
        @AuthenticationPrincipal final LoginMember loginMember
    ) {
        return ResponseEntity.status(OK)
            .body(memberService.getNicknameById(loginMember.memberId()));
    }

    @GetMapping("/reissue")
    public ResponseEntity<AccessTokenResponse> reissueAccessToken(
        @CookieValue("refreshToken") final String refreshToken
    ) {
        return ResponseEntity.status(CREATED)
            .body(authService.renewalAccessToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @AuthenticationPrincipal final LoginMember loginMember,
        @CookieValue("refreshToken") final String refreshToken
    ) {
        authService.removeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
