package ssafy.ssafyhome.deal.presentation.request;

import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.member.domain.Member;

import java.math.BigDecimal;

public record DealCreateRequest(
    BigDecimal exclusiveArea,
    int floor,
    int views,
    Integer deposit,
    Integer price,
    DealStatus status,
    DealType type,
    Long houseId
) {
    public Deal toDeal(House house, Member member, String dirName) {
        return new Deal(
            exclusiveArea,
            floor,
            views,
            deposit,
            price,
            dirName,
            status,
            type,
            house,
            member
        );
    }
}


