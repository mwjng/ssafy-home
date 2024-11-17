package ssafy.ssafyhome.deal.application.response;

import ssafy.ssafyhome.deal.domain.DealStatus;

import java.math.BigDecimal;

public record DealResponse(
        Long dealId,
        BigDecimal exclusiveArea,
        int floor,
        int views,
        DealStatus status,
        Long houseId,
        Long memberId,
        String registrant) {

}