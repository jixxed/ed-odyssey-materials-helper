package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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
    COMMODITY(true),
    UNKNOWN(false);

    private final boolean tradable;

    public static HorizonsMaterialType[] getRawTypes() {
        return new HorizonsMaterialType[]{RAW_1, RAW_2, RAW_3, RAW_4, RAW_5, RAW_6, RAW_7};
    }

    public static HorizonsMaterialType[] getEncodedTypes() {
        return new HorizonsMaterialType[]{EMISSION_DATA, WAKE_SCANS, SHIELD_DATA, ENCRYPTION_FILES, DATA_ARCHIVES, ENCODED_FIRMWARE, GUARDIAN, THARGOID};
    }

    public static HorizonsMaterialType[] getManufacturedTypes() {
        return new HorizonsMaterialType[]{CHEMICAL, THERMIC, HEAT, CONDUCTIVE, MECHANICAL_COMPONENTS, CAPACITORS, SHIELDING, COMPOSITE, CRYSTALS, ALLOYS, GUARDIAN, THARGOID};
    }

    public String getLocalizationKey() {
        return "material.category." + this.name().toLowerCase();
    }

    public static HorizonsMaterialType forName(final String name) {
        return Arrays.stream(HorizonsMaterialType.values()).filter(materialType -> materialType.name().equalsIgnoreCase(name)).findFirst().orElse(HorizonsMaterialType.UNKNOWN);
    }
}
