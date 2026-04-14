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
public class ColonisationItems {
    private String selectedColonisationItemUUID;
    @SuppressWarnings("java:S1700")
    private final Set<ColonisationItem> colonisationItems = new HashSet<>();

    @JsonIgnore
    public Set<ColonisationItem> getAllColonisationItems() {
        final Set<ColonisationItem> wishlists1 = new HashSet<>(this.colonisationItems);
        wishlists1.add(ColonisationItem.ALL);
        if (ColonisationItem.getCurrent() != null) {
            wishlists1.add(ColonisationItem.getCurrent());
        }
        return Collections.unmodifiableSet(wishlists1);
    }

    @JsonIgnore
    public ColonisationItem getSelectedColonisationItem() {
        if (this.selectedColonisationItemUUID == null || this.selectedColonisationItemUUID.isEmpty()) {
            return ColonisationItem.ALL;
        } else {
            return this.getAllColonisationItems().stream().filter(ci -> ci.getUuid().equals(this.selectedColonisationItemUUID)).findFirst().orElse(ColonisationItem.ALL);
        }
    }

    @JsonIgnore
    public ColonisationItem getColonisationItem(final String uuid) {
        return getAllColonisationItems().stream().filter(wishlist -> wishlist.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @JsonIgnore
    public void addColonisationItem(final ColonisationItem wishlistToAdd) {
        //reset UUID if already exists
        while (this.colonisationItems.stream().anyMatch(colonisationItem -> colonisationItem.getUuid().equals(wishlistToAdd.getUuid()))) {
            wishlistToAdd.setUuid(UUID.randomUUID().toString());
        }
        this.colonisationItems.add(wishlistToAdd);
    }

    @JsonIgnore
    public void delete(final String colonisationItemUUID) {
        this.colonisationItems.removeIf(colonisationItem -> colonisationItem.getUuid().equals(colonisationItemUUID));
    }

    @JsonIgnore
    public void renameColonisationItem(final String colonisationItemUUID, final String name) {
        if (name != null && !name.isEmpty()) {
            final ColonisationItem colonisationItem = getColonisationItem(colonisationItemUUID);
            colonisationItem.setName((name.length() > 50) ? name.substring(0, 50) : name);
        }
    }

//    public ColonisationItem createColonisationItem(final ColonisationBuildable colonisationBuildable) {
//        final ColonisationItem colonisationItem = new ColonisationItem(LocaleService.getLocalizedStringForCurrentLocale(colonisationBuildable.getColonisationCategory().getLocalizationKey()) + " - " + LocaleService.getLocalizedStringForCurrentLocale(colonisationBuildable.getLocalizationKey()), colonisationBuildable, colonisationBuildable.getBlueprintCost());
//        this.addColonisationItem(colonisationItem);
//        this.selectedColonisationItemUUID = colonisationItem.getUuid();
//        return colonisationItem;
//    }
}
