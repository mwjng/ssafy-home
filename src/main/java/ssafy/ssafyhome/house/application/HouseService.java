package ssafy.ssafyhome.house.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.house.application.response.HouseResponse;
import ssafy.ssafyhome.house.application.response.HousesResponse;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.house.infrastructure.HouseQueryRepository;
import ssafy.ssafyhome.house.presentation.request.HouseRequest;
import ssafy.ssafyhome.house.presentation.request.HouseSearchRequest;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.region.domain.Region;
import ssafy.ssafyhome.region.domain.repository.RegionRepository;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_HOUSE_ID;
import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_REGION;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HouseService {

    private static final String HOUSE_IMG_DIR = "house/";

    private final HouseRepository houseRepository;
    private final HouseQueryRepository houseQueryRepository;
    private final RegionRepository regionRepository;
    private final ImageService imageService;
    private final ApplicationEventPublisher eventPublisher;

    public HousesResponse searchAll(final HouseSearchRequest request, final String baseUrl) {
        final List<HouseResponse> houseResponses = houseQueryRepository
            .findHousesByRegionAndType(request.toHouseSearchCondition()).stream()
            .map(house -> getHouseResponse(baseUrl, house))
            .toList();

        return new HousesResponse(houseResponses);
    }

    public HouseResponse search(final Long houseId, final String baseUrl) {
        final House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_HOUSE_ID));
        return getHouseResponse(baseUrl, house);
    }

    private HouseResponse getHouseResponse(final String baseUrl, final House house) {
        final List<String> imageUrlList = getHouseImageUrlList(baseUrl, house);
        return HouseResponse.of(house, imageUrlList);
    }

    private List<String> getHouseImageUrlList(final String baseUrl, final House house) {
        final List<String> imageFileNames = imageService.getImageFileNames(house.getDirName(), HOUSE_IMG_DIR);
        return imageService.getImageUrlList(baseUrl, HOUSE_IMG_DIR, imageFileNames, house.getDirName());
    }

    @Transactional
    public void createHouse(final HouseRequest request,
                            final List<MultipartFile> images) {
        final String imagePath = imageService.save(images, HOUSE_IMG_DIR);
        final Region region = regionRepository.findBySidoAndGugunAndDong(
                request.sido(), request.gugun(), request.dong())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_REGION));
        houseRepository.save(request.toMember(imagePath, region));
    }

    @Transactional
    public void updateHouse(final Long houseId,
                            final HouseRequest request,
                            final List<MultipartFile> images) {
        houseRepository.findById(houseId).orElseThrow(() -> new BadRequestException(NOT_FOUND_HOUSE_ID));
    }

    @Transactional
    public void deleteHouse(final Long houseId) {
        final House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_HOUSE_ID));
        final List<String> imageFilePaths = imageService.getImageFilePaths(house.getDirName(), HOUSE_IMG_DIR);
        final String imageFileDirPath = imageService.getImageFileDirPath(house.getDirName(), HOUSE_IMG_DIR);

        eventPublisher.publishEvent(new ImageEvent(imageFileDirPath, imageFilePaths));
        houseRepository.deleteById(houseId);
    }
}
