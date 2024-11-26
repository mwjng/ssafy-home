package ssafy.ssafyhome.member.presentation.response;

import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record MyDealResponse(
        Long dealId,
        BigDecimal exclusiveArea,
        int floor,
        Integer deposit,
        Integer price,
        DealStatus status,
        DealType type,
        LocalDateTime dealDate,
        MyDealHouseResponse house,
        Long memberId,
        String agentNickName,
        List<String> imageUrl,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
) {
    public static MyDealResponse from(Deal deal, List<String> dealImageUrl) {
        return new MyDealResponse(
                deal.getId(),
                deal.getExclusiveArea(),
                deal.getFloor(),
                deal.getDeposit(),
                deal.getPrice(),
                deal.getStatus(),
                deal.getType(),
                deal.getDealDate(),
                MyDealHouseResponse.of(deal.getHouse()),
                deal.getMember().getId(),
                deal.getMember().getNickname(),
                dealImageUrl,
                deal.getCreatedAt(),
                deal.getModifiedAt()
        );
    }
}
