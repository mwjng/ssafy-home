package ssafy.ssafyhome.like.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import ssafy.ssafyhome.like.application.response.LikeHouseQueryResponse;
import ssafy.ssafyhome.like.application.response.QLikeHouseQueryResponse;

import java.util.List;

import static ssafy.ssafyhome.article.domain.QArticle.article;
import static ssafy.ssafyhome.common.querydsl.QueryDslUtil.*;
import static ssafy.ssafyhome.house.domain.QHouse.house;
import static ssafy.ssafyhome.like.domain.QLikeArticle.likeArticle;
import static ssafy.ssafyhome.like.domain.QLikeHouse.likeHouse;
import static ssafy.ssafyhome.member.domain.QMember.member;
import static ssafy.ssafyhome.region.domain.QRegion.region;

@RequiredArgsConstructor
@Repository
public class LikeHouseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<LikeHouseQueryResponse> searchAll(final Long memberId, final Pageable pageable, final Long cursorId) {
        List<LikeHouseQueryResponse> likeHouses = queryFactory
                .select(new QLikeHouseQueryResponse(
                        likeHouse.id,
                        house.id,
                        house.name,
                        house.buildYear,
                        house.jibun,
                        house.road,
                        house.bonbun,
                        house.bubun,
                        house.latitude,
                        house.longitude,
                        house.type,
                        region,
                        house.dirName
                ))
                .from(likeHouse)
                .join(likeHouse.house, house)
                .join(house.region, region)
                .where(
                        toEqExpression(likeHouse.member.id, memberId),
                        cursorLtExpression(likeHouse.id, cursorId)
                )
                .orderBy(makeOrderSpecifiers(likeHouse, pageable))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(likeHouses);
    }
}
