package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

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

    public ColonisationItem createColonisationItem(final ColonisationBuildable colonisationBuildable) {
        final ColonisationItem colonisationItem = new ColonisationItem(LocaleService.getLocalizedStringForCurrentLocale(colonisationBuildable.getColonisationCategory().getLocalizationKey()) + " - " + LocaleService.getLocalizedStringForCurrentLocale(colonisationBuildable.getLocalizationKey()), colonisationBuildable, colonisationBuildable.getBlueprintCost());
        this.addColonisationItem(colonisationItem);
        this.selectedColonisationItemUUID = colonisationItem.getUuid();
        return colonisationItem;
    }
}
