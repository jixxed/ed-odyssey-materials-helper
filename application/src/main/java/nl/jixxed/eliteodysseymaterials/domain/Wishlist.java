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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Wishlist {
    @JsonIgnore
    public static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    @JsonIgnore
    public static final Wishlist ALL = new Wishlist("0", "All Wishlists", new ArrayList<>());
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private List<OdysseyWishlistBlueprint> items = new ArrayList<>();

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {

        if (this == ALL) {
            return LocaleService.getLocalizedStringForCurrentLocale("tab.wishlist.option.all");
        }
        return this.name;
    }

    public List<OdysseyWishlistBlueprint> getItems() {
        if (this == ALL) {
            var allItems = APPLICATION_STATE.getPreferredCommander()
                    .map(commander -> WishlistService.getOdysseyWishlists(commander).getAllWishlists().stream()
                            .filter(wishlist -> wishlist != ALL)
                            .flatMap(wishlist -> wishlist.getItems().stream())
                            .collect(Collectors.toList()))
                    .orElseGet(() -> new ArrayList<>(this.items));
            return aggregateBlueprints(allItems);
        }
        return this.items;
    }

    @JsonIgnore
    private List<OdysseyWishlistBlueprint> aggregateBlueprints(List<OdysseyWishlistBlueprint> blueprints) {
        List<OdysseyWishlistBlueprint> aggregated = new ArrayList<>();

        for (OdysseyWishlistBlueprint item : blueprints) {
            boolean found = false;
            for (OdysseyWishlistBlueprint existing : aggregated) {
                if (existing.getRecipeName().equals(item.getRecipeName())) {
                    existing.setQuantity(existing.getQuantity() + item.getQuantity());
                    found = true;
                    break;
                }
            }
            if (!found) {
                aggregated.add(item);
            }
        }

        return aggregated;
    }

}
