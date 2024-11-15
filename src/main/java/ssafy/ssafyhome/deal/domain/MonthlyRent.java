package ssafy.ssafyhome.deal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class MonthlyRent extends Deal {

    @Column(nullable = false)
    private Long deposit;

    @Column(nullable = false, name = "monthly_rent_price")
    private Long price;
}
