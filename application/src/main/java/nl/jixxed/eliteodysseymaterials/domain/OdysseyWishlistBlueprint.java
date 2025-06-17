package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class OdysseyWishlistBlueprint implements WishlistBlueprint<OdysseyBlueprintName> {

    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();

    private OdysseyBlueprintName recipeName;
    @ClipboardJsonIgnore
    private boolean visible = true;

    private int quantity = 1;

    public OdysseyWishlistBlueprint(OdysseyBlueprintName recipeName) {
        this.recipeName = recipeName;
    }
}
