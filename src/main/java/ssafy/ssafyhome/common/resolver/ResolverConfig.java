package ssafy.ssafyhome.common.resolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipalArgumentResolver;

import java.util.List;

@Configuration
public class ResolverConfig implements WebMvcConfigurer {

    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    public ResolverConfig(final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver) {
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationPrincipalArgumentResolver);
    }
}
