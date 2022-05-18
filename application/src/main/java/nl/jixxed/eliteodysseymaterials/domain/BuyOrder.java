package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;

@Getter
public class BuyOrder extends Order {
    private final Integer total;
    private final Integer outstanding;

    public BuyOrder(final Integer price, final Integer total, final Integer outstanding) {
        super(price);
        this.total = total;
        this.outstanding = outstanding;
    }
}
