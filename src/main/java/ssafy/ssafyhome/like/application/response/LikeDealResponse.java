package ssafy.ssafyhome.like.application.response;

import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record LikeDealResponse(
        Long likeDealId,
        Long dealId,
        Long memberId,
        BigDecimal exclusiveArea,
        int floor,
        Integer deposit,
        Integer price,
        DealStatus status,
        DealType type,
        LocalDateTime dealDate,
        List<String> imageUrl) {

    public static LikeDealResponse from(LikeDealQueryResponse likeDeal, List<String> dealImageUrl) {
        Deal deal = likeDeal.deal();
        return new LikeDealResponse(
                likeDeal.likeDealId(),
                deal.getId(),
                deal.getMember().getId(),
                deal.getExclusiveArea(),
                deal.getFloor(),
                deal.getDeposit(),
                deal.getPrice(),
                deal.getStatus(),
                deal.getType(),
                deal.getDealDate(),
                dealImageUrl
        );
    }
}
