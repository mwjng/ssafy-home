package ssafy.ssafyhome.deal.application.request;

import lombok.Builder;
import lombok.Getter;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;
import ssafy.ssafyhome.deal.presentation.request.DealSearchCondition;

import static ssafy.ssafyhome.deal.application.request.DealSortCondition.*;

@Getter
public class DealCondition {

    private ExclusiveAreaRange exclusiveAreaRange;
    private DepositRange depositRange;
    private PriceRange priceRange;
    private DealStatus dealStatus;
    private DealType dealType;
    private DealSortCondition dealSortCondition;

    @Builder
    public DealCondition(
            final ExclusiveAreaRange exclusiveAreaRange,
            final DepositRange depositRange,
            final PriceRange priceRange,
            final DealStatus dealStatus,
            final DealType dealType,
            final DealSortCondition dealSortCondition) {

        this.exclusiveAreaRange = exclusiveAreaRange;
        this.depositRange = depositRange;
        this.priceRange = priceRange;
        this.dealStatus = dealStatus;
        this.dealType = dealType;
        this.dealSortCondition = dealSortCondition;
    }

    public static DealCondition from(final DealSearchCondition searchCondition) {
        return DealCondition.builder()
                .exclusiveAreaRange(getExclusiveAreaRange(searchCondition))
                .depositRange(getDepositRange(searchCondition))
                .priceRange(getPriceRange(searchCondition))
                .dealStatus(getStatus(searchCondition))
                .dealType(getType(searchCondition))
                .dealSortCondition(getDealSortCondition(searchCondition))
                .build();
    }

    private static DealSortCondition getDealSortCondition(final DealSearchCondition searchCondition) {
        return searchCondition != null ? searchCondition.getDealSortCondition() != null ? searchCondition.getDealSortCondition() : DEAL_DATE_NEWEST_FIRST : null;
    }

    private static DealType getType(final DealSearchCondition searchCondition) {
        return searchCondition != null ? searchCondition.getType() != null ? searchCondition.getType() : null : null;
    }

    private static DealStatus getStatus(final DealSearchCondition searchCondition) {
        return searchCondition != null ? searchCondition.getStatus() != null ? searchCondition.getStatus() : null : null;
    }

    private static ExclusiveAreaRange getExclusiveAreaRange(final DealSearchCondition searchCondition) {
        return searchCondition.getMaxExclusiveArea() != null && searchCondition.getMinExclusiveArea() != null
                ? new ExclusiveAreaRange(searchCondition.getMaxExclusiveArea(), searchCondition.getMinExclusiveArea())
                : null;
    }

    private static DepositRange getDepositRange(final DealSearchCondition searchCondition) {
        return searchCondition.getMaxDeposit() != null && searchCondition.getMinDeposit() != null
                ? new DepositRange(searchCondition.getMaxDeposit(), searchCondition.getMinDeposit())
                : null;
    }

    private static PriceRange getPriceRange(final DealSearchCondition searchCondition) {
        return searchCondition.getMaxPrice() != null && searchCondition.getMinPrice() != null
                ? new PriceRange(searchCondition.getMaxPrice(), searchCondition.getMinPrice())
                : null;
    }
}
