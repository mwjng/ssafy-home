package ssafy.ssafyhome.deal.presentation.request;

import jakarta.validation.constraints.NotNull;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

import java.math.BigDecimal;

public record DealUpdateRequest(

        @NotNull(message = "houseId 값을 입력해주세요.")
        Long houseId,

        @NotNull(message = "면적 값을 입력해주세요.")
        BigDecimal exclusiveArea,

        @NotNull(message = "층수를 입력해주세요.")
        int floor,

        Integer deposit,

        @NotNull(message = "가격을 입력해주세요.")
        Integer price,

        @NotNull(message = "매물 상태를 입력해주세요.")
        DealStatus status,

        @NotNull(message = "매물 종류를 입력해주세요.")
        DealType type) {
}


