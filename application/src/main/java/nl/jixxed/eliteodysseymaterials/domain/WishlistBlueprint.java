package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyBlueprintName;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishlistBlueprint {
    private OdysseyBlueprintName recipeName;
    @ClipboardJsonIgnore
    private boolean visible = true;
}
