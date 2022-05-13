package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Raw implements HorizonsMaterial {

    ANTIMONY(Rarity.RARE, HorizonsMaterialType.RAW_7),
    ARSENIC(Rarity.COMMON, HorizonsMaterialType.RAW_6),
    BORON(Rarity.STANDARD, HorizonsMaterialType.RAW_7),
    CADMIUM(Rarity.STANDARD, HorizonsMaterialType.RAW_3),
    CARBON(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_1),
    CHROMIUM(Rarity.COMMON, HorizonsMaterialType.RAW_2),
    GERMANIUM(Rarity.COMMON, HorizonsMaterialType.RAW_5),
    IRON(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_4),
    LEAD(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_7),
    MANGANESE(Rarity.COMMON, HorizonsMaterialType.RAW_3),
    MERCURY(Rarity.STANDARD, HorizonsMaterialType.RAW_6),
    MOLYBDENUM(Rarity.STANDARD, HorizonsMaterialType.RAW_2),
    NICKEL(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_5),
    NIOBIUM(Rarity.STANDARD, HorizonsMaterialType.RAW_1),
    PHOSPHORUS(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_2),
    POLONIUM(Rarity.RARE, HorizonsMaterialType.RAW_6),
    RHENIUM(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_6),
    RUTHENIUM(Rarity.RARE, HorizonsMaterialType.RAW_3),
    SELENIUM(Rarity.RARE, HorizonsMaterialType.RAW_4),
    SULPHUR(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_3),
    TECHNETIUM(Rarity.RARE, HorizonsMaterialType.RAW_2),
    TELLURIUM(Rarity.RARE, HorizonsMaterialType.RAW_5),
    TIN(Rarity.STANDARD, HorizonsMaterialType.RAW_4),
    TUNGSTEN(Rarity.STANDARD, HorizonsMaterialType.RAW_5),
    VANADIUM(Rarity.COMMON, HorizonsMaterialType.RAW_1),
    YTTRIUM(Rarity.RARE, HorizonsMaterialType.RAW_1),
    ZINC(Rarity.COMMON, HorizonsMaterialType.RAW_4),
    ZIRCONIUM(Rarity.COMMON, HorizonsMaterialType.RAW_7),
    UNKNOWN(Rarity.UNKNOWN, HorizonsMaterialType.UNKNOWN);
    private final Rarity rarity;
    private final HorizonsMaterialType materialType;

    @Override
    public String getLocalizationKey() {
        return "material.raw." + this.name().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Raw.UNKNOWN;
    }

    public static Raw forName(final String name) {
        try {
            return Raw.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Raw.UNKNOWN;
        }
    }

    public static Raw[] materialsForType(final HorizonsMaterialType materialType) {
        return Arrays.stream(Raw.values())
                .filter(raw -> raw.getMaterialType().equals(materialType))
                .toList().toArray(Raw[]::new);
    }

    @Override
    public HorizonsStorageType getStorageType() {
        return HorizonsStorageType.RAW;
    }
}
