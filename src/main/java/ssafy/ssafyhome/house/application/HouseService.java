package ssafy.ssafyhome.house.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.house.application.response.HouseResponse;
import ssafy.ssafyhome.house.application.response.HousesResponse;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HouseService {

    private static final String HOUSE_IMG_DIR = "house/";

    private final HouseRepository houseRepository;
    private final ImageService imageService;

    public HousesResponse searchAll(final String baseUrl) {
        final List<HouseResponse> houseResponses = houseRepository.fetchAll().stream()
            .map(house -> getHouseResponse(baseUrl, house))
            .toList();

        return new HousesResponse(houseResponses);
    }

    private HouseResponse getHouseResponse(final String baseUrl, final House house) {
        final List<String> imageUrlList = getHouseImageUrlList(baseUrl, house);
        return HouseResponse.of(house, imageUrlList);
    }

    private List<String> getHouseImageUrlList(final String baseUrl, final House house) {
        final List<String> imageFileNames = imageService.getImageFileNames(house.getDirName(), HOUSE_IMG_DIR);
        return imageService.getImageUrlList(baseUrl, HOUSE_IMG_DIR, imageFileNames, house.getDirName());
    }
}
