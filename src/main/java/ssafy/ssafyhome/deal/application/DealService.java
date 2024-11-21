package ssafy.ssafyhome.deal.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.repository.DealRepository;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;
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

    private final DealRepository dealRepository;
    private final ImageService imageService;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    public MyDealsResponse getMyDeals(Long memberId, Pageable pageable, String baseUrl) {
        final List<Deal> deals = dealRepository.findDealsByMemberId(memberId, pageable);
        final List<MyDealResponse> myDealResponses = deals.stream()
            .map(deal -> MyDealResponse.of(
                deal,
                getImageUrlList(baseUrl, deal.getDirName(), DEAL.getDirectory()),
                getImageUrlList(baseUrl, deal.getHouse().getDirName(), HOUSE.getDirectory())
            )).toList();

        return new MyDealsResponse(myDealResponses);
    }

    private List<String> getImageUrlList(final String baseUrl, final String dirName, final String imgDir) {
        final List<String> imageFileNames = imageService.getImageFileNames(dirName, imgDir);
        return imageService.getImageUrlList(baseUrl, imgDir, imageFileNames, dirName);
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

    public void deleteDeal(final Long dealId) {
        dealRepository.findById(dealId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_DEAL_ID));
    }
}
