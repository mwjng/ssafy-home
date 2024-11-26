package ssafy.ssafyhome.ai.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.common.ai.ChoicePromptTemplateLoader;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController2 {

    private final ChatModel chatModel;
    private final ChoicePromptTemplateLoader promptLoader;

    @GetMapping("/{purpose}/{location}/{budget}")
    public ResponseEntity<String> getAttractions(
        @PathVariable String purpose,
        @PathVariable String location,
        @PathVariable String budget
    ) {
        try {
            final Map<String, String> params = Map.of(
                "purpose", purpose,
                "location", location,
                "budget", budget
            );
            final String userCommand = makeUserTemplate(params);
            final String systemCommand = makeSystemTemplate();
            final String response = sendMessage(userCommand, systemCommand);

            System.out.println(response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing request: " + e.getMessage());
        }
    }

    private String sendMessage(final String userCommand, final String systemCommand) {
        Message userMessage = new UserMessage(userCommand);
        Message systemMessage = new SystemMessage(systemCommand);
        return chatModel.call(userMessage, systemMessage);
    }

    private String makeSystemTemplate() {
        String systemPromptTemplate = promptLoader.loadSystemPrompt();
        PromptTemplate systemTemplate = new PromptTemplate(systemPromptTemplate);
        return systemTemplate.render();
    }

    private String makeUserTemplate(Map<String, String> params) {
        String userPromptTemplate = promptLoader.loadUserPrompt();
        PromptTemplate userTemplate = new PromptTemplate(userPromptTemplate);
        params.forEach(userTemplate::add);
        return userTemplate.render();
    }
}
