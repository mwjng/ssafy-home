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
import ssafy.ssafyhome.deal.application.response.DealQueryResponse;
import ssafy.ssafyhome.deal.application.response.DealResponse;
import ssafy.ssafyhome.deal.application.response.LikeCountResponse;
import ssafy.ssafyhome.deal.exception.DealException;
import ssafy.ssafyhome.deal.infrastructure.DealQueryRepository;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.repository.DealRepository;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.like.application.response.LikeDealQueryResponse;
import ssafy.ssafyhome.like.application.response.LikeDealResponse;
import ssafy.ssafyhome.like.application.response.LikeDealsResponse;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.presentation.response.MyDealResponse;
import ssafy.ssafyhome.member.presentation.response.MyDealsResponse;

import java.util.List;
import java.util.Map;

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
                        condition,
                        PageRequest.of(0, size, defaultSort()),
                        cursorId);

        final List<DealResponse> dealResponses = dealQueryResponses.stream()
                .map(dealQueryResponse ->
                        getDealResponse(
                                baseUrl,
                                dealQueryResponse,
                                getLikeCounts(dealQueryResponses)))
                .toList();

        return new DealsResponse(dealResponses);
    }

    public LikeDealsResponse getLikeDealsByMemberId(final Long memberId, final String baseUrl, final int size, final Long cursorId) {
        final PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        final List<LikeDealResponse> likeDealResponses = dealQueryRepository.findLikeDealsByMemberId(memberId, pageRequest, cursorId).stream()
                .map(likeDeal -> getLikeDealResponse(baseUrl, likeDeal))
                .toList();
        return new LikeDealsResponse(likeDealResponses);
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
            final AccessContext accessContext,
            final Long dealId,
            final DealUpdateRequest dealUpdateRequest,
            final List<MultipartFile> images) {

        final Deal deal = dealRepository.findMemberAndDealById(dealId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DEAL_ID));

        checkAuthority(accessContext, deal);

        String imagePath = imageService.save(images, DEAL.getDirectory());
        deleteExistedImage(deal);
        deal.changeImageUrl(imagePath);
        deal.changeContent(dealUpdateRequest);
    }

    private void deleteExistedImage(final Deal deal) {
        List<String> imageFilePaths = imageService.getImageFilePaths(deal.getDirName(), DEAL.getDirectory());
        String imageFileDirPath = imageService.getImageFileDirPath(deal.getDirName(), DEAL.getDirectory());
        eventPublisher.publishEvent(new ImageEvent(imageFileDirPath, imageFilePaths));
    }

    @Transactional
    public void deleteDeal(final AccessContext accessContext ,final Long dealId) {
        final Deal deal = dealRepository.findMemberAndDealById(dealId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DEAL_ID));

        checkAuthority(accessContext, deal);
        deleteImages(deal.getDirName(), DEAL.getDirectory());
        dealRepository.deleteById(dealId);
    }

    private void checkAuthority(final AccessContext accessContext, final Deal deal) {
        final Long memberId = accessContext.getMemberId();
        final Authority authority = accessContext.getAuthority();

        if(authority.equals(AGENT) && !deal.getMember().getId().equals(memberId)){
            throw new DealException(UNAUTHORIZED_DEAL_ACCESS);
        }
    }

    private Map<Long, Long> getLikeCounts(final List<DealQueryResponse> dealQueryResponses) {
        return dealQueryRepository.getCountByDealId(getDealIds(dealQueryResponses)).stream()
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
            final Map<Long, Long> likeCountMap) {

        Deal deal = dealQueryResponse.deal();
        Long likeCount = likeCountMap.getOrDefault(deal.getId(), 0L);

        List<String> imageFileNames = getFileNames(deal.getDirName());
        List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, deal.getDirName());
        return DealResponse.of(deal, likeCount, dealQueryResponse.likeStatus(), imageUrl);
    }

    private LikeDealResponse getLikeDealResponse(final String baseUrl, final LikeDealQueryResponse likeDeal) {
        final String dirName = likeDeal.deal().getDirName();
        final List<String> imageFileNames = getFileNames(dirName);
        final List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
        return LikeDealResponse.from(likeDeal, imageUrl);
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
