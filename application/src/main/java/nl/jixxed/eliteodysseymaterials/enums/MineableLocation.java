package nl.jixxed.eliteodysseymaterials.enums;

public enum MineableLocation {
    ROCKY_CORE,
    ROCKY_SURFACE,
    ROCKY_SURFACE_DEPOSIT,
    ROCKY_SUB_SURFACE_DEPOSIT,
    METAL_RICH_CORE,
    METAL_RICH_SURFACE,
    METAL_RICH_SURFACE_DEPOSIT,
    METAL_RICH_SUB_SURFACE_DEPOSIT,
    METALLIC_CORE,
    METALLIC_SURFACE,
    METALLIC_SURFACE_DEPOSIT,
    METALLIC_SUB_SURFACE_DEPOSIT,
    ICY_CORE,
    ICY_SURFACE,
    ICY_SURFACE_DEPOSIT,
    ICY_SUB_SURFACE_DEPOSIT;

    public String getLocalizationKey() {
        return "material.mineable.location." + this.name().toLowerCase();
    }

}
