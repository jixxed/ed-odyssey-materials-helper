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

import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.Comparator;

public enum OdysseyMaterialSort {
    ENGINEER_BLUEPRINT_IRRELEVANT,
    RELEVANT_IRRELEVANT,
    ALPHABETICAL,
    QUANTITY;

    public String getLocalizationKey() {
        return "search.sort." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Comparator<? super OdysseyMaterial> getSort(final Search search) {
        return switch (search.getMaterialSort()) {
            case ALPHABETICAL ->
                    Comparator.comparing((OdysseyMaterial material) -> LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()));
            case QUANTITY ->
                    Comparator.comparing((OdysseyMaterial material) -> StorageService.getMaterialCount(material,AmountType.TOTAL)).reversed();
            case RELEVANT_IRRELEVANT ->
                    Comparator.comparing((OdysseyMaterial material) -> OdysseyBlueprintConstants.isEngineeringIngredient(material) || OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material))
                            .reversed()
                            .thenComparing((OdysseyMaterial material) -> LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()));
            case ENGINEER_BLUEPRINT_IRRELEVANT ->
                    Comparator.comparing(OdysseyBlueprintConstants::isEngineeringIngredient).thenComparing(OdysseyBlueprintConstants::isBlueprintIngredientWithOverride)
                            .reversed()
                            .thenComparing((OdysseyMaterial material) -> LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()));
        };
    }
}
