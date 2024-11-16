package ssafy.ssafyhome.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.auth.infrastructure.JwtProvider;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_AUTHORITY;
import static ssafy.ssafyhome.member.domain.MemberRole.*;

@RequiredArgsConstructor
@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationTokenExtractor extractor;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public AccessContext resolveArgument(
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
        final Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(() -> new AuthException(INVALID_AUTHORITY));

        final AccessContext accessContext = getAccessContext(member, memberId);
        if (accessContext != null) {
            return accessContext;
        }

        throw new AuthException(INVALID_AUTHORITY);
    }

    private AccessContext getAccessContext(final Member member, final Long memberId) {
        if(member.getMemberRole().equals(USER)) {
            return AccessContext.user(memberId);
        }
        if (member.getMemberRole().equals(AGENT)) {
            return AccessContext.agent(memberId);
        }
        if (member.getMemberRole().equals(ADMIN)) {
            return AccessContext.admin(memberId);
        }
        if (member.getMemberRole().equals(MASTER)) {
            return AccessContext.master(memberId);
        }
        return null;
    }
}
