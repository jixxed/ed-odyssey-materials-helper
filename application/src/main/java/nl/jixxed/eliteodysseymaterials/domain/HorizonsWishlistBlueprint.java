package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract sealed class HorizonsWishlistBlueprint implements WishlistBlueprint<HorizonsBlueprintName> permits HorizonsExperimentalWishlistBlueprint,
        HorizonsModuleWishlistBlueprint,
        HorizonsSynthesisWishlistBlueprint,
        HorizonsTechBrokerWishlistBlueprint,
        HorizonsEngineerWishlistBlueprint {
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private HorizonsBlueprintName recipeName;
    @ClipboardJsonIgnore
    private boolean visible = true;

    public HorizonsWishlistBlueprint(final HorizonsBlueprintName recipeName, final boolean visible) {
        this.recipeName = recipeName;
        this.visible = visible;
    }

    public static HorizonsBlueprintType getBlueprintType(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsModuleWishlistBlueprint horizonsModuleWishlistBlueprint) {
            return horizonsModuleWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsExperimentalWishlistBlueprint horizonsExperimentalWishlistBlueprint) {
            return horizonsExperimentalWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsTechBrokerWishlistBlueprint horizonsTechBrokerWishlistBlueprint) {
            return horizonsTechBrokerWishlistBlueprint.getBlueprintType();
        } else if (blueprint instanceof HorizonsEngineerWishlistBlueprint) {
            return HorizonsBlueprintType.ENGINEER;
        }
        return null;
    }

    public static HorizonsBlueprintGrade getBlueprintGrade(final WishlistBlueprint<HorizonsBlueprintName> blueprint) {
        if (blueprint instanceof HorizonsSynthesisWishlistBlueprint horizonsSynthesisWishlistBlueprint) {
            return horizonsSynthesisWishlistBlueprint.getBlueprintGrade();
        }
        return HorizonsBlueprintGrade.NONE;
    }
}
