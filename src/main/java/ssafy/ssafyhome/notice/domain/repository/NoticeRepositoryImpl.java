package ssafy.ssafyhome.notice.domain.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import ssafy.ssafyhome.notice.application.response.NoticeQueryResponse;
import ssafy.ssafyhome.notice.application.response.QNoticeQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.member.domain.QMember.*;
import static ssafy.ssafyhome.notice.domain.QNotice.*;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<NoticeQueryResponse> searchAll(final Long cursorId, Pageable pageable) {
        JPAQuery<NoticeQueryResponse> query = queryFactory
                .select(new QNoticeQueryResponse(notice.id, member.name, notice.title, notice.content, notice.createdAt, notice.modifiedAt))
                .from(notice.member, member).fetchJoin()
                .where(idLt(cursorId))
                .limit(pageable.getPageSize());

        if (pageable.getSort().isSorted()) {
            query.orderBy(pageable.getSort().stream()
                    .map(this::toOrderSpecifier)
                    .toArray(OrderSpecifier[]::new));
        }

        List<NoticeQueryResponse> notices = query.fetch();

        return new PageImpl<>(notices);
    }

    @Override
    public NoticeQueryResponse search(final Long noticeId) {
        return queryFactory
                .select(new QNoticeQueryResponse(notice.id, member.name, notice.title, notice.content, notice.createdAt, notice.modifiedAt))
                .from(notice.member, member).fetchJoin()
                .where(notice.id.eq(noticeId))
                .fetchOne();
    }

    // Helper Method: Sort.Order -> OrderSpecifier 변환
    private OrderSpecifier<?> toOrderSpecifier(Sort.Order order) {
        PathBuilder<?> entityPath = new PathBuilder<>(notice.getType(), notice.getMetadata());
        return new OrderSpecifier(
                order.isAscending() ? Order.ASC : Order.DESC,
                entityPath.get(order.getProperty())
        );
    }

    private BooleanExpression idLt(final Long cursorId) {
        if (cursorId == null || cursorId == 0) {
            return null;
        }
        return notice.id.lt(cursorId);
    }
}
