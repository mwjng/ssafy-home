package ssafy.ssafyhome.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.auth.domain.RefreshToken;
import ssafy.ssafyhome.auth.domain.repository.RefreshTokenRepository;
import ssafy.ssafyhome.auth.exception.InvalidTokenException;
import ssafy.ssafyhome.auth.infrastructure.JwtProvider;
import ssafy.ssafyhome.auth.infrastructure.OAuthProvider;
import ssafy.ssafyhome.auth.infrastructure.OAuthProviders;
import ssafy.ssafyhome.auth.infrastructure.OAuthUserInfo;
import ssafy.ssafyhome.auth.presentation.request.LoginRequest;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.MemberRole;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;

import java.time.LocalDateTime;

import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_REFRESH_TOKEN;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final OAuthProviders providers;

    @Transactional
    public AuthToken login(final LoginRequest request, final LocalDateTime time) {
        final OAuthProvider provider = providers.getProvider(request.provider());
        final OAuthUserInfo userInfo = provider.getOAuthUserInfo(request.code());
        final Member member = findOrCreateMember(userInfo, validateMemberRole(request));
        member.updateLoginDate(time);
        final AuthToken authToken = jwtProvider.generateAccessAndRefreshToken(member.getId().toString());
        saveRefreshToken(authToken, member);
        return authToken;
    }

    public AccessTokenResponse renewalAccessToken(final String refreshToken) {
        jwtProvider.validateRefreshToken(refreshToken);
        final String memberId = refreshTokenRepository.findById(refreshToken)
            .orElseThrow(() -> new InvalidTokenException(INVALID_REFRESH_TOKEN))
            .getMemberId().toString();
        return new AccessTokenResponse(jwtProvider.generateAccessToken(memberId));
    }

    private Member findOrCreateMember(final OAuthUserInfo userInfo, final MemberRole memberRole) {
        return memberRepository.findBySocialLoginId(userInfo.getSocialLoginId())
            .orElseGet(() -> memberRepository.save(userInfo.toMember(memberRole)));
    }

    private MemberRole validateMemberRole(final LoginRequest request) {
        return MemberRole.getMemberRole(request.memberRole());
    }

    private void saveRefreshToken(final AuthToken authToken, final Member member) {
        final RefreshToken refreshToken = new RefreshToken(authToken.refreshToken(), member.getId());
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void removeRefreshToken(final String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }
}