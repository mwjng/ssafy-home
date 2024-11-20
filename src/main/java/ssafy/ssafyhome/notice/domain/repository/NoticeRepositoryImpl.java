package ssafy.ssafyhome.notice.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ssafy.ssafyhome.notice.application.response.NoticeQueryResponse;
import ssafy.ssafyhome.notice.application.response.QNoticeQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.member.domain.QMember.*;
import static ssafy.ssafyhome.notice.domain.QNotice.*;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<NoticeQueryResponse> searchAll(final Long cursorId, Pageable pageable) {
        List<NoticeQueryResponse> notices = queryFactory
                .select(new QNoticeQueryResponse(notice.id, member.nickname, notice.title, notice.content, notice.createdAt, notice.modifiedAt))
                .from(notice.member, member).fetchJoin()
                .where(cursorLtExpression(notice.id, cursorId))
                .orderBy(makeOrderSpecifiers(notice, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(notices);
    }

    @Override
    public NoticeQueryResponse search(final Long noticeId) {
        return queryFactory
                .select(new QNoticeQueryResponse(notice.id, member.nickname, notice.title, notice.content, notice.createdAt, notice.modifiedAt))
                .from(notice.member, member).fetchJoin()
                .where(toEqExpression(notice.id, noticeId))
                .fetchOne();
    }
}
