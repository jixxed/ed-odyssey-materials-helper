package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public final class HorizonsExperimentalWishlistBlueprint extends HorizonsWishlistBlueprint {
    private HorizonsBlueprintType blueprintType;

    public void setRecipeName(HorizonsBlueprintName recipeName) {
        super.setRecipeName(recipeName);
        bugfix(recipeName, this.blueprintType);
    }
    private void bugfix(HorizonsBlueprintName recipeName, HorizonsBlueprintType blueprintType) {
        if (HorizonsBlueprintName.MISSILE_RACK.equals(recipeName)) {
            super.setRecipeName(HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK);
        }
        if (HorizonsBlueprintName.DUMBFIRE_MISSILE_RACK.equals(getRecipeName()) && blueprintType == HorizonsBlueprintType.DRAG_MUNITION) {
            super.setRecipeName(HorizonsBlueprintName.SEEKER_MISSILE_RACK);
        }
    }

    public void setBlueprintType(HorizonsBlueprintType blueprintType) {
        this.blueprintType = blueprintType;
        bugfix(getRecipeName(), blueprintType);
    }
}
