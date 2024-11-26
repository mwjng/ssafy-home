package ssafy.ssafyhome.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UrlUtil {

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        if((scheme.equals("http") && serverPort == 80) ||
            (scheme.equals("https") && serverPort == 443)) {
            return String.format("%s://%s", scheme, serverName);
        }
        return String.format("%s://%s:%d", scheme, serverName, serverPort);
    }
}
