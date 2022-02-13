package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;

@RequiredArgsConstructor
@Getter
public enum Encoded implements HorizonsMaterial {
    SHIELDPATTERNANALYSIS(Rarity.RARE, HorizonsMaterialType.SHIELD_DATA),
    COMPACTEMISSIONSDATA(Rarity.VERY_RARE, HorizonsMaterialType.EMISSION_DATA),
    ADAPTIVEENCRYPTORS(Rarity.VERY_RARE, HorizonsMaterialType.ENCRYPTION_FILES),
    BULKSCANDATA(Rarity.VERY_COMMON, HorizonsMaterialType.DATA_ARCHIVES),
    FSDTELEMETRY(Rarity.COMMON, HorizonsMaterialType.WAKE_SCANS),
    DISRUPTEDWAKEECHOES(Rarity.VERY_COMMON, HorizonsMaterialType.WAKE_SCANS),
    ENCRYPTIONARCHIVES(Rarity.RARE, HorizonsMaterialType.ENCRYPTION_FILES),
    SCANDATABANKS(Rarity.STANDARD, HorizonsMaterialType.DATA_ARCHIVES),
    CLASSIFIEDSCANDATA(Rarity.VERY_RARE, HorizonsMaterialType.DATA_ARCHIVES),
    INDUSTRIALFIRMWARE(Rarity.STANDARD, HorizonsMaterialType.ENCODED_FIRMWARE),
    DATAMINEDWAKE(Rarity.VERY_RARE, HorizonsMaterialType.WAKE_SCANS),
    DECODEDEMISSIONDATA(Rarity.RARE, HorizonsMaterialType.EMISSION_DATA),
    SHIELDCYCLERECORDINGS(Rarity.VERY_COMMON, HorizonsMaterialType.SHIELD_DATA),
    ENCODEDSCANDATA(Rarity.RARE, HorizonsMaterialType.DATA_ARCHIVES),
    HYPERSPACETRAJECTORIES(Rarity.RARE, HorizonsMaterialType.WAKE_SCANS),
    SCRAMBLEDEMISSIONDATA(Rarity.VERY_COMMON, HorizonsMaterialType.EMISSION_DATA),
    GUARDIAN_MODULEBLUEPRINT(Rarity.VERY_RARE, HorizonsMaterialType.GUARDIAN),
    GUARDIAN_VESSELBLUEPRINT(Rarity.VERY_RARE, HorizonsMaterialType.GUARDIAN),
    GUARDIAN_WEAPONBLUEPRINT(Rarity.VERY_RARE, HorizonsMaterialType.GUARDIAN),
    SHIELDSOAKANALYSIS(Rarity.COMMON, HorizonsMaterialType.SHIELD_DATA),
    ARCHIVEDEMISSIONDATA(Rarity.COMMON, HorizonsMaterialType.EMISSION_DATA),
    CONSUMERFIRMWARE(Rarity.COMMON, HorizonsMaterialType.ENCODED_FIRMWARE),
    EMBEDDEDFIRMWARE(Rarity.VERY_RARE, HorizonsMaterialType.ENCODED_FIRMWARE),
    SYMMETRICKEYS(Rarity.STANDARD, HorizonsMaterialType.ENCRYPTION_FILES),
    ANCIENTBIOLOGICALDATA(Rarity.STANDARD, HorizonsMaterialType.GUARDIAN),//Pattern Alpha Obelisk Data
    ANCIENTCULTURALDATA(Rarity.COMMON, HorizonsMaterialType.GUARDIAN),//Pattern Beta Obelisk Data
    ANCIENTLANGUAGEDATA(Rarity.RARE, HorizonsMaterialType.GUARDIAN),//Pattern Delta Obelisk Data
    ANCIENTTECHNOLOGICALDATA(Rarity.RARE, HorizonsMaterialType.GUARDIAN),//Pattern Epsilon Obelisk Data
    ANCIENTHISTORICALDATA(Rarity.VERY_COMMON, HorizonsMaterialType.GUARDIAN),//Pattern Gamma Obelisk Data
    SHIELDFREQUENCYDATA(Rarity.VERY_RARE, HorizonsMaterialType.SHIELD_DATA),
    SECURITYFIRMWARE(Rarity.RARE, HorizonsMaterialType.ENCODED_FIRMWARE),
    TG_SHIPFLIGHTDATA(Rarity.STANDARD, HorizonsMaterialType.THARGOID),
    TG_SHIPSYSTEMSDATA(Rarity.RARE, HorizonsMaterialType.THARGOID),
    LEGACYFIRMWARE(Rarity.VERY_COMMON, HorizonsMaterialType.ENCODED_FIRMWARE),
    WAKESOLUTIONS(Rarity.STANDARD, HorizonsMaterialType.WAKE_SCANS),
    ENCRYPTIONCODES(Rarity.COMMON, HorizonsMaterialType.ENCRYPTION_FILES),
    TG_COMPOSITIONDATA(Rarity.STANDARD, HorizonsMaterialType.THARGOID),
    TG_RESIDUEDATA(Rarity.RARE, HorizonsMaterialType.THARGOID),
    UNKNOWNSHIPSIGNATURE(Rarity.STANDARD, HorizonsMaterialType.THARGOID),
    TG_STRUCTURALDATA(Rarity.COMMON, HorizonsMaterialType.THARGOID),
    UNKNOWNWAKEDATA(Rarity.RARE, HorizonsMaterialType.THARGOID),
    EMISSIONDATA(Rarity.STANDARD, HorizonsMaterialType.EMISSION_DATA),
    SCANARCHIVES(Rarity.COMMON, HorizonsMaterialType.DATA_ARCHIVES),
    SHIELDDENSITYREPORTS(Rarity.STANDARD, HorizonsMaterialType.SHIELD_DATA),
    ENCRYPTEDFILES(Rarity.VERY_COMMON, HorizonsMaterialType.ENCRYPTION_FILES),
    UNKNOWN(Rarity.UNKNOWN, HorizonsMaterialType.UNKNOWN);

    private final Rarity rarity;
    private final HorizonsMaterialType materialType;

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

    public static Encoded[] materialsForTypeSorted(final HorizonsMaterialType materialType) {
        return Arrays.stream(Encoded.values())
                .filter(encoded -> encoded.getMaterialType().equals(materialType))
                .sorted(Comparator.comparing(Encoded::getRarity))
                .toList().toArray(Encoded[]::new);
    }

    @Override
    public int getMaxAmount() {
        if (HorizonsMaterialType.GUARDIAN.equals(this.materialType) /*|| HorizonsMaterialType.THARGOID.equals(this.materialType)*/) {
            return 150;
        }
        return this.getRarity().getMaxAmount();
    }
}
