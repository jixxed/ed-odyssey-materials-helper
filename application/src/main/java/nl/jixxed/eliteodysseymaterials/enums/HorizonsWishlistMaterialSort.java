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
        return getSort(search.getWishlistMaterialSort());
    }

    @SuppressWarnings("java:S1452")
    public Comparator<HorizonsWishlistIngredient> getSort() {
        return getSort(this);
    }

    @SuppressWarnings("java:S1452")
    public static Comparator<HorizonsWishlistIngredient> getSort(HorizonsWishlistMaterialSort sort) {
        return switch (sort) {
            case ALPHABETICAL ->
                    Comparator.comparing((HorizonsWishlistIngredient o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getHorizonsMaterial().getLocalizationKey()));
            case GRADE ->
                    Comparator.comparing((HorizonsWishlistIngredient o) -> o.getHorizonsMaterial().getRarity().getLevel())
                            .reversed()
                            .thenComparing((HorizonsWishlistIngredient o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getHorizonsMaterial().getLocalizationKey()));
            case QUANTITY_REQUIRED -> Comparator.comparing(HorizonsWishlistIngredient::getRequired)
                    .reversed()
                    .thenComparing((HorizonsWishlistIngredient o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getHorizonsMaterial().getLocalizationKey()));
        };
    }
}
