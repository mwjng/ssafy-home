package ssafy.ssafyhome.image.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.image.infrastructure.ImageUploader;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageUploader imageUploader;

    public List<String> save(final List<MultipartFile> images, final String dirName) {
        return imageUploader.uploadImages(images, dirName);
    }

}
