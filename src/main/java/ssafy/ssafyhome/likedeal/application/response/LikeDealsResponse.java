package ssafy.ssafyhome.likedeal.application.response;

import ssafy.ssafyhome.deal.application.response.MonthlyRentResponse;
import ssafy.ssafyhome.deal.application.response.RentResponse;
import ssafy.ssafyhome.deal.application.response.SaleResponse;

import java.util.List;

public record LikeDealsResponse(
        List<SaleResponse> likeSales,
        List<RentResponse> likeRents,
        List<MonthlyRentResponse> likeMonthlyRents) {
}
