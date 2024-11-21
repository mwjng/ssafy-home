package ssafy.ssafyhome.deal.application.response;

import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;
import ssafy.ssafyhome.member.presentation.response.MyDealHouseResponse;
import ssafy.ssafyhome.member.presentation.response.MyDealResponse;

import java.math.BigDecimal;
import java.util.List;

public record DealResponse(
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
    public static DealResponse of(
        Deal deal,
        List<String> dealImageUrl,
        List<String> houseImageUrl
    ) {
        return new DealResponse(
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
