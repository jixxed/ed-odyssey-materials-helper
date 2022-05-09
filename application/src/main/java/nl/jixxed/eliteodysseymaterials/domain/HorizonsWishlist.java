package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HorizonsWishlist {
    @EqualsAndHashCode.Include
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private List<WishlistBlueprint<HorizonsBlueprintName>> items = new ArrayList<>();

    @Override
    public String toString() {
        return this.name;
    }

}
