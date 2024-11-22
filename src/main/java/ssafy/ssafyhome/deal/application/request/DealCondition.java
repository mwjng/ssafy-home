package ssafy.ssafyhome.deal.application.request;

import lombok.Builder;
import lombok.Getter;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;
import ssafy.ssafyhome.house.domain.HouseType;

import java.math.BigDecimal;

@Getter
public class DealCondition {

    private BigDecimal maxExclusiveArea;
    private BigDecimal minExclusiveArea;
    private Integer maxDeposit;
    private Integer minDeposit;
    private Integer maxPrice;
    private Integer minPrice;
    private DealStatus dealStatus;
    private DealType dealType;
    private HouseType houseType;
    private DealSortCondition dealSortCondition;
    private Long memberId;
    private Long houseId;

    @Builder
    public DealCondition(
        BigDecimal maxExclusiveArea,
        BigDecimal minExclusiveArea,
        Integer maxDeposit,
        Integer minDeposit,
        Integer maxPrice,
        Integer minPrice,
        DealStatus dealStatus,
        HouseType houseType,
        DealType dealType,
        DealSortCondition dealSortCondition,
        Long memberId,
        Long houseId
    ) {
        this.maxExclusiveArea = maxExclusiveArea;
        this.minExclusiveArea = minExclusiveArea;
        this.maxDeposit = maxDeposit;
        this.minDeposit = minDeposit;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.dealStatus = dealStatus;
        this.houseType = houseType;
        this.dealType = dealType;
        this.dealSortCondition = dealSortCondition;
        this.memberId = memberId;
        this.houseId = houseId;
    }
}
