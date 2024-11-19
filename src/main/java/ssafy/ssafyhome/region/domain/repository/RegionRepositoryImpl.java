package ssafy.ssafyhome.region.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ssafy.ssafyhome.region.application.response.RegionResponse;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public RegionResponse findBySearchCondition(final RegionSearchCondition regionSearchCondition) {
        return null;
    }
}
