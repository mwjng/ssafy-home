package ssafy.ssafyhome.deal.application.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;

public record AverageQueryResponse(
        BigDecimal exclusiveArea,
        DealType type,
        Double averagePrice) {

    @QueryProjection
    public AverageQueryResponse {
    }
}
