/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

    int getQuantity();

    void setQuantity(int quantity);

    /**
     * Returns the blueprint for this wishlist blueprint.
     * In case of a HorizonsModuleWishlistBlueprint, the highest grade is used to determine the blueprint.
     */
    @JsonIgnore
    @SuppressWarnings("unchecked")
    default Blueprint<E> getBlueprint() {
        try {
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
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    @JsonIgnore
    private HorizonsBlueprintGrade getHighestGrade(HorizonsModuleWishlistBlueprint moduleWishlistBlueprint) {
        return moduleWishlistBlueprint.getPercentageToComplete().entrySet().stream()
                .max(Comparator.comparing(horizonsBlueprintGradeDoubleEntry -> horizonsBlueprintGradeDoubleEntry.getKey().getGrade()))
                .map(Map.Entry::getKey)
                .orElse(HorizonsBlueprintGrade.NONE);
    }
}
