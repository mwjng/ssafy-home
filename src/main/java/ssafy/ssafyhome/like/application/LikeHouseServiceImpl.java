package ssafy.ssafyhome.like.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.repository.HouseRepository;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.like.application.response.LikeHouseQueryResponse;
import ssafy.ssafyhome.like.application.response.LikeHouseResponse;
import ssafy.ssafyhome.like.application.response.LikeHousesResponse;
import ssafy.ssafyhome.like.domain.LikeHouse;
import ssafy.ssafyhome.like.domain.repository.LikeHouseRepository;
import ssafy.ssafyhome.like.infrastructure.LikeHouseQueryRepository;
import ssafy.ssafyhome.like.exception.LikeHouseException;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.image.application.ImageDirectory.HOUSE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeHouseServiceImpl implements LikeHouseService {

    private final LikeHouseRepository likeHouseRepository;
    private final HouseRepository houseRepository;
    private final MemberRepository memberRepository;
    private final LikeHouseQueryRepository likeHouseQueryRepository;
    private final ImageService imageService;

    public LikeHousesResponse searchAll(final Long memberId, final int size, final Long cursorId, final String baseUrl) {
        final PageRequest pageRequest = PageRequest.of(0, size, defaultSort());
        final List<LikeHouseResponse> likeHouses = likeHouseQueryRepository.searchAll(memberId, pageRequest, cursorId)
                .stream()
                .map(likeHouse -> getHouseResponse(baseUrl, likeHouse))
                .toList();

        return new LikeHousesResponse(likeHouses);
    }

    @Transactional
    public void create(final Long memberId, final Long houseId) {
        if(!houseRepository.existsById(houseId)){
            throw new BadRequestException(NOT_FOUND_HOUSE_ID);
        }

        if(likeHouseRepository.existsByMemberIdAndHouseId(memberId, houseId)){
            throw new LikeHouseException(DUPLICATED_LIKE_HOUSE);
        }

        likeHouseRepository.save(makeLikeHouse(memberId, houseId));
    }

    @Transactional
    public void delete(final Long memberId, final Long houseId) {
        if(!houseRepository.existsById(houseId)){
            throw new BadRequestException(NOT_FOUND_HOUSE_ID);
        }
        LikeHouse likeHouse = likeHouseRepository.findByMemberIdAndHouseId(memberId, houseId);

        if(likeHouse == null){
            throw new LikeHouseException(NOT_FOUND_LIKE_HOUSE_ID);
        }

        if (!likeHouse.getMember().getId().equals(memberId)) {
            throw new LikeHouseException(UNAUTHORIZED_LIKE_HOUSE_ACCESS);
        }

        likeHouseRepository.delete(likeHouse);
    }

    private LikeHouseResponse getHouseResponse(final String baseUrl, final LikeHouseQueryResponse likeHouse) {
        final String dirName = likeHouse.dirName();
        final List<String> imageFileNames = getFileNames(dirName);
        final List<String> imageUrl = getImageUrl(baseUrl, imageFileNames, dirName);
        return LikeHouseResponse.from(likeHouse, imageUrl);
    }

    private List<String> getFileNames(final String dirName) {
        return imageService.getImageFileNames(dirName, HOUSE.getDirectory());
    }

    private List<String> getImageUrl(final String baseUrl, final List<String> imageFileNames, final String dirName) {
        return imageService.getImageUrlList(baseUrl, HOUSE.getDirectory(), imageFileNames, dirName);
    }

    private LikeHouse makeLikeHouse(final Long memberId, final Long houseId) {
        return LikeHouse.create(Member.withId(memberId), House.withId(houseId));
    }
}
