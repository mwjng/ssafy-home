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
    private Long id;

    @Column(nullable = false)
    private String nickname;

    private String name;

    private String email;

    @Column(nullable = false, unique = true)
    private Long userId;

    private String imageUrl;

    @Enumerated(STRING)
    private MemberStatus status;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @Builder
    public Member(
        final String nickname,
        final String name,
        final String email,
        final Long userId,
        final String imageUrl
    ) {
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.status = ACTIVE;
        this.lastLogin = now();
    }

    public void updateLoginDate(LocalDateTime loginDateTime) {
        this.lastLogin = loginDateTime;
    }
}
