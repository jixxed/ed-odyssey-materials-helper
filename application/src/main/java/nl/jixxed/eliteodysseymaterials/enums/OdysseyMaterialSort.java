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
