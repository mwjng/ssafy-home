package ssafy.ssafyhome.likeregion.application.response;

public record LikeRegionResponse(
        Long likeRegionId,
        String sido,
        String gugun,
        String dong) {

    public static LikeRegionResponse from(LikeRegionQueryResponse queryResponse){
        return new LikeRegionResponse(
                queryResponse.likeRegionId(),
                queryResponse.sido(),
                queryResponse.gugun(),
                queryResponse.dong()
        );
    }
}
