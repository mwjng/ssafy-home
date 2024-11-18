package ssafy.ssafyhome.image.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.image.exception.ImageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static ssafy.ssafyhome.common.exception.ErrorCode.FAIL_IMAGE_UPLOAD;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_IMAGE_FORMAT;

@Component
public class FileSystemImageUploader implements ImageUploader {

    @Value("${file.image.dir}")
    private String uploadDir;

    @Override
    public List<String> uploadImages(final List<MultipartFile> images, final String dirName) {
        return images.stream()
            .map(image -> uploadImage(image, dirName))
            .toList();
    }

    private String uploadImage(final MultipartFile image, final String dirName) {
        final String originalName = image.getOriginalFilename();
        final String imagePath = getImageFullPath(originalName, uploadDir + dirName);
        try {
            Path path = Path.of(imagePath);
            Files.createDirectories(path.getParent());
            Files.copy(image.getInputStream(), path, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImageException(FAIL_IMAGE_UPLOAD, e);
        }
        return imagePath;
    }

    private String getImageFullPath(final String originalName, final String imagePath) {
        final String extension = getExtension(originalName);
        return imagePath + UUID.randomUUID().toString().substring(0, 8) + extension;
    }

    private String getExtension(final String originalName) {
        if (originalName == null || !originalName.contains(".")) {
            throw new ImageException(INVALID_IMAGE_FORMAT);
        }
        return originalName.substring(originalName.lastIndexOf("."));
    }
}
