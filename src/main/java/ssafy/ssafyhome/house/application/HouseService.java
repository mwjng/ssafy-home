package ssafy.ssafyhome.house.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.house.application.request.HouseNameSearchCondition;
import ssafy.ssafyhome.house.application.response.*;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.house.infrastructure.HouseQueryRepository;
import ssafy.ssafyhome.house.presentation.request.HouseRequest;
import ssafy.ssafyhome.house.presentation.request.HouseSearchRequest;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.region.domain.Region;
import ssafy.ssafyhome.region.domain.repository.RegionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_HOUSE_ID;
import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_REGION;
import static ssafy.ssafyhome.image.application.ImageDirectory.HOUSE;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final HouseQueryRepository houseQueryRepository;
    private final RegionRepository regionRepository;
    private final ImageService imageService;
    private final ApplicationEventPublisher eventPublisher;

    public HouseNamesResponse searchByHouseName(final HouseNameSearchCondition condition) {
        List<HouseNameResponse> houseNames = houseQueryRepository.searchByHouseName(condition).stream()
                .map(HouseNameResponse::from)
                .toList();
        return new HouseNamesResponse(houseNames);
    }

    public HousesResponse searchAll(
        final Long memberId,
        final HouseSearchRequest request,
        final Pageable pageable,
        final String baseUrl
    ) {
        List<HouseAllQueryResponse> houseQueryResponses = houseQueryRepository
                .findAllWithLikeStatus(memberId, request.toHouseSearchCondition(), pageable.previousOrFirst());

        List<Long> houseIds = houseQueryResponses.stream()
                .map(HouseAllQueryResponse::house)
                .map(House::getId)
                .toList();

        List<Deal> latestDeals = houseQueryRepository.findLatestDealsByHouseIds(houseIds);

        Map<Long, Deal> houseDealsMap = new HashMap<>();
        latestDeals.forEach(deal -> houseDealsMap.put(deal.getHouse().getId(), deal));

        List<HouseAllResponse> houseResponses = houseQueryResponses
                .stream()
                .map(houseQueryResponse ->
                        getHouseAllResponse(baseUrl, houseQueryResponse, houseDealsMap))
                .toList();

        return new HousesResponse(houseResponses);
    }

    private HouseAllResponse getHouseAllResponse(
            final String baseUrl,
            final HouseAllQueryResponse houseQueryResponse,
            final Map<Long, Deal> houseDealsMap) {

        House house = houseQueryResponse.house();

        return HouseAllResponse.of(
                house,
                houseQueryResponse.region(),
                houseDealsMap.get(house.getId()),
                getHouseImageUrlList(baseUrl, house),
                houseQueryResponse.likeStatus()
        );
    }

    public HouseDetailsResponse search(final Long memberId, final Long houseId, final String baseUrl) {
        if(!houseRepository.existsById(houseId)) {
            throw new BadRequestException(NOT_FOUND_HOUSE_ID);
        }
        return getHouseDetailsResponse(baseUrl, houseQueryRepository.findOne(memberId, houseId));
    }

    private HouseDetailsResponse getHouseDetailsResponse(
            final String baseUrl,
            final HouseDetailsQueryResponse houseDetailsQueryResponse) {
        House house = houseDetailsQueryResponse.house();
        return HouseDetailsResponse.of(
                house,
                house.getRegion(),
                getHouseImageUrlList(baseUrl, house),
                houseDetailsQueryResponse.likeStatus());
    }

    private List<String> getHouseImageUrlList(final String baseUrl, final House house) {
        final List<String> imageFileNames = imageService.getImageFileNames(house.getDirName(), HOUSE.getDirectory());
        return imageService.getImageUrlList(baseUrl, HOUSE.getDirectory(), imageFileNames, house.getDirName());
    }

    @Transactional
    public void createHouse(final HouseRequest request,
                            final List<MultipartFile> images) {
        final String imagePath = imageService.save(images, HOUSE.getDirectory());
        houseRepository.save(request.toMember(imagePath, getRegion(request)));
    }

    @Transactional
    public void updateHouse(final Long houseId,
                            final HouseRequest request,
                            final List<MultipartFile> images) {
        final House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_HOUSE_ID));
        final String imagePath = imageService.save(images, HOUSE.getDirectory());
        deleteHouseImages(house);
        house.updateHouseInfo(request, getRegion(request), imagePath);
    }

    private Region getRegion(final HouseRequest request) {
        return regionRepository
            .findBySidoAndGugunAndDong(request.sido(), request.gugun(), request.dong())
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_REGION));
    }

    @Transactional
    public void deleteHouse(final Long houseId) {
        final House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_HOUSE_ID));
        deleteHouseImages(house);
        houseRepository.deleteById(houseId);
    }

    private void deleteHouseImages(final House house) {
        final List<String> imageFilePaths = imageService.getImageFilePaths(house.getDirName(), HOUSE.getDirectory());
        final String imageFileDirPath = imageService.getImageFileDirPath(house.getDirName(), HOUSE.getDirectory());
        eventPublisher.publishEvent(new ImageEvent(imageFileDirPath, imageFilePaths));
    }
}
