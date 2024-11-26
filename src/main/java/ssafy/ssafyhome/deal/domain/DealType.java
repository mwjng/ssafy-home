package ssafy.ssafyhome.deal.domain;

import ssafy.ssafyhome.common.exception.BadRequestException;

import java.util.Arrays;

import static org.springframework.util.StringUtils.hasText;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_DEAL_TYPE;

public enum DealType {
    SALE,
    RENT,
    MONTHLY_RENT;

    public static DealType getDealType(String dealType) {
        if (!hasText(dealType)) {
            return null;
        }
        return Arrays.stream(DealType.values())
                .filter(type -> type.name().equalsIgnoreCase(dealType))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(INVALID_DEAL_TYPE));
    }

}
