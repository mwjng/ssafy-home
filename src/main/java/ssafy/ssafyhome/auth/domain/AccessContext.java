package ssafy.ssafyhome.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static ssafy.ssafyhome.auth.domain.Authority.*;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class AccessContext {

    private final Long memberId;
    private final Authority authority;

    public static AccessContext user(final Long memberId) {
        return new AccessContext(memberId, USER);
    }

    public static AccessContext agent(final Long memberId) {
        return new AccessContext(memberId, AGENT);
    }

    public static AccessContext admin(final Long memberId) {
        return new AccessContext(memberId, ADMIN);
    }

    public boolean isUser() {
        return USER.equals(authority) || AGENT.equals(authority) || ADMIN.equals(authority);
    }

    public boolean isAgent() {
        return AGENT.equals(authority) || ADMIN.equals(authority);
    }

    public boolean isAdmin() {
        return ADMIN.equals(authority);
    }
}
