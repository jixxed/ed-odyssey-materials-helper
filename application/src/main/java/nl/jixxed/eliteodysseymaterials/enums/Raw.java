package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Raw implements EngineeringMaterial {

    ANTIMONY(Rarity.RARE, HorizonsMaterialType.RAW_7, 51, 121.760, "Sb"),
    ARSENIC(Rarity.COMMON, HorizonsMaterialType.RAW_6, 33, 74.9216, "As"),
    BORON(Rarity.STANDARD, HorizonsMaterialType.RAW_7, 5, 10.81, "B"),
    CADMIUM(Rarity.STANDARD, HorizonsMaterialType.RAW_3, 48, 112.414, "Cd"),
    CARBON(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_1, 6, 12.011, "C"),
    CHROMIUM(Rarity.COMMON, HorizonsMaterialType.RAW_2, 24, 51.9961, "Cr"),
    GERMANIUM(Rarity.COMMON, HorizonsMaterialType.RAW_5, 32, 72.630, "Ge"),
    IRON(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_4, 26, 55.845, "Fe"),
    LEAD(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_7, 82, 207.2, "Pb"),
    MANGANESE(Rarity.COMMON, HorizonsMaterialType.RAW_3, 25, 54.9380, "Mn"),
    MERCURY(Rarity.STANDARD, HorizonsMaterialType.RAW_6, 80, 200.59, "Hg"),
    MOLYBDENUM(Rarity.STANDARD, HorizonsMaterialType.RAW_2, 42, 95.95, "Mo"),
    NICKEL(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_5, 28, 58.6934, "Ni"),
    NIOBIUM(Rarity.STANDARD, HorizonsMaterialType.RAW_1, 41, 92.9064, "Nb"),
    PHOSPHORUS(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_2, 15, 30.9738, "P"),
    POLONIUM(Rarity.RARE, HorizonsMaterialType.RAW_6, 84, 209.0, "Po"),
    RHENIUM(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_6, 75, 186.207, "Re"),
    RUTHENIUM(Rarity.RARE, HorizonsMaterialType.RAW_3, 44, 101.07, "Ru"),
    SELENIUM(Rarity.RARE, HorizonsMaterialType.RAW_4, 34, 78.971, "Se"),
    SULPHUR(Rarity.VERY_COMMON, HorizonsMaterialType.RAW_3, 16, 32.06, "S"),
    TECHNETIUM(Rarity.RARE, HorizonsMaterialType.RAW_2, 43, 98.0, "Tc"),
    TELLURIUM(Rarity.RARE, HorizonsMaterialType.RAW_5, 52, 127.60, "Te"),
    TIN(Rarity.STANDARD, HorizonsMaterialType.RAW_4, 50, 118.710, "Sn"),
    TUNGSTEN(Rarity.STANDARD, HorizonsMaterialType.RAW_5, 74, 183.84, "W"),
    VANADIUM(Rarity.COMMON, HorizonsMaterialType.RAW_1, 23, 50.9415, "V"),
    YTTRIUM(Rarity.RARE, HorizonsMaterialType.RAW_1, 39, 88.9058, "Y"),
    ZINC(Rarity.COMMON, HorizonsMaterialType.RAW_4, 30, 65.38, "Zn"),
    ZIRCONIUM(Rarity.COMMON, HorizonsMaterialType.RAW_7, 40, 91.224, "Zr"),
    UNKNOWN(Rarity.UNKNOWN, HorizonsMaterialType.UNKNOWN, 0, 0.0, "");
    private final Rarity rarity;
    private final HorizonsMaterialType materialType;
    private final GameVersion gameVersion;
    private final Integer atomicNumber;
    private final Double atomicMass;
    private final String atomicSymbol;

    Raw(final Rarity rarity, final HorizonsMaterialType materialType, Integer atomicNumber, final Double atomicMass, final String atomicSymbol) {
        this.rarity = rarity;
        this.materialType = materialType;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
        this.atomicSymbol = atomicSymbol;
        this.gameVersion = GameVersion.LEGACY;
    }
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
