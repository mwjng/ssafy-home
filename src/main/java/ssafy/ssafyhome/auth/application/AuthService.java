package ssafy.ssafyhome.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.auth.domain.RefreshToken;
import ssafy.ssafyhome.auth.domain.repository.RefreshTokenRepository;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.auth.exception.InvalidTokenException;
import ssafy.ssafyhome.auth.infrastructure.*;
import ssafy.ssafyhome.auth.presentation.request.LoginRequest;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;

import java.time.LocalDateTime;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final OAuthProviders providers;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthToken socialLogin(final LoginRequest request, final LocalDateTime time) {
        validateMemberRole(request);

        final OAuthProvider provider = providers.getProvider(request.provider());
        final OAuthUserInfo userInfo = provider.getOAuthUserInfo(request.code());

        final Member member = findOrCreateMember(userInfo, request.getMemberRole());
        validateMemberStatus(member);
        member.updateLoginDate(time);

        final AuthToken authToken = jwtProvider.generateAccessAndRefreshToken(member.getId().toString());
        saveRefreshToken(authToken, member);
        return authToken;
    }

    @Transactional
    public AuthToken login(final LoginRequest request, final LocalDateTime time) {
        final Member member = memberRepository
            .findBySocialLoginId(request.loginId())
            .orElseThrow(() -> new AuthException(INVALID_USER_ID));

        validateMemberStatus(member);
        if(passwordEncoder.matches(request.password(), member.getPassword())) {
            member.updateLoginDate(time);
            final AuthToken authToken = jwtProvider.generateAccessAndRefreshToken(member.getId().toString());
            saveRefreshToken(authToken, member);
            return authToken;
        }

        throw new AuthException(INVALID_PASSWORD);
    }

    public AccessTokenResponse renewalAccessToken(final String refreshToken) {
        jwtProvider.validateRefreshToken(refreshToken);

        final String memberId = refreshTokenRepository.findById(refreshToken)
            .orElseThrow(() -> new InvalidTokenException(INVALID_REFRESH_TOKEN))
            .getMemberId().toString();
        return new AccessTokenResponse(jwtProvider.generateAccessToken(memberId));
    }

    @Transactional
    public void removeRefreshToken(final String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    private static void validateMemberStatus(final Member member) {
        if(!member.isActive()) {
            throw new AuthException(INVALID_MEMBER_STATUS);
        }
    }

    private void validateMemberRole(final LoginRequest request) {
        if(request.isAdmin()) {
            throw new AuthException(INVALID_MEMBER_ROLE);
        }
    }

    private Member findOrCreateMember(final OAuthUserInfo userInfo, final MemberRole memberRole) {
        return memberRepository.findBySocialLoginId(userInfo.getSocialLoginId())
            .orElseGet(() -> memberRepository.save(userInfo.toMember(memberRole)));
    }

    private void saveRefreshToken(final AuthToken authToken, final Member member) {
        final RefreshToken refreshToken = new RefreshToken(authToken.refreshToken(), member.getId());
        refreshTokenRepository.save(refreshToken);
    }
}
