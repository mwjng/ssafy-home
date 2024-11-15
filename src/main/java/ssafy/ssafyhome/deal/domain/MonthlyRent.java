package ssafy.ssafyhome.deal.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class MonthlyRent extends Deal{

    private Long deposit;
    private Long price;
}
