package ssafy.ssafyhome.deal.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.domain.Authority;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.deal.application.request.DealCondition;
import ssafy.ssafyhome.deal.application.response.*;
import ssafy.ssafyhome.deal.exception.DealException;
import ssafy.ssafyhome.deal.infrastructure.DealQueryRepository;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.repository.DealRepository;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.presentation.response.MyDealResponse;
import ssafy.ssafyhome.member.presentation.response.MyDealsResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static ssafy.ssafyhome.auth.domain.Authority.*;
import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.image.application.ImageDirectory.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DealService {

    private final ImageService imageService;
    private final DealRepository dealRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;
    private final DealQueryRepository dealQueryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MyDealsResponse getDealsByMemberId(final Long memberId, final String baseUrl, final int size, final Long cursorId) {
        final PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        final List<MyDealResponse> myDealResponses = dealQueryRepository.findByMemberId(memberId, pageRequest, cursorId).stream()
                .map(deal -> getMyDealsResponse(baseUrl, deal))
                .toList();
        return new MyDealsResponse(myDealResponses);
    }

    public DealsResponse getDealsByHouseId(
            final Long houseId,
            final Long memberId,
            final DealCondition condition,
            final int size,
            final Long cursorId,
            final String baseUrl) {

        if (!houseRepository.existsById(houseId)) {
            throw new BadRequestException(NOT_FOUND_HOUSE_ID);
        }

        final List<DealQueryResponse> dealQueryResponses =
                dealQueryRepository.findDeals(
                        houseId,
                        memberId,
                        condition,
                        PageRequest.of(0, size, defaultSort()),
                        cursorId).getContent();

        Map<Long, Long> likeCounts = getLikeCounts(dealQueryResponses);

        final List<DealResponse> dealResponses = dealQueryResponses.stream()
                .map(dealQueryResponse ->
                        getDealResponse(
                                baseUrl,
                                dealQueryResponse,
                                getLikeCount(dealQueryResponse, likeCounts)))
                .toList();

        return new DealsResponse(dealResponses);
    }

    public DealResponse getDealByDealId(final Long dealId, final Long memberId, final String baseUrl){
        if(!dealRepository.existsById(dealId)){
            throw new DealException(NOT_FOUND_DEAL_ID);
        }

        DealQueryResponse  dealQueryResponse = dealQueryRepository.findDeal(dealId, memberId);
        LikeCountResponse likeCountResponse = dealQueryRepository.getCountByDealId(dealQueryResponse.deal().getId());
        Long count = likeCountResponse != null ? likeCountResponse.count() : 0L;

        return getDealResponse(baseUrl, dealQueryResponse, count);
    }

    private Long getLikeCount(final DealQueryResponse dealQueryResponse, final Map<Long, Long> likeCounts) {
        return likeCounts.get(dealQueryResponse.deal().getId());
    }

    public AverageResponses getAverageByHouseId(final Long houseId) {
        Map<BigDecimal, List<AverageQueryResponse>> groupedByArea = dealQueryRepository.getAverageByHouseId(houseId).stream()
                .collect(groupingBy(AverageQueryResponse::exclusiveArea));

        List<AverageResponse> averageResponses = groupedByArea.entrySet().stream()
                .map(this::getAverageResponse)
                .toList();

        return new AverageResponses(averageResponses);
    }

    private AverageResponse getAverageResponse(final Map.Entry<BigDecimal, List<AverageQueryResponse>> entry) {
        BigDecimal exclusiveArea = entry.getKey();
        List<TypeAndPrice> typeAndPrices = entry.getValue().stream()
                .map(TypeAndPrice::from)
                .toList();
        return AverageResponse.of(exclusiveArea, typeAndPrices);
    }

    @Transactional
    public void createDeal(
            final Long memberId,
            final DealCreateRequest dealCreateRequest,
            final List<MultipartFile> images
    ) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_ID));
        final House house = houseRepository.findById(dealCreateRequest.houseId())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_HOUSE_ID));
        final String imagePath = imageService.save(images, DEAL.getDirectory());

        dealRepository.save(dealCreateRequest.toDeal(house, member, imagePath));
    }

    @Transactional
    public void updateDeal(
            final Long agentId,
            final Long dealId,
            final DealUpdateRequest dealUpdateRequest,
            final List<MultipartFile> images) {

        final Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DEAL_ID));

        if (!deal.getMember().getId().equals(agentId)) {
            throw new DealException(UNAUTHORIZED_DEAL_ACCESS);
        }

        final String imagePath = imageService.save(images, DEAL.getDirectory());
        deleteImages(deal.getDirName(), DEAL.getDirectory());
        deal.changeImageUrl(imagePath);
        deal.changeContent(dealUpdateRequest);
    }

    @Transactional
    public void deleteDeal(final AccessContext accessContext, final Long dealId) {
        final Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DEAL_ID));

        checkAuthority(accessContext, deal);
        deleteImages(deal.getDirName(), DEAL.getDirectory());
        dealRepository.deleteById(dealId);
    }

    private void checkAuthority(final AccessContext accessContext, final Deal deal) {
        final Long memberId = accessContext.getMemberId();
        final Authority authority = accessContext.getAuthority();

        if (authority.equals(AGENT) && !deal.getMember().getId().equals(memberId)) {
            throw new DealException(UNAUTHORIZED_DEAL_ACCESS);
        }
    }

    private Map<Long, Long> getLikeCounts(final List<DealQueryResponse> dealQueryResponses) {
        return dealQueryRepository.getCountsByDealId(getDealIds(dealQueryResponses)).stream()
                .collect(toMap(LikeCountResponse::dealId, LikeCountResponse::count));
    }

    private List<Long> getDealIds(final List<DealQueryResponse> dealQueryResponses) {
        return dealQueryResponses.stream()
                .map(DealQueryResponse::deal)
                .map(Deal::getId)
                .toList();
    }

    private DealResponse getDealResponse(
            final String baseUrl,
            final DealQueryResponse dealQueryResponse,
            final Long likeCount) {

        final Deal deal = dealQueryResponse.deal();
        final List<String> imageFileNames = getFileNames(deal.getDirName());
        final List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, deal.getDirName());
        return DealResponse.of(deal, likeCount, dealQueryResponse.likeStatus(), imageUrl);
    }

    private MyDealResponse getMyDealsResponse(final String baseUrl, final Deal deal) {
        final String dirName = deal.getDirName();
        final List<String> imageFileNames = getFileNames(dirName);
        final List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
        return MyDealResponse.from(deal, imageUrl);
    }

    private List<String> getFileNames(final String dirName) {
        return imageService.getImageFileNames(dirName, DEAL.getDirectory());
    }

    private List<String> getImageUrl(final String baseUrl, final List<String> imageFileNames, final String dirName) {
        return imageService.getImageUrlList(baseUrl, DEAL.getDirectory(), imageFileNames, dirName);
    }

    private void deleteImages(final String dirName, String imgDir) {
        final List<String> imageFilePaths = imageService.getImageFilePaths(dirName, imgDir);
        final String imageFileDirPath = imageService.getImageFileDirPath(dirName, imgDir);
        eventPublisher.publishEvent(new ImageEvent(imageFileDirPath, imageFilePaths));
    }
}
