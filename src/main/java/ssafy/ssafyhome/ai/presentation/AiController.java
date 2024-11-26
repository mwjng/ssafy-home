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
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.common.ai.PromptTemplateLoader;

import static ssafy.ssafyhome.ai.domain.PromptType.*;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class AiController {

    private final OpenAiChatModel chatModel;
    private final PromptTemplateLoader promptLoader;

    @PostMapping
    @UserAccess
    public ResponseEntity<PromptResponse> chat(
            @AuthenticationPrincipal AccessContext accessContext,
            @Valid@RequestBody final ChatRequest chatRequest) {
        return ResponseEntity.ok().body(PromptResponse.from(getResponse(DEFAULT, chatRequest.message())));
    }

    @PostMapping("/prompts")
    @UserAccess
    public ResponseEntity<PromptResponse> prompt(
            @AuthenticationPrincipal AccessContext accessContext,
            @RequestBody final PromptRequest promptRequest) {
        return ResponseEntity.ok().body(PromptResponse.from(getResponse(promptRequest.promptType(), null)));
    }

    private String getResponse(final PromptType promptType, final String message) {
        SystemMessage systemMessage = new SystemMessage(promptLoader.getSystemPromptResource(promptType));
        if(message == null){
            return chatModel.call(systemMessage);
        }
        UserMessage userMessage = new UserMessage(message);
        return chatModel.call(systemMessage, userMessage);
    }
}