package ssafy.ssafyhome.ai.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RequiredArgsConstructor
//@RequestMapping("/chat")
//@RestController
//public class ChatController {
//
//    private final OpenAiChatModel chatModel;
//
//    @GetMapping("/ai/generate")
//    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        return Map.of("generation", this.chatModel.call(message));
//    }
//
//    @GetMapping("/ai/generateStream")
//    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        Prompt prompt = new Prompt(new UserMessage(message));
//        return this.chatModel.stream(prompt);
//    }
//}
