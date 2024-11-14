package ssafy.ssafyhome.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PROTECTED;
import static ssafy.ssafyhome.member.domain.MemberStatus.ACTIVE;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String name;

    private String email;

    private String password;

    @Column(nullable = false, unique = true)
    private String socialLoginId;

    private String imageUrl;

    @Enumerated(STRING)
    private MemberRole memberRole;

    @Enumerated(STRING)
    private SocialType socialType;

    @Enumerated(STRING)
    private MemberStatus status;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @Builder
    public Member(
        final String nickname,
        final String name,
        final String email,
        final String socialLoginId,
        final String imageUrl,
        final MemberRole memberRole,
        final SocialType socialType,
        final MemberStatus status,
        final LocalDateTime lastLogin
    ) {
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.imageUrl = imageUrl;
        this.memberRole = memberRole;
        this.socialType = socialType;
        this.status = ACTIVE;
        this.lastLogin = now();
    }

    public void updateLoginDate(LocalDateTime loginDateTime) {
        this.lastLogin = loginDateTime;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
