package ssafy.ssafyhome.member.presentation.request;

import jakarta.servlet.http.HttpServletRequest;

public record RequestUrl(
    String protocol,
    String address,
    int port
) {
    public RequestUrl(HttpServletRequest request) {
        this(request.getProtocol(), request.getServerName(), request.getServerPort());
    }

    public String getBaseUrl() {
        return String.format("%s://%s:%d", protocol.split("/")[0], address, port);
    }
}
