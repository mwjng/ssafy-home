package ssafy.ssafyhome.deal.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.deal.DealQueryRepository;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.repository.DealRepository;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.like.application.response.LikeDealsResponse;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.presentation.response.MyDealResponse;
import ssafy.ssafyhome.member.presentation.response.MyDealsResponse;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
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

    public MyDealsResponse getDealsByMemberId(final Long memberId, final String baseUrl) {
        final List<MyDealResponse> myDealResponses = dealQueryRepository.findByMemberId(memberId).stream()
                .map(deal -> getMyDealsResponse(baseUrl, deal))
                .toList();

        return new MyDealsResponse(myDealResponses);
    }

    public DealsResponse getDealsByHouseId(final Long houseId, final String baseUrl) {
        if (!houseRepository.existsById(houseId)) {
            throw new BadRequestException(NOT_FOUND_HOUSE_ID);
        }
        return null;
    }

    public LikeDealsResponse getLikeDealsByMemberId(final Long memberId, final String baseUrl){
        dealQueryRepository.findLikeDealsByMemberId(memberId);
        return  null;
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
    public void deleteDeal(final Long dealId) {
        final Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_DEAL_ID));
        deleteImages(deal.getDirName(), DEAL.getDirectory());
        dealRepository.deleteById(dealId);
    }

    private MyDealResponse getMyDealsResponse(final String baseUrl, final Deal deal) {
        String dirName = deal.getDirName();
        List<String> imageFileNames = getFileNames(dirName);
        List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
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
