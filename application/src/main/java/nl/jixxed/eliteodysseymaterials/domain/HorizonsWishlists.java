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
public class HorizonsWishlists {
    @SuppressWarnings("java:S1700")
    private Set<HorizonsWishlist> wishlists = new HashSet<>();
    private String selectedWishlistUUID;

    @JsonIgnore
    public Set<HorizonsWishlist> getAllWishlists() {
        final Set<HorizonsWishlist> wishlists1 = new HashSet<>(this.wishlists);
        wishlists1.add(HorizonsWishlist.ALL);
        return Collections.unmodifiableSet(wishlists1);
    }

    @JsonIgnore
    public HorizonsWishlist getSelectedWishlist() {
        if (this.selectedWishlistUUID == null || this.selectedWishlistUUID.isEmpty()) {
            return selectFirstWishlist();
        } else {
            return this.getAllWishlists().stream().filter(wishlist -> wishlist.getUuid().equals(this.selectedWishlistUUID)).findFirst().orElseGet(this::selectFirstWishlist);
        }
    }

    @JsonIgnore
    private HorizonsWishlist selectFirstWishlist() {
        final HorizonsWishlist wishlist = this.getAllWishlists().stream().filter(wl -> wl != HorizonsWishlist.ALL).findFirst().orElse(createInitialWishlist());
        this.selectedWishlistUUID = wishlist.getUuid();
        return wishlist;
    }

    @JsonIgnore
    private HorizonsWishlist createInitialWishlist() {
        final HorizonsWishlist wishlist = new HorizonsWishlist();
        wishlist.setName("Default wishlist");
        this.wishlists.add(wishlist);
        return wishlist;
    }

    @JsonIgnore
    public HorizonsWishlist getWishlist(final String uuid) {
        return getAllWishlists().stream().filter(wishlist -> wishlist.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @JsonIgnore
    public void addWishlist(final HorizonsWishlist wishlistToAdd) {
        //reset UUID if already exists
        while (this.wishlists.stream().anyMatch(wishlist -> wishlist.getUuid().equals(wishlistToAdd.getUuid()))) {
            wishlistToAdd.setUuid(UUID.randomUUID().toString());
        }
        this.wishlists.add(wishlistToAdd);
    }

    @JsonIgnore
    public void delete(final String wishlistUUID) {
        this.wishlists.removeIf(wishlist -> wishlist.getUuid().equals(wishlistUUID));
        if (this.wishlists.isEmpty()) {
            createInitialWishlist();
        }
    }

    @JsonIgnore
    public void renameWishlist(final String uuid, final String name) {
        if (name != null && !name.isEmpty()) {
            final HorizonsWishlist wishlist = getWishlist(uuid);
            if(wishlist != null) {
                wishlist.setName((name.length() > 50) ? name.substring(0, 50) : name);
            }
        }
    }

    public HorizonsWishlist createWishlist(final String name) {
        final HorizonsWishlist wishlist = new HorizonsWishlist();
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
