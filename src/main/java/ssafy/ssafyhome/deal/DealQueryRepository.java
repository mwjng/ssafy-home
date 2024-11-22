package ssafy.ssafyhome.deal;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.deal.application.request.DealCondition;
import ssafy.ssafyhome.deal.domain.Deal;
import ssafy.ssafyhome.deal.domain.DealStatus;
import ssafy.ssafyhome.deal.domain.DealType;
import ssafy.ssafyhome.house.domain.HouseType;

import java.math.BigDecimal;
import java.util.List;

import static ssafy.ssafyhome.deal.domain.QDeal.deal;
import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.region.domain.QRegion.region;

@RequiredArgsConstructor
@Repository
public class DealQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Deal> findDeals(DealCondition condition, Pageable pageable) {
        return queryFactory
            .selectFrom(deal)
            .leftJoin(deal.house, house).fetchJoin()
            .leftJoin(house.region, region).fetchJoin()
            .where(
                maxExclusiveArea(condition.getMaxExclusiveArea()),
                minExclusiveArea(condition.getMinExclusiveArea()),
                maxDeposit(condition.getMaxDeposit()),
                minDeposit(condition.getMinDeposit()),
                maxPrice(condition.getMaxPrice()),
                minPrice(condition.getMinPrice()),
                dealStatusEq(condition.getDealStatus()),
                dealTypeEq(condition.getDealType()),
                memberIdEq(condition.getMemberId()),
                houseIdEq(condition.getHouseId())
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression maxExclusiveArea(BigDecimal maxArea) {
        return maxArea != null ? deal.exclusiveArea.loe(maxArea) : null;
    }

    private BooleanExpression minExclusiveArea(BigDecimal minArea) {
        return minArea != null ? deal.exclusiveArea.goe(minArea) : null;
    }

    private BooleanExpression maxDeposit(Integer maxDeposit) {
        return maxDeposit != null ? deal.deposit.loe(maxDeposit) : null;
    }

    private BooleanExpression minDeposit(Integer minDeposit) {
        return minDeposit != null ? deal.deposit.goe(minDeposit) : null;
    }

    private BooleanExpression maxPrice(Integer maxPrice) {
        return maxPrice != null ? deal.price.loe(maxPrice) : null;
    }

    private BooleanExpression minPrice(Integer minPrice) {
        return minPrice != null ? deal.price.goe(minPrice) : null;
    }

    private BooleanExpression dealStatusEq(DealStatus status) {
        return status != null ? deal.status.eq(status) : null;
    }

    private BooleanExpression dealTypeEq(DealType type) {
        return type != null ? deal.type.eq(type) : null;
    }

    private BooleanExpression houseTypeEq(HouseType type) {
        return type != null ? house.type.eq(type) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? deal.member.id.eq(memberId) : null;
    }

    private BooleanExpression houseIdEq(Long houseId) {
        return houseId != null ? deal.house.id.eq(houseId) : null;
    }
}
