package ssafy.ssafyhome.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ssafy.ssafyhome.member.application.response.FollowQueryResponse;
import ssafy.ssafyhome.follow.application.response.QFollowQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.follow.domain.QFollow.*;
import static ssafy.ssafyhome.member.domain.QMember.*;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<FollowQueryResponse> searchFollowers(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<FollowQueryResponse> followers = queryFactory
                .select(new QFollowQueryResponse(
                        follow.id,
                        follow.follower.id,
                        follow.follower.nickname,
                        follow.follower.memberRole,
                        follow.follower.dirName))
                .from(follow.follower, member).fetchJoin()
                .where(toEqExpression(follow.following.id, memberId), cursorLtExpression(follow.id, cursorId))
                .orderBy(makeOrderSpecifiers(follow, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(followers);
    }

    @Override
    public Slice<FollowQueryResponse> searchFollowings(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<FollowQueryResponse> followings = queryFactory
                .select(new QFollowQueryResponse(
                        follow.id,
                        follow.following.id,
                        follow.following.nickname,
                        follow.following.memberRole,
                        follow.following.dirName))
                .from(follow.following, member).fetchJoin()
                .where(toEqExpression(follow.follower.id, memberId), cursorLtExpression(follow.id, cursorId))
                .orderBy(makeOrderSpecifiers(follow, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(followings);
    }
}
