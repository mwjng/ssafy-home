package ssafy.ssafyhome.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import ssafy.ssafyhome.member.application.response.ReceivedMessageQueryResponse;
import ssafy.ssafyhome.member.application.response.SentMessageQueryResponse;

import java.util.List;

@RequiredArgsConstructor
public class DirectMessageRepositoryImpl implements DirectMessageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReceivedMessageQueryResponse> searchReceivedMessages(final Long memberId, final Pageable pageable, final Long cursorId) {
        return List.of();
    }

    @Override
    public List<SentMessageQueryResponse> searchSentMessages(final Long memberId, final Pageable pageable, final Long cursorId) {
        return List.of();
    }
}
