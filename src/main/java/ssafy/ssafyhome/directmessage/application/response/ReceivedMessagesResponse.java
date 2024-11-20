package ssafy.ssafyhome.directmessage.application.response;

import java.util.List;

public record ReceivedMessagesResponse(List<ReceivedMessageResponse> messages) {
}
