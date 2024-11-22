package ssafy.ssafyhome.like.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.deal.domain.Deal;

public record LikeDealQueryResponse(
        Deal deal,
        Long likeDealId) {

    @QueryProjection
    public LikeDealQueryResponse {
    }
}
