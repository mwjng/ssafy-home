package ssafy.ssafyhome.member.application.response;

import ssafy.ssafyhome.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MyInfoResponse(
    Long memberId,
    String nickname,
    String name,
    String email,
    String loginId,
    String imageUrl,
    String memberRole,
    String socialType,
    String status,
    LocalDateTime lastLogin,
    LocalDateTime createdAt
) {
    public static MyInfoResponse from(final Member member) {
        return new MyInfoResponse(
            member.getId(),
            member.getNickname(),
            member.getName(),
            member.getEmail(),
            member.getSocialLoginId(),
            member.getDirName(),
            member.getMemberRole().name(),
            member.getSocialType().name(),
            member.getStatus().name(),
            member.getLastLogin(),
            member.getCreatedAt()
        );
    }

    public static MyInfoResponse of(final Member member, final String imagePath) {
        return new MyInfoResponse(
            member.getId(),
            member.getNickname(),
            member.getName(),
            member.getEmail(),
            member.getSocialLoginId(),
            imagePath,
            member.getMemberRole().name(),
            member.getSocialType().name(),
            member.getStatus().name(),
            member.getLastLogin(),
            member.getCreatedAt()
        );
    }
}
