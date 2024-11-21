package ssafy.ssafyhome.like.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.house.application.response.HouseResponse;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.like.application.response.LikeHouseQueryResponse;
import ssafy.ssafyhome.like.application.response.LikeHousesResponse;
import ssafy.ssafyhome.like.domain.LikeHouse;
import ssafy.ssafyhome.like.domain.repository.LikeHouseRepository;
import ssafy.ssafyhome.like.infrastructure.LikeHouseQueryRepository;
import ssafy.ssafyhome.like.exception.LikeHouseException;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.image.application.ImageDirectory.HOUSE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeHouseServiceImpl implements LikeHouseService{

    private final LikeHouseRepository likeHouseRepository;
    private final HouseRepository houseRepository;
    private final LikeHouseQueryRepository likeHouseQueryRepository;
    private final ImageService imageService;

    public LikeHousesResponse searchAll(final Long memberId, final int size, final Long cursorId, final String baseUrl) {
        PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        List<HouseResponse> likeHouses = likeHouseQueryRepository.searchAll(memberId, pageRequest, cursorId)
                .stream()
                .map(likeHouse -> getHouseResponse(baseUrl, likeHouse))
                .toList();;
        return new LikeHousesResponse(likeHouses);
    }

    public void create(final Long memberId, final Long houseId) {
        if(!houseRepository.existsById(houseId)){
            throw new BadRequestException(NOT_FOUND_HOUSE_ID);
        }
    }

    public void delete(final Long memberId, final Long likeHouseId) {
        LikeHouse likeHouse = likeHouseRepository.findById(likeHouseId).orElseThrow(() -> new LikeHouseException(NOT_FOUND_LIKE_HOUSE_ID));
        if(!likeHouse.getMember().getId().equals(memberId)){
            throw new LikeHouseException(UNAUTHORIZED_LIKE_HOUSE_ACCESS);
        }
        likeHouseRepository.delete(likeHouse);
    }

    private HouseResponse getHouseResponse(final String baseUrl, final LikeHouseQueryResponse likeHouse) {
        String dirName = likeHouse.dirName();
        List<String> imageFileNames = getFileNames(dirName);
        List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
        return HouseResponse.from(likeHouse, imageUrl);
    }

    private List<String> getFileNames(final String dirName) {
        return imageService.getImageFileNames(dirName, HOUSE.getDirectory());
    }

    private List<String> getImageUrl(final String baseUrl, final List<String> imageFileNames, final String dirName) {
        return imageService.getImageUrlList(baseUrl, HOUSE.getDirectory(), imageFileNames, dirName);
    }
}
