package ssafy.ssafyhome.auth.infrastructure;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ssafy.ssafyhome.auth.application.AuthToken;
import ssafy.ssafyhome.auth.exception.ExpiredTokenException;
import ssafy.ssafyhome.auth.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;
import static ssafy.ssafyhome.common.exception.ErrorCode.*;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final Long accessTokenExpTime;
    private final Long refreshTokenExpTime;

    public JwtProvider(
        @Value("${security.jwt.token.secret-key}") final String secretKey,
        @Value("${security.jwt.token.access-expiration-time}") final Long accessTokenExpTime,
        @Value("${security.jwt.token.refresh-expiration-time}") final Long refreshTokenExpTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
        this.accessTokenExpTime = Long.valueOf(accessTokenExpTime);
        this.refreshTokenExpTime = Long.valueOf(refreshTokenExpTime);
    }

    public AuthToken generateAccessAndRefreshToken(final String subject) {
        return new AuthToken(
            generateAccessToken(subject),
            generateRefreshToken(subject)
        );
    }

    public String generateAccessToken(final String subject) {
        return generateToken(subject, accessTokenExpTime);
    }

    private String generateRefreshToken(final String subject) {
        return generateToken(subject, refreshTokenExpTime);
    }

    public void validateAccessToken(final String accessToken) {
        try {
            parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(EXPIRED_ACCESS_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }
    }

    public void validateRefreshToken(final String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(EXPIRED_REFRESH_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException(INVALID_REFRESH_TOKEN);
        }
    }

    private String generateToken(final String subject, final Long validityInMilliseconds) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .subject(subject)
            .issuedAt(now)
            .expiration(validity)
            .signWith(secretKey)
            .compact();
    }

    public String getSubject(final String token) {
        return parseToken(token)
            .getPayload()
            .getSubject();
    }

    private Jws<Claims> parseToken(final String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token);
    }
}
