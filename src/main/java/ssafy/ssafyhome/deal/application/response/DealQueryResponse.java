package ssafy.ssafyhome.deal.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.deal.domain.Deal;

public record DealQueryResponse(
        Deal deal,
        Boolean likeStatus) {

    @QueryProjection
    public DealQueryResponse {
    }
}
