package ssafy.ssafyhome.like.application.response;

import ssafy.ssafyhome.house.application.response.HouseResponse;

import java.util.List;

public record LikeHousesResponse(List<LikeHouseResponse> likeHouses) {
}
