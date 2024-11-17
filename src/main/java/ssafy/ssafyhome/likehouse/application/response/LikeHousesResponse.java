package ssafy.ssafyhome.likehouse.application.response;

import ssafy.ssafyhome.house.application.response.HouseResponse;

import java.util.List;

public record LikeHousesResponse(List<HouseResponse> likeHouses) {
}
