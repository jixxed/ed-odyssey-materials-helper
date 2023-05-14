package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Manufactured implements HorizonsMaterial {
    BASICCONDUCTORS(Rarity.VERY_COMMON, HorizonsMaterialType.CONDUCTIVE, GameVersion.LEGACY),
    TG_BIOMECHANICALCONDUITS(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),//tg_biomechanicalconduits
    BIOTECHCONDUCTORS(Rarity.VERY_RARE, HorizonsMaterialType.CONDUCTIVE, GameVersion.LEGACY),
    TG_CAUSTICCRYSTAL(Rarity.RARE, HorizonsMaterialType.THARGOID),//
    TG_CAUSTICSHARD(Rarity.COMMON, HorizonsMaterialType.THARGOID),//
    CHEMICALDISTILLERY(Rarity.STANDARD, HorizonsMaterialType.CHEMICAL, GameVersion.LEGACY),
    CHEMICALMANIPULATORS(Rarity.RARE, HorizonsMaterialType.CHEMICAL, GameVersion.LEGACY),
    CHEMICALPROCESSORS(Rarity.COMMON, HorizonsMaterialType.CHEMICAL, GameVersion.LEGACY),
    CHEMICALSTORAGEUNITS(Rarity.VERY_COMMON, HorizonsMaterialType.CHEMICAL, GameVersion.LEGACY),
    COMPACTCOMPOSITES(Rarity.VERY_COMMON, HorizonsMaterialType.COMPOSITE, GameVersion.LEGACY),
    COMPOUNDSHIELDING(Rarity.RARE, HorizonsMaterialType.SHIELDING, GameVersion.LEGACY),
    CONDUCTIVECERAMICS(Rarity.STANDARD, HorizonsMaterialType.CONDUCTIVE, GameVersion.LEGACY),
    CONDUCTIVECOMPONENTS(Rarity.COMMON, HorizonsMaterialType.CONDUCTIVE, GameVersion.LEGACY),
    CONDUCTIVEPOLYMERS(Rarity.RARE, HorizonsMaterialType.CONDUCTIVE, GameVersion.LEGACY),
    CONFIGURABLECOMPONENTS(Rarity.RARE, HorizonsMaterialType.MECHANICAL_COMPONENTS, GameVersion.LEGACY),
    TG_CAUSTICGENERATORPARTS(Rarity.STANDARD, HorizonsMaterialType.THARGOID),//
    FEDCORECOMPOSITES(Rarity.VERY_RARE, HorizonsMaterialType.COMPOSITE, GameVersion.LEGACY),//fedcorecomposites
    CRYSTALSHARDS(Rarity.VERY_COMMON, HorizonsMaterialType.CRYSTALS, GameVersion.LEGACY),
    ELECTROCHEMICALARRAYS(Rarity.STANDARD, HorizonsMaterialType.CAPACITORS, GameVersion.LEGACY),
    EXQUISITEFOCUSCRYSTALS(Rarity.VERY_RARE, HorizonsMaterialType.CRYSTALS, GameVersion.LEGACY),
    FILAMENTCOMPOSITES(Rarity.COMMON, HorizonsMaterialType.COMPOSITE, GameVersion.LEGACY),
    UNCUTFOCUSCRYSTALS(Rarity.COMMON, HorizonsMaterialType.CRYSTALS, GameVersion.LEGACY),//uncutfocuscrystals
    FOCUSCRYSTALS(Rarity.STANDARD, HorizonsMaterialType.CRYSTALS, GameVersion.LEGACY),
    GALVANISINGALLOYS(Rarity.COMMON, HorizonsMaterialType.ALLOYS, GameVersion.LEGACY),
    GRIDRESISTORS(Rarity.VERY_COMMON, HorizonsMaterialType.CAPACITORS, GameVersion.LEGACY),
    GUARDIAN_POWERCELL(Rarity.VERY_COMMON, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//guardian_powercell
    GUARDIAN_POWERCONDUIT(Rarity.COMMON, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//guardian_powerconduit
    GUARDIAN_SENTINEL_WEAPONPARTS(Rarity.STANDARD, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//guardian_sentinel_weaponparts
    GUARDIAN_SENTINEL_WRECKAGECOMPONENTS(Rarity.VERY_COMMON, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//guardian_techcomponent
    GUARDIAN_TECHCOMPONENT(Rarity.STANDARD, HorizonsMaterialType.GUARDIAN, GameVersion.LEGACY),//guardian_sentinel_wreckagecomponents
    HEATCONDUCTIONWIRING(Rarity.VERY_COMMON, HorizonsMaterialType.HEAT, GameVersion.LEGACY),
    HEATDISPERSIONPLATE(Rarity.COMMON, HorizonsMaterialType.HEAT, GameVersion.LEGACY),
    HEATEXCHANGERS(Rarity.STANDARD, HorizonsMaterialType.HEAT, GameVersion.LEGACY),
    HEATRESISTANTCERAMICS(Rarity.COMMON, HorizonsMaterialType.THERMIC, GameVersion.LEGACY),
    HEATVANES(Rarity.RARE, HorizonsMaterialType.HEAT, GameVersion.LEGACY),
    HIGHDENSITYCOMPOSITES(Rarity.STANDARD, HorizonsMaterialType.COMPOSITE, GameVersion.LEGACY),
    HYBRIDCAPACITORS(Rarity.COMMON, HorizonsMaterialType.CAPACITORS, GameVersion.LEGACY),
    IMPERIALSHIELDING(Rarity.VERY_RARE, HorizonsMaterialType.SHIELDING, GameVersion.LEGACY),
    IMPROVISEDCOMPONENTS(Rarity.VERY_RARE, HorizonsMaterialType.MECHANICAL_COMPONENTS, GameVersion.LEGACY),
    MECHANICALCOMPONENTS(Rarity.STANDARD, HorizonsMaterialType.MECHANICAL_COMPONENTS, GameVersion.LEGACY),
    MECHANICALEQUIPMENT(Rarity.COMMON, HorizonsMaterialType.MECHANICAL_COMPONENTS, GameVersion.LEGACY),
    MECHANICALSCRAP(Rarity.VERY_COMMON, HorizonsMaterialType.MECHANICAL_COMPONENTS, GameVersion.LEGACY),
    MILITARYGRADEALLOYS(Rarity.VERY_RARE, HorizonsMaterialType.THERMIC, GameVersion.LEGACY),
    MILITARYSUPERCAPACITORS(Rarity.VERY_RARE, HorizonsMaterialType.CAPACITORS, GameVersion.LEGACY),
    PHARMACEUTICALISOLATORS(Rarity.VERY_RARE, HorizonsMaterialType.CHEMICAL, GameVersion.LEGACY),
    PHASEALLOYS(Rarity.STANDARD, HorizonsMaterialType.ALLOYS, GameVersion.LEGACY),
    POLYMERCAPACITORS(Rarity.RARE, HorizonsMaterialType.CAPACITORS, GameVersion.LEGACY),
    PRECIPITATEDALLOYS(Rarity.STANDARD, HorizonsMaterialType.THERMIC, GameVersion.LEGACY),
    FEDPROPRIETARYCOMPOSITES(Rarity.RARE, HorizonsMaterialType.COMPOSITE, GameVersion.LEGACY),//fedproprietarycomposites
    TG_PROPULSIONELEMENT(Rarity.VERY_RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),//tg_propulsionelement
    PROTOHEATRADIATORS(Rarity.VERY_RARE, HorizonsMaterialType.HEAT, GameVersion.LEGACY),
    PROTOLIGHTALLOYS(Rarity.RARE, HorizonsMaterialType.ALLOYS, GameVersion.LEGACY),
    PROTORADIOLICALLOYS(Rarity.VERY_RARE, HorizonsMaterialType.ALLOYS, GameVersion.LEGACY),
    REFINEDFOCUSCRYSTALS(Rarity.RARE, HorizonsMaterialType.CRYSTALS, GameVersion.LEGACY),
    SALVAGEDALLOYS(Rarity.VERY_COMMON, HorizonsMaterialType.ALLOYS, GameVersion.LEGACY),
    UNKNOWNENERGYSOURCE(Rarity.VERY_RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),//unknownenergysource
    SHIELDEMITTERS(Rarity.COMMON, HorizonsMaterialType.SHIELDING, GameVersion.LEGACY),
    SHIELDINGSENSORS(Rarity.STANDARD, HorizonsMaterialType.SHIELDING, GameVersion.LEGACY),
    TEMPEREDALLOYS(Rarity.VERY_COMMON, HorizonsMaterialType.THERMIC, GameVersion.LEGACY),
    UNKNOWNCARAPACE(Rarity.COMMON, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    UNKNOWNENERGYCELL(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    UNKNOWNORGANICCIRCUITRY(Rarity.VERY_RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    UNKNOWNTECHNOLOGYCOMPONENTS(Rarity.RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),
    UNKNOWNCORECHIP(Rarity.COMMON,HorizonsMaterialType.THARGOID),
    THERMICALLOYS(Rarity.RARE, HorizonsMaterialType.THERMIC, GameVersion.LEGACY),
    TG_WEAPONPARTS(Rarity.RARE, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),//tg_weaponparts

    TG_ABRASION01(Rarity.VERY_RARE, HorizonsMaterialType.THARGOID),

    TG_ABRASION02(Rarity.STANDARD, HorizonsMaterialType.THARGOID),
    TG_ABRASION03(Rarity.VERY_COMMON, HorizonsMaterialType.THARGOID),
    WORNSHIELDEMITTERS(Rarity.VERY_COMMON, HorizonsMaterialType.SHIELDING, GameVersion.LEGACY),
    TG_WRECKAGECOMPONENTS(Rarity.STANDARD, HorizonsMaterialType.THARGOID, GameVersion.LEGACY),//tg_wreckagecomponents
    UNKNOWN(Rarity.UNKNOWN, HorizonsMaterialType.UNKNOWN, GameVersion.LEGACY);
    private final Rarity rarity;
    private final HorizonsMaterialType materialType;
    private final GameVersion gameVersion;

    Manufactured(final Rarity rarity, final HorizonsMaterialType materialType) {
        this.rarity = rarity;
        this.materialType = materialType;
        this.gameVersion = GameVersion.LIVE;
    }

    @Override
    public String getLocalizationKey() {
        return "material.manufactured." + this.name().toLowerCase();
    }

    @Override
    public boolean isUnknown() {
        return this == Manufactured.UNKNOWN;
    }

    public static Manufactured forName(final String name) {
        try {
            return Manufactured.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {
            return Manufactured.UNKNOWN;
        }
    }

    public static Manufactured[] materialsForType(final HorizonsMaterialType materialType) {
        return Arrays.stream(Manufactured.values())
                .filter(manufactured -> manufactured.getMaterialType().equals(materialType))
                .toList().toArray(Manufactured[]::new);
    }

    @Override
    public HorizonsStorageType getStorageType() {
        return HorizonsStorageType.MANUFACTURED;
    }

}