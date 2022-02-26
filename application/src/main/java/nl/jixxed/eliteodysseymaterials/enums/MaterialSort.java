package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Comparator;
import java.util.Map;

public enum MaterialSort {
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
    public static Comparator<Map.Entry<? extends OdysseyMaterial, Storage>> getSort(final Search search) {
        return switch (search.getMaterialSort()) {
            case ALPHABETICAL -> Comparator.comparing((Map.Entry<? extends OdysseyMaterial, Storage> o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()));
            case QUANTITY -> Comparator.comparing((Map.Entry<? extends OdysseyMaterial, Storage> o) -> o.getValue().getTotalValue()).reversed();
            case RELEVANT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends OdysseyMaterial, Storage> o) -> OdysseyBlueprintConstants.isEngineeringIngredient(o.getKey()) || OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(o.getKey())).reversed().thenComparing((Map.Entry<? extends OdysseyMaterial, Storage> o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()));
            case ENGINEER_BLUEPRINT_IRRELEVANT -> Comparator.comparing((Map.Entry<? extends OdysseyMaterial, Storage> o) -> OdysseyBlueprintConstants.isEngineeringIngredient(o.getKey())).thenComparing((Map.Entry<? extends OdysseyMaterial, Storage> o) -> OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(o.getKey())).reversed().thenComparing((Map.Entry<? extends OdysseyMaterial, Storage> o) -> LocaleService.getLocalizedStringForCurrentLocale(o.getKey().getLocalizationKey()));
        };
    }
}
