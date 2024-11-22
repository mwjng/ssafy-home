package ssafy.ssafyhome.deal.application.request;

import lombok.Builder;
import lombok.Getter;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;

@Getter
public class DealCondition {

    private ExclusiveAreaRange exclusiveAreaRange;
    private DepositRange depositRange;
    private PriceRange priceRange;
    private DealStatus dealStatus;
    private DealType dealType;
    private DealSortCondition dealSortCondition;
    private Long houseId;

    @Builder
    public DealCondition(
            final ExclusiveAreaRange exclusiveAreaRange,
            final DepositRange depositRange,
            final PriceRange priceRange,
            final DealStatus dealStatus,
            final DealType dealType,
            final DealSortCondition dealSortCondition,
            final Long houseId) {

        this.exclusiveAreaRange = exclusiveAreaRange;
        this.depositRange = depositRange;
        this.priceRange = priceRange;
        this.dealStatus = dealStatus;
        this.dealType = dealType;
        this.dealSortCondition = dealSortCondition;
        this.houseId = houseId;
    }
}
