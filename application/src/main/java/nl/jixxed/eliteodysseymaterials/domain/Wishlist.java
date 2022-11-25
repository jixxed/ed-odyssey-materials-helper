package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
            return APPLICATION_STATE.getPreferredCommander()
                    .map(commander -> APPLICATION_STATE.getWishlists(commander).getAllWishlists().stream()
                            .filter(wishlist -> wishlist != ALL)
                            .flatMap(wishlist -> wishlist.getItems().stream())
                            .toList())
                    .orElse(Collections.unmodifiableList(this.items));
        }
        return this.items;
    }
}
