package ssafy.ssafyhome.member.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.member.application.response.QReceivedMessageQueryResponse;
import ssafy.ssafyhome.member.application.response.QSentMessageQueryResponse;
import ssafy.ssafyhome.member.application.response.ReceivedMessageQueryResponse;
import ssafy.ssafyhome.member.application.response.SentMessageQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.member.domain.QDirectMessage.directMessage;
import static ssafy.ssafyhome.member.domain.QMember.member;

@RequiredArgsConstructor
@Repository
public class DirectMessageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<ReceivedMessageQueryResponse> searchReceivedMessages(final Long receiverId, final Pageable pageable, final Long cursorId) {
        List<ReceivedMessageQueryResponse> messages = queryFactory
                .select(new QReceivedMessageQueryResponse(
                        directMessage.id,
                        directMessage.receiver.id,
                        directMessage.receiver.nickname,
                        directMessage.content,
                        directMessage.status,
                        directMessage.createdAt,
                        directMessage.modifiedAt))
                .from(directMessage)
                .join(directMessage.sender, member)
                .where(toEqExpression(directMessage.receiver.id, receiverId), cursorLtExpression(directMessage.id, cursorId))
                .orderBy(makeOrderSpecifiers(directMessage, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(messages);
    }

    public Slice<SentMessageQueryResponse> searchSentMessages(final Long senderId, final Pageable pageable, final Long cursorId) {
        List<SentMessageQueryResponse> messages = queryFactory
                .select(new QSentMessageQueryResponse(
                        directMessage.id,
                        directMessage.receiver.id,
                        directMessage.receiver.nickname,
                        directMessage.content,
                        directMessage.status,
                        directMessage.createdAt,
                        directMessage.modifiedAt))
                .from(directMessage)
                .join(directMessage.receiver, member)
                .where(toEqExpression(directMessage.sender.id, senderId), cursorLtExpression(directMessage.id, cursorId))
                .orderBy(makeOrderSpecifiers(directMessage, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(messages);
    }
}
