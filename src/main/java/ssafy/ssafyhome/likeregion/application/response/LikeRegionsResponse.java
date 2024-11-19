package ssafy.ssafyhome.likeregion.application.response;

import ssafy.ssafyhome.region.application.response.RegionResponse;

import java.util.List;

public record LikeRegionsResponse(List<RegionResponse> likeRegions) {
}
