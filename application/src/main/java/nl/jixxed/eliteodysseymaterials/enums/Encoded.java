package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Encoded implements EngineeringMaterial {
    SHIELDPATTERNANALYSIS(Rarity.RARE, HorizonsMaterialType.SHIELD_DATA, GameVersion.LEGACY),
    COMPACTEMISSIONSDATA(Rarity.VERY_RARE, HorizonsMaterialType.EMISSION_DATA, GameVersion.LEGACY),
    ADAPTIVEENCRYPTORS(Rarity.VERY_RARE, HorizonsMaterialType.ENCRYPTION_FILES, GameVersion.LEGACY),
    BULKSCANDATA(Rarity.VERY_COMMON, HorizonsMaterialType.DATA_ARCHIVES, GameVersion.LEGACY),
    FSDTELEMETRY(Rarity.COMMON, HorizonsMaterialType.WAKE_SCANS, GameVersion.LEGACY),
    DISRUPTEDWAKEECHOES(Rarity.VERY_COMMON, HorizonsMaterialType.WAKE_SCANS, GameVersion.LEGACY),
    ENCRYPTIONARCHIVES(Rarity.RARE, HorizonsMaterialType.ENCRYPTION_FILES, GameVersion.LEGACY),
    SCANDATABANKS(Rarity.STANDARD, HorizonsMaterialType.DATA_ARCHIVES, GameVersion.LEGACY),
    CLASSIFIEDSCANDATA(Rarity.VERY_RARE, HorizonsMaterialType.DATA_ARCHIVES, GameVersion.LEGACY),
    INDUSTRIALFIRMWARE(Rarity.STANDARD, HorizonsMaterialType.ENCODED_FIRMWARE, GameVersion.LEGACY),
    DATAMINEDWAKE(Rarity.VERY_RARE, HorizonsMaterialType.WAKE_SCANS, GameVersion.LEGACY),
    DECODEDEMISSIONDATA(Rarity.RARE, HorizonsMaterialType.EMISSION_DATA, GameVersion.LEGACY),
    SHIELDCYCLERECORDINGS(Rarity.VERY_COMMON, HorizonsMaterialType.SHIELD_DATA, GameVersion.LEGACY),
    ENCODEDSCANDATA(Rarity.RARE, HorizonsMaterialType.DATA_ARCHIVES, GameVersion.LEGACY),
    HYPERSPACETRAJECTORIES(Rarity.RARE, HorizonsMaterialType.WAKE_SCANS, GameVersion.LEGACY),
    SCRAMBLEDEMISSIONDATA(Rarity.VERY_COMMON, HorizonsMaterialType.EMISSION_DATA, GameVersion.LEGACY),
    GUARDIAN_MODULEBLUEPRINT(Rarity.VERY_RARE, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),
    GUARDIAN_VESSELBLUEPRINT(Rarity.VERY_RARE, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),
    GUARDIAN_WEAPONBLUEPRINT(Rarity.VERY_RARE, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),
    SHIELDSOAKANALYSIS(Rarity.COMMON, HorizonsMaterialType.SHIELD_DATA, GameVersion.LEGACY),
    ARCHIVEDEMISSIONDATA(Rarity.COMMON, HorizonsMaterialType.EMISSION_DATA, GameVersion.LEGACY),
    CONSUMERFIRMWARE(Rarity.COMMON, HorizonsMaterialType.ENCODED_FIRMWARE, GameVersion.LEGACY),
    EMBEDDEDFIRMWARE(Rarity.VERY_RARE, HorizonsMaterialType.ENCODED_FIRMWARE, GameVersion.LEGACY),
    SYMMETRICKEYS(Rarity.STANDARD, HorizonsMaterialType.ENCRYPTION_FILES, GameVersion.LEGACY),
    ANCIENTBIOLOGICALDATA(Rarity.STANDARD, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//Pattern Alpha Obelisk Data
    ANCIENTCULTURALDATA(Rarity.COMMON, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//Pattern Beta Obelisk Data
    ANCIENTLANGUAGEDATA(Rarity.RARE, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//Pattern Delta Obelisk Data
    ANCIENTTECHNOLOGICALDATA(Rarity.RARE, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//Pattern Epsilon Obelisk Data
    ANCIENTHISTORICALDATA(Rarity.VERY_COMMON, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//Pattern Gamma Obelisk Data
    SHIELDFREQUENCYDATA(Rarity.VERY_RARE, HorizonsMaterialType.SHIELD_DATA, GameVersion.LEGACY),
    SECURITYFIRMWARE(Rarity.RARE, HorizonsMaterialType.ENCODED_FIRMWARE, GameVersion.LEGACY),
    TG_INTERDICTIONDATA(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LIVE),
    TG_SHUTDOWNDATA(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LIVE),
    TG_SHIPFLIGHTDATA(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    TG_SHIPSYSTEMSDATA(Rarity.RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    LEGACYFIRMWARE(Rarity.VERY_COMMON, HorizonsMaterialType.ENCODED_FIRMWARE, GameVersion.LEGACY),
    WAKESOLUTIONS(Rarity.STANDARD, HorizonsMaterialType.WAKE_SCANS, GameVersion.LEGACY),
    ENCRYPTIONCODES(Rarity.COMMON, HorizonsMaterialType.ENCRYPTION_FILES, GameVersion.LEGACY),
    TG_COMPOSITIONDATA(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    TG_RESIDUEDATA(Rarity.RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    UNKNOWNSHIPSIGNATURE(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    TG_STRUCTURALDATA(Rarity.COMMON, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    UNKNOWNWAKEDATA(Rarity.RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    EMISSIONDATA(Rarity.STANDARD, HorizonsMaterialType.EMISSION_DATA, GameVersion.LEGACY),
    SCANARCHIVES(Rarity.COMMON, HorizonsMaterialType.DATA_ARCHIVES, GameVersion.LEGACY),
    SHIELDDENSITYREPORTS(Rarity.STANDARD, HorizonsMaterialType.SHIELD_DATA, GameVersion.LEGACY),
    ENCRYPTEDFILES(Rarity.VERY_COMMON, HorizonsMaterialType.ENCRYPTION_FILES, GameVersion.LEGACY),
    UNKNOWN(Rarity.UNKNOWN, HorizonsMaterialType.UNKNOWN, GameVersion.LEGACY);

    private final Rarity rarity;
    private final HorizonsMaterialType materialType;
    private final GameVersion gameVersion;

    Encoded(final Rarity rarity, final HorizonsMaterialType materialType) {
        this.rarity = rarity;
        this.materialType = materialType;
        this.gameVersion = GameVersion.LIVE;
    }

    @Override
    public String getLocalizationKey() {
        return "material.encoded." + this.name().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Encoded.UNKNOWN;
    }

    public static Encoded forName(final String name) {
        try {
            return Encoded.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Encoded.UNKNOWN;
        }
    }

    public static Encoded[] materialsForType(final HorizonsMaterialType materialType) {
        return Arrays.stream(Encoded.values())
                .filter(encoded -> encoded.getMaterialType().equals(materialType))
                .toList().toArray(Encoded[]::new);
    }

    @Override
    public int getMaxAmount() {
        if (this == GUARDIAN_VESSELBLUEPRINT) {
            return 100;// might actually be bugged like raw materials, but too much to verify
        } else if (HorizonsMaterialType.GUARDIAN.equals(this.materialType)) {
            return 150;
        }
        return this.getRarity().getMaxAmount();
    }

    @Override
    public HorizonsStorageType getStorageType() {
        return HorizonsStorageType.ENCODED;
    }
}
