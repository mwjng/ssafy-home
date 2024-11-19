package ssafy.ssafyhome.image.domain;

import java.util.List;

public record ImageEvent(String dirPath, List<String> imagePaths) {
}
