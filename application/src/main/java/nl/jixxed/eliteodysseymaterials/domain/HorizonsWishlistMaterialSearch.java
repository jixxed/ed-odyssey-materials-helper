package nl.jixxed.eliteodysseymaterials.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsWishlistMaterialSort;
import nl.jixxed.eliteodysseymaterials.enums.WishlistMaterialGrouping;

@AllArgsConstructor
@Getter
@Setter
public class HorizonsWishlistMaterialSearch {
    private String query;
    private HorizonsWishlistMaterialSort wishlistMaterialSort;
    private WishlistMaterialGrouping wishlistMaterialGrouping;

}
