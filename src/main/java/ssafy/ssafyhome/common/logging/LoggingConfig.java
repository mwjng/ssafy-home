package ssafy.ssafyhome.common.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        final RequestLoggingFilter filter = new RequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(500);
        return filter;
    }
}
