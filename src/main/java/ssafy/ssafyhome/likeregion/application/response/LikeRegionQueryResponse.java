package ssafy.ssafyhome.likeregion.application.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class LikeRegionQueryResponse {
    private Long likeRegionId;
    private String sido;
    private String gugun;
    private String dong;

    @QueryProjection
    public LikeRegionQueryResponse(final Long likeRegionId, final String sido, final String gugun, final String dong) {
        this.likeRegionId = likeRegionId;
        this.sido = sido;
        this.gugun = gugun;
        this.dong = dong;
    }
}
