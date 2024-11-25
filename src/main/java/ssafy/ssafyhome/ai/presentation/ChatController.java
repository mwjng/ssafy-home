package ssafy.ssafyhome.ai.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ssafy.ssafyhome.ai.presentation.request.ChatRequest;
import ssafy.ssafyhome.ai.presentation.request.PromptRequest;
import ssafy.ssafyhome.common.util.PromptTemplateLoader;

import java.util.function.Predicate;

import static org.springframework.http.HttpStatus.*;
import static ssafy.ssafyhome.ai.domain.PromptType.*;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController {

    private final OpenAiChatModel chatModel;
    private final PromptTemplateLoader promptLoader;

    @PostMapping
    public ResponseEntity<Flux<String>> chatStream(@Valid @RequestBody final ChatRequest chatRequest) {
        Prompt prompt = new Prompt(new UserMessage(promptLoader.getPromptResource(DEFAULT) + chatRequest.message()));
        return getBody(prompt);
    }

    @PostMapping("/prompts")
    public ResponseEntity<Flux<String>> promptChatStream(@RequestBody final PromptRequest promptRequest) {
        Prompt prompt = new Prompt(new UserMessage(promptLoader.getPromptResource(promptRequest.promptType())));
        return getBody(prompt);
    }

    @Async
    private ResponseEntity<Flux<String>> getBody(final Prompt prompt) {
        return ResponseEntity.status(CREATED).body(this.chatModel.stream(prompt)
                .filter(checkNullResponse())
                .map(chatResponse -> chatResponse.getResult().getOutput().getContent()));
    }

    private Predicate<ChatResponse> checkNullResponse() {
        return chatResponse -> chatResponse != null && chatResponse.getResult() != null &&
                chatResponse.getResult().getOutput() != null &&
                chatResponse.getResult().getOutput().getContent() != null;
    }
}