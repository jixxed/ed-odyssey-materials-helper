package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static nl.jixxed.eliteodysseymaterials.enums.MineableLocation.*;

@RequiredArgsConstructor
@Getter
public enum Mineable {
    ALEXANDRITE(RegularCommodity.ALEXANDRITE, true, List.of(
            ROCKY_CORE,
            METAL_RICH_CORE,
            ICY_CORE
    )),
    BAUXITE(RegularCommodity.BAUXITE, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT
    )),
    BENITOITE(RegularCommodity.BENITOITE, true, List.of(
            ROCKY_CORE
    )),
    BERTRANDITE(RegularCommodity.BERTRANDITE, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT
    )),
    BROMELLITE(RegularCommodity.BROMELLITE, true, List.of(
            ICY_CORE,
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    COBALT(RegularCommodity.COBALT, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT
    )),
    COLTAN(RegularCommodity.COLTAN, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT,
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT
    )),
    GALLITE(RegularCommodity.GALLITE, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT,
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    GOLD(RegularCommodity.GOLD, false, List.of(
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    GRANDIDIERITE(RegularCommodity.GRANDIDIERITE, true, List.of(
            ICY_CORE
    )),
    HYDROGENPEROXIDE(RegularCommodity.HYDROGENPEROXIDE, false, List.of(
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    INDITE(RegularCommodity.INDITE, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT,
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    LEPIDOLITE(RegularCommodity.LEPIDOLITE, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT,
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT
    )),
    LIQUIDOXYGEN(RegularCommodity.LIQUIDOXYGEN, false, List.of(
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    LITHIUMHYDROXIDE(RegularCommodity.LITHIUMHYDROXIDE, false, List.of(
            ICY_SURFACE,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    LOWTEMPERATUREDIAMOND(RegularCommodity.LOWTEMPERATUREDIAMOND, true, List.of(
            ICY_CORE,
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    METHANECLATHRATE(RegularCommodity.METHANECLATHRATE, false, List.of(
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    METHANOLMONOHYDRATECRYSTALS(RegularCommodity.METHANOLMONOHYDRATECRYSTALS, false, List.of(
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    MONAZITE(RegularCommodity.MONAZITE, true, List.of(
            ROCKY_CORE,
            METAL_RICH_CORE,
            METALLIC_CORE
    )),
    MUSGRAVITE(RegularCommodity.MUSGRAVITE, true, List.of(
            ROCKY_CORE
    )),
    OSMIUM(RegularCommodity.OSMIUM, false, List.of(
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    PAINITE(RegularCommodity.PAINITE, true, List.of(
            METAL_RICH_CORE,
            METALLIC_CORE,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    PALLADIUM(RegularCommodity.PALLADIUM, false, List.of(
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    PLATINUM(RegularCommodity.PLATINUM, true, List.of(
            METAL_RICH_CORE,
            METALLIC_CORE,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    PRASEODYMIUM(RegularCommodity.PRASEODYMIUM, false, List.of(

            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    RHODPLUMSITE(RegularCommodity.RHODPLUMSITE, true, List.of(
            ROCKY_CORE,
            METAL_RICH_CORE,
            METALLIC_CORE
    )),
    RUTILE(RegularCommodity.RUTILE, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT
    )),
    SAMARIUM(RegularCommodity.SAMARIUM, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT,
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    SERENDIBITE(RegularCommodity.SERENDIBITE, true, List.of(
            ROCKY_CORE,
            METAL_RICH_CORE,
            METALLIC_CORE
    )),
    SILVER(RegularCommodity.SILVER, false, List.of(

            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT,
            METALLIC_SURFACE,
            METALLIC_SURFACE_DEPOSIT,
            METALLIC_SUB_SURFACE_DEPOSIT
    )),
    TRITIUM(RegularCommodity.TRITIUM, true, List.of(
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    )),
    URANINITE(RegularCommodity.URANINITE, false, List.of(
            ROCKY_SURFACE,
            ROCKY_SURFACE_DEPOSIT,
            ROCKY_SUB_SURFACE_DEPOSIT,
            METAL_RICH_SURFACE,
            METAL_RICH_SURFACE_DEPOSIT,
            METAL_RICH_SUB_SURFACE_DEPOSIT
    )),
    OPAL(RegularCommodity.OPAL, true, List.of(
            ICY_CORE
    )),
    WATER(RegularCommodity.WATER, false, List.of(
            ICY_SURFACE,
            ICY_SURFACE_DEPOSIT,
            ICY_SUB_SURFACE_DEPOSIT
    ));

    private final Commodity commodity;
    private final boolean hasHotspot;
    private final List<MineableLocation> locations;

    String getLocalizationKey() {
        return "material.commodity." + this.commodity.name().toLowerCase();
    }

    String getDescriptionLocalizationKey() {
        return "material.commodity.description." + this.commodity.name().toLowerCase();
    }

    public static Optional<Mineable> getMineable(final Commodity commodity) {
        for (final Mineable mineable : Mineable.values()) {
            if (mineable.commodity.equals(commodity)) {
                return Optional.of(mineable);
            }
        }
        return Optional.empty();
    }
}
