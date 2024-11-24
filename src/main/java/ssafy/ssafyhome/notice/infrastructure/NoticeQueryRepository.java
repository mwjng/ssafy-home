package ssafy.ssafyhome.notice.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.notice.application.response.NoticeQueryResponse;
import ssafy.ssafyhome.notice.application.response.QNoticeQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.member.domain.QMember.member;
import static ssafy.ssafyhome.notice.domain.QNotice.notice;

@RequiredArgsConstructor
@Repository
public class NoticeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<NoticeQueryResponse> searchAll(final Long cursorId, Pageable pageable) {
        List<NoticeQueryResponse> notices = queryFactory
                .select(new QNoticeQueryResponse(
                        notice.id,
                        notice.member.id ,
                        notice.member.nickname,
                        notice.title,
                        notice.content,
                        notice.createdAt,
                        notice.modifiedAt))
                .from(notice)
                .join(notice.member, member)
                .where(cursorLtExpression(notice.id, cursorId))
                .orderBy(makeOrderSpecifiers(notice, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(notices);
    }

    public NoticeQueryResponse search(final Long noticeId) {
        return queryFactory
                .select(new QNoticeQueryResponse(
                        notice.id,
                        notice.member.id,
                        notice.member.nickname,
                        notice.title,
                        notice.content,
                        notice.createdAt,
                        notice.modifiedAt))
                .from(notice)
                .join(notice.member, member)
                .where(toEqExpression(notice.id, noticeId))
                .fetchOne();
    }
}
