package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class HorizonsWishlistBlueprint implements WishlistBlueprint<HorizonsBlueprintName> {
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private HorizonsBlueprintName recipeName;
    @ClipboardJsonIgnore
    private boolean visible = true;

    public HorizonsWishlistBlueprint(final HorizonsBlueprintName recipeName, final boolean visible) {
        this.recipeName = recipeName;
        this.visible = visible;
    }
}
