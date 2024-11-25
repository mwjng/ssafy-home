package ssafy.ssafyhome.deal.application.response;

import ssafy.ssafyhome.deal.domain.DealType;

public record TypeAndPrice(
        DealType type,
        Double averagePrice) {

    public static TypeAndPrice from(AverageQueryResponse queryResponse) {
        return new TypeAndPrice(
                queryResponse.type(),
                queryResponse.averagePrice()
        );
    }
}

