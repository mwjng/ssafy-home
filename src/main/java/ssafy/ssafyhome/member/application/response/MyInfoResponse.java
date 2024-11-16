package ssafy.ssafyhome.member.application.response;

import ssafy.ssafyhome.member.domain.Member;

import java.time.LocalDateTime;

public record MyInfoResponse(
    String nickname,
    String name,
    String email,
    String loginId,
    String imageUrl,
    String memberRole,
    String socialType,
    String status,
    LocalDateTime lastLogin
) {
    public static MyInfoResponse from(final Member member) {
        return new MyInfoResponse(
            member.getNickname(),
            member.getName(),
            member.getEmail(),
            member.getSocialLoginId(),
            member.getImageUrl(),
            member.getMemberRole().name(),
            member.getSocialType().name(),
            member.getStatus().name(),
            member.getLastLogin()
        );
    }
}
