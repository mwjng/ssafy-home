package ssafy.ssafyhome.house.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.house.application.request.HouseSearchCondition;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.house.domain.HouseType;

import java.util.List;

import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.region.domain.QRegion.region;

@RequiredArgsConstructor
@Repository
public class HouseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<House> findHousesByRegionAndType(HouseSearchCondition condition) {
        return queryFactory
            .selectFrom(house)
            .leftJoin(house.region, region).fetchJoin()
            .where(
                sidoEq(condition.sido()),
                gugunEq(condition.gugun()),
                dongEq(condition.dong()),
                typeEq(condition.type())
            ).fetch();
    }

    private BooleanExpression sidoEq(final String sido) {
        return sido != null ? region.sido.eq(sido) : null;
    }

    private BooleanExpression gugunEq(final String gugun) {
        return gugun != null ? region.gugun.eq(gugun) : null;
    }

    private BooleanExpression dongEq(final String dong) {
        return dong != null ? region.dong.eq(dong) : null;
    }

    private BooleanExpression typeEq(final HouseType type) {
        return type != null ? house.type.eq(type) : null;
    }
}