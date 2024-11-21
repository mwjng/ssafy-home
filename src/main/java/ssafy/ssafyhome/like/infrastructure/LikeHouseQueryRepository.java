package ssafy.ssafyhome.like.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.like.application.response.LikeHouseQueryResponse;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class LikeHouseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<LikeHouseQueryResponse> searchAll(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<LikeHouseQueryResponse> likeHouses = null;

        return new SliceImpl<>(likeHouses);
    }
}
