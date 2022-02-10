package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HorizonsMaterialType {
    ALLOYS(true),
    CAPACITORS(true),
    CHEMICAL(true),
    COMPOSITE(true),
    CONDUCTIVE(true),
    CRYSTALS(true),
    DATA_ARCHIVES(true),
    EMISSION_DATA(true),
    ENCODED_FIRMWARE(true),
    ENCRYPTION_FILES(true),
    GUARDIAN(false),
    HEAT(true),
    MECHANICAL_COMPONENTS(true),
    RAW_1(true),
    RAW_2(true),
    RAW_3(true),
    RAW_4(true),
    RAW_5(true),
    RAW_6(true),
    RAW_7(true),
    SHIELD_DATA(true),
    SHIELDING(true),
    THARGOID(false),
    THERMIC(true),
    WAKE_SCANS(true),
    UNKNOWN(false);

    private final boolean tradable;

    public static HorizonsMaterialType[] getRawTypes() {
        return new HorizonsMaterialType[]{RAW_1, RAW_2, RAW_3, RAW_4, RAW_5, RAW_6, RAW_7};
    }

    public static HorizonsMaterialType[] getEncodedTypes() {
        return new HorizonsMaterialType[]{SHIELD_DATA, EMISSION_DATA, ENCODED_FIRMWARE, ENCRYPTION_FILES, DATA_ARCHIVES, WAKE_SCANS, GUARDIAN, THARGOID};
    }

    public static HorizonsMaterialType[] getManufacturedTypes() {
        return new HorizonsMaterialType[]{CONDUCTIVE, COMPOSITE, CHEMICAL, CRYSTALS, SHIELDING, MECHANICAL_COMPONENTS, HEAT, THERMIC, CAPACITORS, ALLOYS, GUARDIAN, THARGOID};
    }

    public String getLocalizationKey() {
        return "material.category." + this.name().toLowerCase();
    }

}
