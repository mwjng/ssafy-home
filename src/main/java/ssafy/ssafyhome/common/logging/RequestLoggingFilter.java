package ssafy.ssafyhome.common.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(final HttpServletRequest request, final String message) {
        log.info(message);
    }

    @Override
    protected void afterRequest(final HttpServletRequest request, final String message) {
    }
}
