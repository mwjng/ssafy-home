package ssafy.ssafyhome.deal.application.request;

public enum DealSortCondition {
    DEAL_DATE_NEWEST_FIRST, // 거래 완료 일자가 최신 순
    NEWEST_FIRST,       // 최신순
    OLDEST_FIRST,       // 오래된순
    HIGHEST_DEPOSIT,    // 보증금 비싼순
    LOWEST_DEPOSIT,     // 보증금 싼순
    HIGHEST_PRICE,      // 가격 비싼순
    LOWEST_PRICE,       // 가격 싼순
    HIGHEST_FLOOR,      // 높은 층 순
    LOWEST_FLOOR,       // 낮은 층 순
    LARGEST_AREA,       // 면적이 넓은 순
    SMALLEST_AREA       // 면적이 좁은 순
}
