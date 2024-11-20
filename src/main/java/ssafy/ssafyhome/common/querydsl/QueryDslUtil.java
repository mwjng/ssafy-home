package ssafy.ssafyhome.common.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static org.springframework.util.StringUtils.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryDslUtil {

    public static <T> OrderSpecifier[] makeOrderSpecifiers(final EntityPathBase<T> qClass, final Pageable pageable) {
        return pageable.getSort()
                .stream()
                .map(sort -> toOrderSpecifier(qClass, sort))
                .toArray(OrderSpecifier[]::new);
    }

    private static <T> OrderSpecifier toOrderSpecifier(final EntityPathBase<T> qClass, final Sort.Order sortOrder) {
        final Order orderMethod = toOrder(sortOrder);
        final PathBuilder<T> pathBuilder = new PathBuilder<>(qClass.getType(), qClass.getMetadata());
        return new OrderSpecifier(orderMethod, pathBuilder.get(sortOrder.getProperty()));
    }

    private static Order toOrder(final Sort.Order sortOrder) {
        return sortOrder.isAscending() ? Order.ASC : Order.DESC;
    }

    public static <T> BooleanExpression toEqExpression(final SimpleExpression<T> simpleExpression, final T compared) {
        if (Objects.isNull(compared)) {
            return null;
        }
        if (compared instanceof String stringCompared) {
            if (!hasText(stringCompared)) {
                return null;
            }
        }
        return simpleExpression.eq(compared);
    }

    public static BooleanExpression cursorLtExpression(final NumberExpression<Long> expression, final Long cursorValue) {
        if (cursorValue == null || cursorValue == 0) {
            return null;
        }
        return expression.lt(cursorValue);
    }

    public static Sort defaultSort() {
        return Sort.by(
                Sort.Order.desc("id")
        );
    }
}

