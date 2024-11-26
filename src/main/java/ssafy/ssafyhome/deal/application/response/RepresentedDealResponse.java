package ssafy.ssafyhome.deal.application.response;

import ssafy.ssafyhome.deal.domain.Deal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RepresentedDealResponse(
        Integer price,
        BigDecimal exclusiveArea,
        LocalDateTime dealDate,
        Integer floor) {

    public static RepresentedDealResponse from(Deal deal) {
        return new RepresentedDealResponse(
                deal.getPrice(),
                deal.getExclusiveArea(),
                deal.getDealDate(),
                deal.getFloor()
        );
    }
}
