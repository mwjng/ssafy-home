package ssafy.ssafyhome.like.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.common.querydsl.QueryDslUtil;
import ssafy.ssafyhome.house.domain.QHouse;
import ssafy.ssafyhome.like.application.response.LikeHouseQueryResponse;
import ssafy.ssafyhome.like.application.response.QLikeHouseQueryResponse;
import ssafy.ssafyhome.like.domain.QLikeHouse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.house.domain.QHouse.*;
import static ssafy.ssafyhome.like.domain.QLikeHouse.*;

@RequiredArgsConstructor
@Repository
public class LikeHouseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<LikeHouseQueryResponse> searchAll(final Long memberId, final Pageable pageable, final Long cursorId) {
//        queryFactory
//                .select(new QLikeHouseQueryResponse())
//                .from(likeHouse.house, house).fetchJoin()
//                .where(toEqExpression(likeHouse.member.id, memberId), cursorLtExpression(likeHouse.id, cursorId))
        List<LikeHouseQueryResponse> likeHouses = null;

        return new SliceImpl<>(likeHouses);
    }
}
