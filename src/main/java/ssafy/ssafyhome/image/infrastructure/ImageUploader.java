package ssafy.ssafyhome.image.infrastructure;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {

    List<String> uploadImages(List<MultipartFile> imageFiles, String dirName, String imageDirPath);
}
