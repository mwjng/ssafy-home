package ssafy.ssafyhome.directmessage.application.response;

import java.util.List;

public record SentMessagesResponse(List<SentMessageResponse> messages) {
}
