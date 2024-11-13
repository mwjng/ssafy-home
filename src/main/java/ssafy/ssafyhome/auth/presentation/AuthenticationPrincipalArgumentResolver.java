package ssafy.ssafyhome.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ssafy.ssafyhome.auth.application.AuthService;
import ssafy.ssafyhome.auth.infrastructure.JwtProvider;
import ssafy.ssafyhome.auth.presentation.request.LoginMember;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationTokenExtractor extractor;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        final String accessToken = extractor.extract(authorizationHeader);
        jwtProvider.validateAccessToken(accessToken);
        final Long memberId = Long.valueOf(jwtProvider.getSubject(accessToken));
        return new LoginMember(memberId);
    }
}
