package ssafy.ssafyhome.image.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.common.exception.FileException;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.image.infrastructure.ImageUploader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;
import static ssafy.ssafyhome.common.exception.ErrorCode.DIRECTORY_ACCESS_ERROR;

@Service
public class ImageService {

    private static final String COMMON_IMG_PATH = "/images/";

    private final ImageUploader imageUploader;
    private final String imageDirPath;

    public ImageService(final ImageUploader imageUploader, @Value("${file.image.dir}") final String imageDirPath) {
        this.imageUploader = imageUploader;
        this.imageDirPath = imageDirPath;
    }

    public String save(final List<MultipartFile> images, final String dirName) {
        if(images == null) {
            return null;
        }
        return imageUploader.uploadImages(images, dirName, imageDirPath);
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void delete(final ImageEvent event) {
        final List<String> imagePaths = event.imagePaths();
        imagePaths.parallelStream()
            .map(Path::of)
            .forEach(this::deleteFile);

        deleteFile(Path.of(event.dirPath()));
    }

    private void deleteFile(final Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileException(DIRECTORY_ACCESS_ERROR, e);
        }
    }

    public List<String> getImageFilePaths(String dirName, final String imgDir) {
        final List<String> imageFileNames = getImageFileNames(dirName, imgDir);
        return imageFileNames.stream()
            .map(imageFileName -> getImageFileDirPath(dirName, imgDir) + File.separator + imageFileName)
            .toList();
    }

    public List<String> getImageFileNames(final String dirName, final String imgDir) {
        try (Stream<Path> paths = Files.list(Path.of(getImageFileDirPath(dirName, imgDir)))) {
            return paths.filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .toList();
        } catch (NoSuchFileException e) {
            return List.of();
        } catch (IOException e) {
            throw new FileException(DIRECTORY_ACCESS_ERROR, e);
        }
    }

    public String getImageFileDirPath(final String dirName, final String imgDir) {
        return imageDirPath + imgDir + dirName;
    }

    public List<String> getImageUrlList(
        final String baseUrl,
        final String profileImgDir,
        final List<String> imageFileNames,
        final String dirName
        ) {
        final String imageBaseUrl = baseUrl + COMMON_IMG_PATH + profileImgDir + dirName;
        return imageFileNames.stream()
            .map(imageFileName -> imageBaseUrl + File.separator + imageFileName)
            .toList();
    }
}
