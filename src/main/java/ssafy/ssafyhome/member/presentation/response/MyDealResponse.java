package ssafy.ssafyhome.member.presentation.response;

import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;
import java.util.List;

public record MyDealResponse(
    Long dealId,
    BigDecimal exclusiveArea,
    int floor,
    int views,
    Integer price,
    Integer deposit,
    DealStatus status,
    DealType type,
    MyDealHouseResponse house,
    List<String> imageUrl
) {
    public static MyDealResponse of(
        Deal deal,
        List<String> dealImageUrl,
        List<String> houseImageUrl
    ) {
        return new MyDealResponse(
            deal.getId(),
            deal.getExclusiveArea(),
            deal.getFloor(),
            deal.getViews(),
            deal.getPrice(),
            deal.getDeposit(),
            deal.getStatus(),
            deal.getType(),
            MyDealHouseResponse.of(deal.getHouse(), houseImageUrl),
            dealImageUrl
        );
    }
}
