/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        return getSort(search.getWishlistMaterialSort());
    }

    @SuppressWarnings("java:S1452")
    public Comparator<OdysseyWishlistIngredient> getSort() {
        return getSort(this);
    }

    private static Comparator<OdysseyWishlistIngredient> getSort(OdysseyWishlistMaterialSort sort) {
        return switch (sort) {
            case ALPHABETICAL ->
                    Comparator.comparing((OdysseyWishlistIngredient o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getOdysseyMaterial().getLocalizationKey()));
            case QUANTITY_REQUIRED ->
                    Comparator.comparing((OdysseyWishlistIngredient o) -> o.getRequired().get()).reversed()
                            .thenComparing((OdysseyWishlistIngredient o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getOdysseyMaterial().getLocalizationKey()));
        };
    }
}
