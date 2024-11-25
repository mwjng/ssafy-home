package ssafy.ssafyhome.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import lombok.extern.slf4j.Slf4j;
import ssafy.ssafyhome.ai.domain.PromptType;

import static ssafy.ssafyhome.ai.domain.PromptType.*;

@Component
@Slf4j
public class PromptTemplateLoader {

    @Value("classpath:prompts/trend.st")
    private Resource trendPromptResource;

    @Value("classpath:prompts/information.st")
    private Resource informationPromptResource;

    @Value("classpath:prompts/news.st")
    private Resource newsPromptResource;

    @Value("classpath:prompts/standard.st")
    private Resource standardPromptResource;

    @Value("classpath:prompts/advice.st")
    private Resource advicePromptResource;

    @Value("classpath:prompts/default.st")
    private Resource defaultPromptResource;

    private Map<PromptType, Resource> promptResources;

    @PostConstruct
    public void initPromptResources() {
        promptResources = new EnumMap<>(PromptType.class);
        promptResources.put(TREND, trendPromptResource);
        promptResources.put(INFORMATION, informationPromptResource);
        promptResources.put(NEWS, newsPromptResource);
        promptResources.put(STANDARD, standardPromptResource);
        promptResources.put(ADVICE, advicePromptResource);
        promptResources.put(DEFAULT, defaultPromptResource);
    }

    public String getPromptResource(PromptType promptType) {
        try {
            return new String(FileCopyUtils.copyToByteArray(promptResources.get(promptType).getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e){
            throw new RuntimeException("Failed to load prompt template", e);
        }
    }
}
