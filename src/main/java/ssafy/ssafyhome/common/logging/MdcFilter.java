package ssafy.ssafyhome.common.logging;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Component
@Order(HIGHEST_PRECEDENCE)
public class MdcFilter implements Filter {

    private static final String CORRELATION_ID = "CORRELATION_ID";

    @Override
    public void doFilter(
        final ServletRequest request,
        final ServletResponse response,
        final FilterChain chain
    ) throws IOException, ServletException {
        MDC.put(CORRELATION_ID, generateCorrelationId());
        chain.doFilter(request, response);
        MDC.clear();
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
