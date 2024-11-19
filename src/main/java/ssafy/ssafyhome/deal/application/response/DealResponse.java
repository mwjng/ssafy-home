package ssafy.ssafyhome.deal.application.response;

import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;
import java.util.List;

public record DealResponse(
        Long dealId,
        BigDecimal exclusiveArea,
        int floor,
        int views,
        DealStatus status,
        DealType type,
        Long houseId,
        Long memberId,
        String registrant,
        String dirName,
        List<String> imageNames) {
}