package ssafy.ssafyhome.deal.presentation.request;

import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;

public record DealCreateRequest(
        BigDecimal exclusiveArea,
        int floor,
        int views,
        Integer deposit,
        Integer price,
        DealStatus status,
        DealType type
) {
}


