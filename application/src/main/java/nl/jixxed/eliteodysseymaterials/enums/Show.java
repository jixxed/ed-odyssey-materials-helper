package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.constants.RecipeConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Map;
import java.util.function.Predicate;

public enum Show {
    ALL,
    ALL_WITH_STOCK,
    ALL_ENGINEER_BLUEPRINT,
    REQUIRED_ENGINEER_BLUEPRINT,
    ALL_ENGINEER,
    REQUIRED_ENGINEER,
    BLUEPRINT,
    IRRELEVANT,
    IRRELEVANT_WITH_STOCK,
    PROHIBITED,
    FAVOURITES;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    public static Predicate<? super Map.Entry<? extends Material, Storage>> getFilter(final Search search) {
        return switch (search.getShow()) {
            case ALL -> (Map.Entry<? extends Material, Storage> o) -> true;
            case ALL_WITH_STOCK -> (Map.Entry<? extends Material, Storage> o) -> o.getValue().getTotalValue() > 0;
            case BLUEPRINT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isBlueprintIngredient(o.getKey());
            case IRRELEVANT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isNotRelevantAndNotEngineeringIngredient(o.getKey());
            case IRRELEVANT_WITH_STOCK -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isNotRelevantAndNotEngineeringIngredient(o.getKey()) && o.getValue().getTotalValue() > 0;
            case PROHIBITED -> (Map.Entry<? extends Material, Storage> o) -> o.getKey().isIllegal();
            case ALL_ENGINEER -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredient(o.getKey());
            case REQUIRED_ENGINEER -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredientAndNotCompleted(o.getKey());
            case ALL_ENGINEER_BLUEPRINT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isBlueprintIngredient(o.getKey()) || RecipeConstants.isEngineeringIngredient(o.getKey());
            case REQUIRED_ENGINEER_BLUEPRINT -> (Map.Entry<? extends Material, Storage> o) -> RecipeConstants.isEngineeringIngredientAndNotCompleted(o.getKey()) || RecipeConstants.isBlueprintIngredient(o.getKey());
            case FAVOURITES -> (Map.Entry<? extends Material, Storage> o) -> APPLICATION_STATE.isFavourite(o.getKey());
        };
    }
}
