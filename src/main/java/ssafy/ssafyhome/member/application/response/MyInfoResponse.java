package ssafy.ssafyhome.member.application.response;

import ssafy.ssafyhome.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

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
            member.getDirName(),
            member.getMemberRole().name(),
            member.getSocialType().name(),
            member.getStatus().name(),
            member.getLastLogin()
        );
    }

    public static MyInfoResponse of(final Member member, final String imagePath) {
        return new MyInfoResponse(
            member.getNickname(),
            member.getName(),
            member.getEmail(),
            member.getSocialLoginId(),
            imagePath,
            member.getMemberRole().name(),
            member.getSocialType().name(),
            member.getStatus().name(),
            member.getLastLogin()
        );
    }
}
