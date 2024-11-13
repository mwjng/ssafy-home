package ssafy.ssafyhome.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.auth.domain.RefreshToken;
import ssafy.ssafyhome.auth.domain.repository.RefreshTokenRepository;
import ssafy.ssafyhome.auth.exception.InvalidTokenException;
import ssafy.ssafyhome.auth.infrastructure.JwtProvider;
import ssafy.ssafyhome.auth.infrastructure.OAuthProvider;
import ssafy.ssafyhome.auth.infrastructure.OAuthUserInfo;
import ssafy.ssafyhome.member.domain.Member;
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
    private final OAuthProvider provider;

    @Transactional
    public AuthToken login(final String code, LocalDateTime time) {
        final OAuthUserInfo userInfo = provider.getOAuthUserInfo(code);
        final Member member = findOrCreateMember(userInfo);
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

    private Member findOrCreateMember(final OAuthUserInfo userInfo) {
        return memberRepository.findByUserId(userInfo.getUserId())
            .orElseGet(() -> memberRepository.save(userInfo.toMember()));
    }

    private void saveRefreshToken(final AuthToken authToken, final Member member) {
        final RefreshToken refreshToken = new RefreshToken(authToken.refreshToken(), member.getId());
        refreshTokenRepository.save(refreshToken);
    }

    public void removeRefreshToken(final String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }
}