package ssafy.ssafyhome.deal.application.response;

import java.math.BigDecimal;
import java.util.List;

public record AverageResponse(
        BigDecimal exclusiveArea,
        List<TypeAndPrice> typeAndPrice) {

    public static AverageResponse of(final BigDecimal exclusiveArea, final List<TypeAndPrice> typeAndPrice){
        return new AverageResponse(
                exclusiveArea,
                typeAndPrice
        );
    }
}
