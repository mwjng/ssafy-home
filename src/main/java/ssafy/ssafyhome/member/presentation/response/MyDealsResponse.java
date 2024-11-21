package ssafy.ssafyhome.member.presentation.response;

import java.util.List;

public record MyDealsResponse(
    List<MyDealResponse> myDeals
) {
}
