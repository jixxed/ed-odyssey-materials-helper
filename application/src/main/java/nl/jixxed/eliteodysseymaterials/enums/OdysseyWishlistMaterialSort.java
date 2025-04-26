package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.domain.OdysseyWishlistMaterialSearch;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.wishlist.OdysseyWishlistIngredient;

import java.util.Comparator;

public enum OdysseyWishlistMaterialSort {
    ALPHABETICAL,
    QUANTITY_REQUIRED;

    public String getLocalizationKey() {
        return "search.sort." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Comparator<OdysseyWishlistIngredient> getSort(final OdysseyWishlistMaterialSearch search) {
        return switch (search.getWishlistMaterialSort()) {
            case ALPHABETICAL ->
                    Comparator.comparing((OdysseyWishlistIngredient o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getOdysseyMaterial().getLocalizationKey()));
            case QUANTITY_REQUIRED -> Comparator.comparing((OdysseyWishlistIngredient o) -> o.getRequired()).reversed();
        };
    }
}
