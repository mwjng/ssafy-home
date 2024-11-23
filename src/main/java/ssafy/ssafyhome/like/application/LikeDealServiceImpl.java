package ssafy.ssafyhome.like.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.repository.DealRepository;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.like.application.response.LikeDealQueryResponse;
import ssafy.ssafyhome.like.application.response.LikeDealResponse;
import ssafy.ssafyhome.like.application.response.LikeDealsResponse;
import ssafy.ssafyhome.like.domain.LikeDeal;
import ssafy.ssafyhome.like.domain.repository.LikeDealRepository;
import ssafy.ssafyhome.like.exception.LikeDealException;
import ssafy.ssafyhome.like.infrastructure.LikeDealQueryRepository;
import ssafy.ssafyhome.like.presentation.request.LikeDealCreateRequest;
import ssafy.ssafyhome.member.domain.Member;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.defaultSort;
import static ssafy.ssafyhome.image.application.ImageDirectory.DEAL;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeDealServiceImpl {

    private final LikeDealRepository likeDealRepository;
    private final LikeDealQueryRepository likeDealQueryRepository;
    private final DealRepository dealRepository;
    private final ImageService imageService;

    public LikeDealsResponse getLikeDeals(
            final Long memberId,
            final String baseUrl,
            final int size,
            final Long cursorId) {

        final PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        final List<LikeDealResponse> likeDealResponses = likeDealQueryRepository
                .findLikeDealsByMemberId(memberId, pageRequest, cursorId).stream()
                .map(likeDeal -> getLikeDealResponse(baseUrl, likeDeal))
                .toList();
        return new LikeDealsResponse(likeDealResponses);
    }

    private LikeDealResponse getLikeDealResponse(final String baseUrl, final LikeDealQueryResponse likeDeal) {
        final String dirName = likeDeal.deal().getDirName();
        final List<String> imageFileNames = getFileNames(dirName);
        final List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
        return LikeDealResponse.from(likeDeal, imageUrl);
    }

    private List<String> getFileNames(final String dirName) {
        return imageService.getImageFileNames(dirName, DEAL.getDirectory());
    }

    private List<String> getImageUrl(final String baseUrl, final List<String> imageFileNames, final String dirName) {
        return imageService.getImageUrlList(baseUrl, DEAL.getDirectory(), imageFileNames, dirName);
    }

    public void create(final Long memberId, final LikeDealCreateRequest likeDealCreateRequest) {
        Deal deal = dealRepository.findById(likeDealCreateRequest.dealId())
                .orElseThrow(() -> new LikeDealException(NOT_FOUND_DEAL_ID));

        if(likeDealRepository.existsByMemberIdAndDealId(memberId, deal.getId())){
            throw new LikeDealException(DUPLICATED_LIKE_DEAL);
        }

        likeDealRepository.save(LikeDeal.of(Member.withId(memberId), deal));
    }

    public void delete(final Long memberId, final Long likeDealId) {
        LikeDeal likeDeal = likeDealRepository.findById(likeDealId)
                .orElseThrow(() -> new LikeDealException(NOT_FOUND_LIKE_DEAL));

        if (!likeDeal.getMember().getId().equals(memberId)) {
            throw new LikeDealException(UNAUTHORIZED_LIKE_DEAL_ACCESS);
        }

        likeDealRepository.deleteById(likeDealId);
    }
}
