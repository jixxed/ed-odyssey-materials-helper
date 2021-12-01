package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
public class Wishlists {
    @SuppressWarnings("java:S1700")
    private Set<Wishlist> wishlists = new HashSet<>();
    private String selectedWishlistUUID;

    @JsonIgnore
    public Set<Wishlist> getAllWishlists() {
        return Collections.unmodifiableSet(this.wishlists);
    }

    @JsonIgnore
    public Wishlist getSelectedWishlist() {
        if (this.selectedWishlistUUID == null || this.selectedWishlistUUID.isEmpty()) {
            return selectFirstWishlist();
        } else {
            return this.wishlists.stream().filter(wishlist -> wishlist.getUuid().equals(this.selectedWishlistUUID)).findFirst().orElse(selectFirstWishlist());
        }
    }

    @JsonIgnore
    private Wishlist selectFirstWishlist() {
        final Wishlist wishlist = this.wishlists.stream().findFirst().orElse(createInitialWishlist());
        this.selectedWishlistUUID = wishlist.getUuid();
        return wishlist;
    }

    @JsonIgnore
    private Wishlist createInitialWishlist() {
        final Wishlist wishlist = new Wishlist();
        wishlist.setName("Default wishlist");
        this.wishlists.add(wishlist);
        return wishlist;
    }

    @JsonIgnore
    public Wishlist getWishlist(final String uuid) {
        return getAllWishlists().stream().filter(wishlist -> wishlist.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @JsonIgnore
    public void addWishlist(final Wishlist wishlistToAdd) {
        //reset UUID if already exists
        while (this.wishlists.stream().anyMatch(wishlist -> wishlist.getUuid().equals(wishlistToAdd.getUuid()))) {
            wishlistToAdd.setUuid(UUID.randomUUID().toString());
        }
        this.wishlists.add(wishlistToAdd);
    }

    @JsonIgnore
    void delete(final String wishlistUUID) {
        this.wishlists.removeIf(wishlist -> wishlist.getUuid().equals(wishlistUUID));
        if (this.wishlists.isEmpty()) {
            createInitialWishlist();
        }
    }

    @JsonIgnore
    public void renameWishlist(final String uuid, final String name) {
        if (name != null && !name.isEmpty()) {
            final Wishlist wishlist = getWishlist(uuid);
            wishlist.setName((name.length() > 50) ? name.substring(0, 50) : name);
        }
    }

    public Wishlist createWishlist(final String name) {
        final Wishlist wishlist = new Wishlist();
        if (name != null && !name.isEmpty()) {
            wishlist.setName(name);
        } else {
            wishlist.setName("Default wishlist");
        }
        this.addWishlist(wishlist);
        this.selectedWishlistUUID = wishlist.getUuid();
        return wishlist;
    }
}
