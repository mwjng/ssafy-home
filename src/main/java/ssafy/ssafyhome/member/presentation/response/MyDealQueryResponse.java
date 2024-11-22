package ssafy.ssafyhome.member.presentation.response;

import com.querydsl.core.annotations.QueryProjection;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;
import ssafy.ssafyhome.house.application.response.HouseQueryResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MyDealQueryResponse(
        Long dealId,
        BigDecimal exclusiveArea,
        int floor,
        Integer deposit,
        Integer price,
        DealStatus status,
        DealType type,
        LocalDateTime dealDate,
        HouseQueryResponse house,
        Long memberId,
        String agentNickName,
        String dirName,
        LocalDateTime createAt,
        LocalDateTime modifiedAt) {

    @QueryProjection
    public MyDealQueryResponse {
    }
}
