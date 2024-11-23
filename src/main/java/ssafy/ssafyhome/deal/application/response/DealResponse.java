package ssafy.ssafyhome.deal.application.response;

import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DealResponse(
        Long dealId,
        Long memberId,
        String agentNickName,
        Long countLike,
        Boolean likeStatus,
        BigDecimal exclusiveArea,
        int floor,
        Integer deposit,
        Integer price,
        DealStatus status,
        DealType type,
        LocalDateTime dealDate,
        List<String> imageUrl) {

    public static DealResponse of(Deal deal, Long countLike, Boolean likeStatus, List<String> imageUrl) {
        return new DealResponse(
                deal.getId(),
                deal.getMember().getId(),
                deal.getMember().getNickname(),
                countLike,
                likeStatus,
                deal.getExclusiveArea(),
                deal.getFloor(),
                deal.getDeposit(),
                deal.getPrice(),
                deal.getStatus(),
                deal.getType(),
                deal.getDealDate(),
                imageUrl
        );
    }
}
