package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;

@Getter
public class SellOrder extends Order {
    private final Integer stock;

    public SellOrder(final Integer price, final Integer stock) {
        super(price);
        this.stock = stock;
    }
}
