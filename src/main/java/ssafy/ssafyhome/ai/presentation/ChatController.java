package ssafy.ssafyhome.ai.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ssafy.ssafyhome.ai.presentation.request.ChatRequest;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController {

    private final OpenAiChatModel chatModel;

    @PostMapping
    public Flux<ChatResponse> generateStream(@RequestBody ChatRequest chatRequest) {
        String customPrompt = "당신은 부동산 매물과 관련된 정보를 제공하는 AI 도우미입니다. 질문에 명확하고 간결하게 답변해주세요.\n";
        Prompt prompt = new Prompt(new UserMessage(customPrompt + chatRequest.message()));
        return this.chatModel.stream(prompt);
    }
}