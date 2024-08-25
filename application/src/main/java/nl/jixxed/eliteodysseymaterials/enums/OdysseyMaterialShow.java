package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.domain.Wishlist;
import nl.jixxed.eliteodysseymaterials.service.FavouriteService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

import java.util.function.Predicate;

public enum OdysseyMaterialShow {
    ALL,
    ALL_WITH_STOCK,
    ALL_ENGINEER_BLUEPRINT,
    REQUIRED_ENGINEER_BLUEPRINT,
    ALL_ENGINEER,
    REQUIRED_ENGINEER,
    BLUEPRINT,
    IRRELEVANT,
    IRRELEVANT_WITH_STOCK,
    NOT_ON_WISHLIST,
    PROHIBITED,
    BACKPACK,
    FLEETCARRIER,
    FAVOURITES;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<? super OdysseyMaterial> getFilter(final Search search) {
        return switch (search.getMaterialShow()) {
            case ALL -> material -> true;
            case ALL_WITH_STOCK ->
                    material -> StorageService.getMaterialCount(material,AmountType.TOTAL) > 0;
            case BACKPACK ->
                    material -> StorageService.getMaterialCount(material,AmountType.BACKPACK) > 0;
            case FLEETCARRIER ->
                    material -> StorageService.getMaterialCount(material,AmountType.FLEETCARRIER) > 0;
            case BLUEPRINT ->
                    OdysseyBlueprintConstants::isBlueprintIngredientWithOverride;
            case IRRELEVANT -> OdysseyMaterialShow::getIrrelevantFilter;
            case IRRELEVANT_WITH_STOCK ->
                    material -> getIrrelevantFilter(material) && StorageService.getMaterialCount(material,AmountType.TOTAL) > 0;
            case PROHIBITED -> OdysseyMaterial::isIllegal;
            case ALL_ENGINEER ->
                    OdysseyBlueprintConstants::isEngineeringIngredient;
            case REQUIRED_ENGINEER ->
                    OdysseyBlueprintConstants::isEngineeringIngredientAndNotCompleted;
            case ALL_ENGINEER_BLUEPRINT ->
                    material -> OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material) || OdysseyBlueprintConstants.isEngineeringIngredient(material);
            case REQUIRED_ENGINEER_BLUEPRINT ->
                    material -> OdysseyBlueprintConstants.isEngineeringIngredientAndNotCompleted(material) || OdysseyBlueprintConstants.isBlueprintIngredientWithOverride(material);
            case FAVOURITES ->
                    FavouriteService::isFavourite;
            case NOT_ON_WISHLIST -> material -> {
                final int amountOnAllWishlists = Wishlist.ALL.getItems().stream().map(odysseyWishlistBlueprint -> OdysseyBlueprintConstants.getRecipe(odysseyWishlistBlueprint.getRecipeName()).getRequiredAmount(material)).mapToInt(Integer::intValue).sum();
                final Integer materialCount = StorageService.getMaterialCount(material, AmountType.TOTAL);
                return materialCount > 0 && (amountOnAllWishlists == 0 || amountOnAllWishlists < materialCount);
            };
        };
    }

    private static boolean getIrrelevantFilter(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getSoloMode() ? OdysseyBlueprintConstants.isNotRelevantWithOverrideAndNotRequiredEngineeringIngredient(odysseyMaterial) : OdysseyBlueprintConstants.isNotRelevantAndNotEngineeringIngredient(odysseyMaterial);
    }
}
