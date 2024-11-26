package ssafy.ssafyhome.image.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.image.exception.ImageException;

import java.io.File;
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

    @Override
    public String uploadImages(final List<MultipartFile> images, final String dirName, final String imageDirPath) {
        return images.stream()
            .map(image -> uploadImage(image, dirName, imageDirPath))
            .findAny()
            .orElseThrow(() -> new ImageException(FAIL_IMAGE_UPLOAD));
    }

    private String uploadImage(final MultipartFile image, final String dirName, String imageFileDir) {
        final String originalName = image.getOriginalFilename();
        final String imageFileDirName = generateShortUUID();
        final String imageFullPath = getImageFullPath(originalName, imageFileDir + dirName, imageFileDirName);
        try {
            Path path = Path.of(imageFullPath);
            Files.createDirectories(path.getParent());
            Files.copy(image.getInputStream(), path, REPLACE_EXISTING);
            return imageFileDirName;
        } catch (IOException e) {
            throw new ImageException(FAIL_IMAGE_UPLOAD, e);
        }
    }

    private String getImageFullPath(final String originalName, final String imagePath, String imageFileName) {
        final String extension = getExtension(originalName);
        return imagePath + imageFileName +
            File.separator + generateShortUUID() + extension;
    }

    private static String generateShortUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String getExtension(final String originalName) {
        if (originalName == null || !originalName.contains(".")) {
            throw new ImageException(INVALID_IMAGE_FORMAT);
        }
        return originalName.substring(originalName.lastIndexOf("."));
    }
}
