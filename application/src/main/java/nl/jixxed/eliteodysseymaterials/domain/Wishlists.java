/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        final Set<Wishlist> wishlists1 = new HashSet<>(this.wishlists);
        wishlists1.add(Wishlist.ALL);
        return Collections.unmodifiableSet(wishlists1);
    }

    @JsonIgnore
    public Wishlist getSelectedWishlist() {
        if (this.selectedWishlistUUID == null || this.selectedWishlistUUID.isEmpty()) {
            return selectFirstWishlist();
        } else {
            return this.getAllWishlists().stream().filter(wishlist -> wishlist.getUuid().equals(this.selectedWishlistUUID)).findFirst().orElseGet(this::selectFirstWishlist);
        }
    }

    @JsonIgnore
    private Wishlist selectFirstWishlist() {
        final Wishlist wishlist = this.getAllWishlists().stream().filter(wl -> wl != Wishlist.ALL).findFirst().orElse(createInitialWishlist());
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
    public void delete(final String wishlistUUID) {
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
