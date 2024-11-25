package ssafy.ssafyhome.ai.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.ai.application.PromptResponse;
import ssafy.ssafyhome.ai.domain.PromptType;
import ssafy.ssafyhome.ai.presentation.request.ChatRequest;
import ssafy.ssafyhome.ai.presentation.request.PromptRequest;
import ssafy.ssafyhome.common.ai.PromptTemplateLoader;

import static ssafy.ssafyhome.ai.domain.PromptType.*;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class AiController {

    private final OpenAiChatModel chatModel;
    private final PromptTemplateLoader promptLoader;

    @GetMapping
    public ResponseEntity<PromptResponse> chat(@Valid @RequestBody final ChatRequest chatRequest) {
        return ResponseEntity.ok().body(PromptResponse.from(getResponse(DEFAULT, chatRequest.message())));
    }

    @PostMapping("/prompts")
    public ResponseEntity<PromptResponse> prompt(@RequestBody final PromptRequest promptRequest) {
        return ResponseEntity.ok().body(PromptResponse.from(getResponse(promptRequest.promptType(), null)));
    }

    private String getResponse(final PromptType promptType, final String message) {
        SystemMessage systemMessage = new SystemMessage(promptLoader.getSystemPromptResource(promptType));
        UserMessage userMessage = getUserMessage(promptType, message);
        return chatModel.call(systemMessage, userMessage);
    }

    private UserMessage getUserMessage(final PromptType promptType, final String message) {
        return promptType == DEFAULT ? new UserMessage(message) : new UserMessage(promptLoader.getUserPromptResource(promptType));
    }
}