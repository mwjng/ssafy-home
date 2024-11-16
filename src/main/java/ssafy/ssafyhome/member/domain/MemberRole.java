package ssafy.ssafyhome.member.domain;

import ssafy.ssafyhome.auth.exception.AuthException;

import java.util.Arrays;

import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_MEMBER_ROLE;

public enum MemberRole {

    USER,
    ADMIN,
    AGENT,
    MASTER;

    public static MemberRole getMemberRole(String memberRole) {
        return Arrays.stream(MemberRole.values())
            .filter(role -> role.name().equalsIgnoreCase(memberRole))
            .findFirst()
            .orElseThrow(() -> new AuthException(INVALID_MEMBER_ROLE));
    }
}
