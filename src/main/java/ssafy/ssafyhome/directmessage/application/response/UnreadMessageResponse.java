package ssafy.ssafyhome.directmessage.application.response;

public record UnreadMessageResponse(
        boolean hasUnreadMessages,
        int unreadCount) {
}
