package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsWishlistMaterialSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist.HorizonsWishlistIngredient;

import java.util.Comparator;

public enum HorizonsWishlistMaterialSort {
    ALPHABETICAL,
    GRADE,
    QUANTITY_REQUIRED;

    public String getLocalizationKey() {
        return "search.sort." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Comparator<HorizonsWishlistIngredient> getSort(final HorizonsWishlistMaterialSearch search) {
        return switch (search.getWishlistMaterialSort()) {
            case ALPHABETICAL ->
                    Comparator.comparing((HorizonsWishlistIngredient o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getHorizonsMaterial().getLocalizationKey()));
            case GRADE ->
                    Comparator.comparing((HorizonsWishlistIngredient o) -> o.getHorizonsMaterial().getRarity().getLevel()).reversed();
            case QUANTITY_REQUIRED ->
                    Comparator.comparing((HorizonsWishlistIngredient o) -> o.getLeftAmount()).reversed();
        };
    }
}
