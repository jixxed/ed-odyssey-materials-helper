package nl.jixxed.eliteodysseymaterials.service.event;

public class WishlistChangedEvent extends Event {
    final Integer wishlistSize;

    public WishlistChangedEvent(final Integer wishlistSize) {
        this.wishlistSize = wishlistSize;
    }

    public Integer getWishlistSize() {
        return this.wishlistSize;
    }
}
