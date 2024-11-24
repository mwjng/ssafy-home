package ssafy.ssafyhome.member.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.member.application.response.FollowQueryResponse;
import ssafy.ssafyhome.member.application.response.QFollowQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.makeOrderSpecifiers;
import static ssafy.ssafyhome.member.domain.QFollow.follow;
import static ssafy.ssafyhome.member.domain.QMember.member;

@RequiredArgsConstructor
@Repository
public class FollowQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<FollowQueryResponse> searchFollowers(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<FollowQueryResponse> followers = queryFactory
                .select(new QFollowQueryResponse(
                        follow.id,
                        follow.follower.id,
                        follow.follower.nickname,
                        follow.follower.memberRole,
                        follow.follower.dirName))
                .from(follow)
                .join(follow.follower, member)
                .where(toEqExpression(follow.following.id, memberId), cursorLtExpression(follow.id, cursorId))
                .orderBy(makeOrderSpecifiers(follow, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(followers);
    }

    public Slice<FollowQueryResponse> searchFollowings(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<FollowQueryResponse> followings = queryFactory
                .select(new QFollowQueryResponse(
                        follow.id,
                        follow.following.id,
                        follow.following.nickname,
                        follow.following.memberRole,
                        follow.following.dirName))
                .from(follow)
                .join(follow.following, member)
                .where(toEqExpression(follow.follower.id, memberId), cursorLtExpression(follow.id, cursorId))
                .orderBy(makeOrderSpecifiers(follow, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(followings);
    }
}
