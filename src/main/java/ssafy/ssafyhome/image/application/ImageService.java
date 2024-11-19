package ssafy.ssafyhome.image.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.common.exception.FileException;
import ssafy.ssafyhome.image.infrastructure.ImageUploader;
import ssafy.ssafyhome.member.domain.Member;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

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

    public List<String> save(final List<MultipartFile> images, final String dirName) {
        return imageUploader.uploadImages(images, dirName, imageDirPath);
    }

    public List<String> getImagePaths(final Member member, String profileImgDir) {
        final String imageDirDetailPath = imageDirPath + profileImgDir + member.getDirName();

        try (Stream<Path> paths = Files.list(Paths.get(imageDirDetailPath))) {
            return paths.filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .toList();
        } catch (IOException e) {
            throw new FileException(DIRECTORY_ACCESS_ERROR, e);
        }
    }

    public List<String> getImageUrlList(
        final String baseUrl,
        final String profileImgDir,
        final Member member,
        final List<String> imageFileNames
    ) {
        final String imageBaseUrl = baseUrl + COMMON_IMG_PATH + profileImgDir + member.getDirName();
        return imageFileNames.stream()
            .map(imageFileName -> imageBaseUrl + File.separator + imageFileName)
            .toList();
    }
}
