package ssafy.ssafyhome.common.client;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@ConfigurationPropertiesScan
@Configuration
public class ClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
