package ssafy.ssafyhome.common.ai;

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

    @Value("classpath:prompts/system-trend.st")
    private Resource trendSystemPromptResource;

    @Value("classpath:prompts/system-information.st")
    private Resource informationSystemPromptResource;

    @Value("classpath:prompts/system-news.st")
    private Resource newsSystemPromptResource;

    @Value("classpath:prompts/system-standard.st")
    private Resource standardSystemPromptResource;

    @Value("classpath:prompts/system-advice.st")
    private Resource adviceSystemPromptResource;

    @Value("classpath:prompts/system-default.st")
    private Resource defaultSystemPromptResource;

    private Map<PromptType, Resource> systemPromptResources;


    @PostConstruct
    public void initPromptResources() {
        systemPromptResources = new EnumMap<>(PromptType.class);
        systemPromptResources.put(TREND, trendSystemPromptResource);
        systemPromptResources.put(INFORMATION, informationSystemPromptResource);
        systemPromptResources.put(NEWS, newsSystemPromptResource);
        systemPromptResources.put(STANDARD, standardSystemPromptResource);
        systemPromptResources.put(ADVICE, adviceSystemPromptResource);
        systemPromptResources.put(DEFAULT, defaultSystemPromptResource);
    }

    public String getSystemPromptResource(PromptType promptType) {
        try {
            return new String(FileCopyUtils.copyToByteArray(systemPromptResources.get(promptType).getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e){
            throw new RuntimeException("Failed to load system prompt template", e);
        }
    }
}
