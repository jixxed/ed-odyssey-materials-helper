package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyWishlistMaterialSort;
import nl.jixxed.eliteodysseymaterials.enums.WishlistMaterialGrouping;

@AllArgsConstructor
@Getter
@Setter
public class OdysseyWishlistMaterialSearch {
    private String query;
    private OdysseyWishlistMaterialSort wishlistMaterialSort;
    private WishlistMaterialGrouping wishlistMaterialGrouping;

}
