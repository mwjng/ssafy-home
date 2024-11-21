package ssafy.ssafyhome.deal.infrastructure;

import com.querydsl.core.QueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.ssafyhome.deal.domain.Deal;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DealQueryRepository {

    private final QueryFactory queryFactory;

//    public List<Deal> findDeals(Long memberId, Long houseId, )
}
