package ssafy.ssafyhome.member.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ssafy.ssafyhome.member.application.response.FollowQueryResponse;

public interface FollowRepositoryCustom {
    Slice<FollowQueryResponse> searchFollowers(final Long memberId, final Pageable pageable, final Long cursorId);
    Slice<FollowQueryResponse> searchFollowings(final Long memberId, final Pageable pageable, final Long cursorId);
}
