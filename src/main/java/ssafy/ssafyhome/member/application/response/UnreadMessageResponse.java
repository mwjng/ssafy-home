package ssafy.ssafyhome.member.application.response;

public record UnreadMessageResponse(
        boolean hasUnreadMessages,
        int unreadCount) {
}
