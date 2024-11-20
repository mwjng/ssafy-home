package ssafy.ssafyhome.region.domain.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ssafy.ssafyhome.region.application.RegionField;
import ssafy.ssafyhome.region.exception.RegionException;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.springframework.util.StringUtils.hasText;
import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_REGION_FIELD;
import static ssafy.ssafyhome.region.domain.QRegion.*;

@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final Map<RegionField, Expression<String>> fieldPathMap = Map.ofEntries(
            entry(RegionField.SIDO, region.sido),
            entry(RegionField.GUGUN, region.gugun),
            entry(RegionField.DONG, region.dong)
    );

    @Override
    public List<String> findBySearchCondition(final RegionSearchCondition regionSearchCondition) {
        return queryFactory
                .selectDistinct(getFieldPath(regionSearchCondition.getField()))
                .from(region)
                .where(eqCondition(
                        regionSearchCondition.getSido(),
                        regionSearchCondition.getGugun(),
                        regionSearchCondition.getDong()))
                .fetch();
    }

    @Override
    public Long findIdBySidoAndGugunAndDong(final String sido, final String gugun, final String dong) {
        return queryFactory
                .select(region.id)
                .from(region)
                .where(eqCondition(sido, gugun, dong))
                .fetchOne();
    }

    private Expression<String> getFieldPath(RegionField field) {
        Expression<String> fieldPath = fieldPathMap.get(field);
        if (fieldPath == null) {
            throw new RegionException(INVALID_REGION_FIELD);
        }
        return fieldPath;
    }

    private BooleanExpression eqCondition(String sido, String gugun, String dong) {
        BooleanExpression predicate = null;

        if (hasText(sido)) {
            predicate = region.sido.eq(sido);
        }
        if (hasText(gugun)) {
            predicate = predicate == null ? region.gugun.eq(gugun) : predicate.and(region.gugun.eq(gugun));
        }
        if (hasText(dong)) {
            predicate = predicate == null ? region.dong.eq(dong) : predicate.and(region.dong.eq(dong));
        }

        return predicate;
    }

    private BooleanExpression eqSido(String sido) {
        return hasText(sido) ? region.sido.eq(sido) : null;
    }

    private BooleanExpression eqGugun(String gugun) {
        return hasText(gugun) ? region.gugun.eq(gugun) : null;
    }

    private BooleanExpression eqDong(String dong) {
        return hasText(dong) ? region.dong.eq(dong) : null;
    }
}
