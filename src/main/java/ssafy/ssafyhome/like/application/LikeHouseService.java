package ssafy.ssafyhome.like.application;

import ssafy.ssafyhome.like.application.response.LikeHousesResponse;

public interface LikeHouseService {
    LikeHousesResponse searchAll(Long memberId, int size, Long cursorId, String baseUrl);

    void create(Long memberId, Long houseId);

    void delete(Long memberId, Long houseId);
}
