package nl.jixxed.eliteodysseymaterials.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;

import java.util.Comparator;
import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = OdysseyWishlistBlueprint.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HorizonsExperimentalWishlistBlueprint.class, name = "experimental"),
        @JsonSubTypes.Type(value = HorizonsModuleWishlistBlueprint.class, name = "module"),
        @JsonSubTypes.Type(value = HorizonsSynthesisWishlistBlueprint.class, name = "synthesis"),
        @JsonSubTypes.Type(value = HorizonsTechBrokerWishlistBlueprint.class, name = "techbroker"),
        @JsonSubTypes.Type(value = HorizonsEngineerWishlistBlueprint.class, name = "engineer"),
        @JsonSubTypes.Type(value = OdysseyWishlistBlueprint.class, name = "odyssey")
})
public sealed interface WishlistBlueprint<E extends BlueprintName<E>> permits HorizonsWishlistBlueprint, OdysseyWishlistBlueprint {
    BlueprintName<E> getRecipeName();

    boolean isVisible();

    void setVisible(boolean visible);

    /**
     * Returns the blueprint for this wishlist blueprint.
     * In case of a HorizonsModuleWishlistBlueprint, the highest grade is used to determine the blueprint.
     */
    @JsonIgnore
    @SuppressWarnings("unchecked")
    default Blueprint<E> getBlueprint() {
        return (Blueprint<E>) switch (this) {
            case HorizonsModuleWishlistBlueprint bp ->
                    HorizonsBlueprintConstants.getRecipe(bp.getRecipeName(), bp.getBlueprintType(), getHighestGrade(bp));
            case HorizonsExperimentalWishlistBlueprint bp ->
                    HorizonsBlueprintConstants.getRecipe(bp.getRecipeName(), bp.getBlueprintType(), null);
            case HorizonsSynthesisWishlistBlueprint bp ->
                    HorizonsBlueprintConstants.getRecipe(bp.getRecipeName(), null, bp.getBlueprintGrade());
            case HorizonsTechBrokerWishlistBlueprint bp ->
                    HorizonsBlueprintConstants.getRecipe(bp.getRecipeName(), bp.getBlueprintType(), null);
            case HorizonsEngineerWishlistBlueprint bp ->
                    HorizonsBlueprintConstants.getRecipe(bp.getRecipeName(), null, null);
            case OdysseyWishlistBlueprint bp -> OdysseyBlueprintConstants.getRecipe(bp.getRecipeName());
        };
    }

    @JsonIgnore
    private HorizonsBlueprintGrade getHighestGrade(HorizonsModuleWishlistBlueprint moduleWishlistBlueprint) {
        return moduleWishlistBlueprint.getPercentageToComplete().entrySet().stream()
                .max(Comparator.comparing(horizonsBlueprintGradeDoubleEntry -> horizonsBlueprintGradeDoubleEntry.getKey().getGrade()))
                .map(Map.Entry::getKey)
                .orElse(HorizonsBlueprintGrade.NONE);
    }
}
