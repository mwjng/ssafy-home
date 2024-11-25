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

    @Value("classpath:prompts/user-trend.st")
    private Resource trendUserPromptResource;

    @Value("classpath:prompts/user-information.st")
    private Resource informationUserPromptResource;

    @Value("classpath:prompts/user-news.st")
    private Resource newsUserPromptResource;

    @Value("classpath:prompts/user-standard.st")
    private Resource standardUserPromptResource;

    @Value("classpath:prompts/user-advice.st")
    private Resource adviceUserPromptResource;

    private Map<PromptType, Resource> systemPromptResources;

    private Map<PromptType, Resource> userPromptResources;

    @PostConstruct
    public void initPromptResources() {
        makeSystemPromptResources();
        makeUserPromptResources();
    }

    private void makeSystemPromptResources() {
        systemPromptResources = new EnumMap<>(PromptType.class);
        systemPromptResources.put(TREND, trendSystemPromptResource);
        systemPromptResources.put(INFORMATION, informationSystemPromptResource);
        systemPromptResources.put(NEWS, newsSystemPromptResource);
        systemPromptResources.put(STANDARD, standardSystemPromptResource);
        systemPromptResources.put(ADVICE, adviceSystemPromptResource);
        systemPromptResources.put(DEFAULT, defaultSystemPromptResource);
    }

    private void makeUserPromptResources() {
        userPromptResources = new EnumMap<>(PromptType.class);
        userPromptResources.put(TREND, trendUserPromptResource);
        userPromptResources.put(INFORMATION, informationUserPromptResource);
        userPromptResources.put(NEWS, newsUserPromptResource);
        userPromptResources.put(STANDARD, standardUserPromptResource);
        userPromptResources.put(ADVICE, adviceUserPromptResource);
    }

    public String getSystemPromptResource(PromptType promptType) {
        try {
            return new String(FileCopyUtils.copyToByteArray(systemPromptResources.get(promptType).getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e){
            throw new RuntimeException("Failed to load system prompt template", e);
        }
    }

    public String getUserPromptResource(PromptType promptType) {
        try {
            return new String(FileCopyUtils.copyToByteArray(userPromptResources.get(promptType).getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e){
            throw new RuntimeException("Failed to load user prompt template", e);
        }
    }
}
