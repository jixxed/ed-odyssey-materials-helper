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
