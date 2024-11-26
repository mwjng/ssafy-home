package ssafy.ssafyhome.deal.domain;

import ssafy.ssafyhome.common.exception.BadRequestException;

import java.util.Arrays;

import static org.springframework.util.StringUtils.hasText;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_DEAL_STATUS;

public enum DealStatus {

    COMPLETED,
    PENDING;

    public static DealStatus getDealStatus(String dealStatus) {
        if (!hasText(dealStatus)) {
            return null;
        }
        return Arrays.stream(DealStatus.values())
            .filter(type -> type.name().equalsIgnoreCase(dealStatus))
            .findFirst()
            .orElseThrow(() -> new BadRequestException(INVALID_DEAL_STATUS));
    }
}
