package ssafy.ssafyhome.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.ssafyhome.common.auditing.BaseEntity;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.*;
import static ssafy.ssafyhome.member.domain.MemberStatus.ACTIVE;
import static ssafy.ssafyhome.member.domain.MemberStatus.DORMANT;

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

    private String dirName;

    @Enumerated(STRING)
    private MemberRole memberRole;

    @Enumerated(STRING)
    private SocialType socialType;

    @Enumerated(STRING)
    private MemberStatus status;

    private LocalDateTime lastLogin;

    @Builder
    public Member(
        final String nickname,
        final String name,
        final String email,
        final String password,
        final String socialLoginId,
        final String dirName,
        final MemberRole memberRole,
        final SocialType socialType,
        final LocalDateTime lastLogin
    ) {
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.password = password;
        this.socialLoginId = socialLoginId;
        this.dirName = dirName;
        this.memberRole = memberRole;
        this.socialType = socialType;
        this.status = ACTIVE;
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return status == ACTIVE;
    }

    public void updateLoginDate(LocalDateTime loginDateTime) {
        this.lastLogin = loginDateTime;
    }

    public boolean isChangedNickname(String newNickname) {
        return !this.nickname.equals(newNickname) && hasText(newNickname);
    }

    public boolean isChangedLoginId(String newLoginId) {
        return !this.socialLoginId.equals(newLoginId);
    }

    public boolean isOAuthLogin() {
        return !SocialType.NONE.equals(this.socialType);
    }

    public void changeMemberInfo(String nickname, String name, String email) {
        if(hasText(nickname)) {
            changeNickname(nickname);
        }
        if(hasText(name)) {
            this.name = name;
        }
        if(hasText(email)) {
            this.email = email;
        }
    }

    public void changeEmail(String email) { this.email = email; }

    public void changeProfileImageUrl(String imageUrl) {
        this.dirName = imageUrl;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changeLoginId(String newLoginId) {
        this.socialLoginId = newLoginId;
    }

    public void changePassword(String newHashedPassword) {
        this.password = newHashedPassword;
    }

    public static Member withId(Long id) {
        Member member = new Member();
        member.id = id;
        return member;
    }

    public void changeStatus() {
        this.status = status == ACTIVE ? DORMANT : ACTIVE;
    }
}
