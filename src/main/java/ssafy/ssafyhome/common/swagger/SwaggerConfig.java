package ssafy.ssafyhome.common.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "SSAFYHOME API 명세서",
                description = "<h3>SSAFYHOME API Reference for Developers</h3>Swagger를 이용한 SSAFYHOME API<br>",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

}