package ssafy.ssafyhome.auth.presentation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.exception.AuthException;

import java.util.Arrays;

import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_AUTHORITY;

@Aspect
@Component
public class AgentAccessAspect {

    @Before("@annotation(ssafy.ssafyhome.auth.presentation.AgentAccess)")
    public void agentAccess(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
            .filter(AccessContext.class::isInstance)
            .map(AccessContext.class::cast)
            .filter(AccessContext::isAgent)
            .findAny()
            .orElseThrow(() -> new AuthException(INVALID_AUTHORITY));
    }
}
