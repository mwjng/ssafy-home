package ssafy.ssafyhome.deal.application.request;

public enum DealSortCondition {
    NEWEST_FIRST,       // 최신순
    OLDEST_FIRST,       // 오래된순
    MOST_LIKED,         // 관심 매물로 등록된 수가 많은 순
    HIGHEST_PRICE,      // 가격 비싼순
    LOWEST_PRICE,       // 가격 싼순
    HIGHEST_FLOOR,      // 높은 층 순
    LOWEST_FLOOR,       // 낮은 층 순
    LARGEST_AREA,       // 면적이 넓은 순
    SMALLEST_AREA       // 면적이 좁은 순
}
