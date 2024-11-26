package ssafy.ssafyhome.ai.application;

public record PromptResponse(
        String response) {

    public static PromptResponse from(String response){
        return new PromptResponse(response);
    }
}
