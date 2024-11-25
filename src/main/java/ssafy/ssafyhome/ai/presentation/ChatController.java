package ssafy.ssafyhome.ai.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ssafy.ssafyhome.ai.presentation.request.ChatRequest;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController {

    private final OpenAiChatModel chatModel;

    @PostMapping
    public Map<String,String> generate(@RequestBody ChatRequest chatRequest) {
        return Map.of("generation", this.chatModel.call(chatRequest.message()));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}
