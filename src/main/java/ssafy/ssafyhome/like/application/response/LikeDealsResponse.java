package ssafy.ssafyhome.like.application.response;

import ssafy.ssafyhome.deal.application.response.DealResponse;

import java.util.List;

public record LikeDealsResponse(List<DealResponse> likeDeals) {
}
