package nl.jixxed.eliteodysseymaterials.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableRegion;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionBuilder extends AbstractNodeBuilder<RegionBuilder> {

    public static RegionBuilder builder() {
        return new RegionBuilder();
    }

    public DestroyableRegion build() {
        final DestroyableRegion region = new DestroyableRegion();
        super.build(region);
        return region;
    }
}
