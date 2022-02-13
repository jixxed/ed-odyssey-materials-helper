package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;

@RequiredArgsConstructor
@Getter
public enum Manufactured implements HorizonsMaterial {
    BASICCONDUCTORS(Rarity.VERY_COMMON, HorizonsMaterialType.CONDUCTIVE),
    TG_BIOMECHANICALCONDUITS(Rarity.STANDARD, HorizonsMaterialType.THARGOID),//tg_biomechanicalconduits
    BIOTECHCONDUCTORS(Rarity.VERY_RARE, HorizonsMaterialType.CONDUCTIVE),
    CHEMICALDISTILLERY(Rarity.STANDARD, HorizonsMaterialType.CHEMICAL),
    CHEMICALMANIPULATORS(Rarity.RARE, HorizonsMaterialType.CHEMICAL),
    CHEMICALPROCESSORS(Rarity.COMMON, HorizonsMaterialType.CHEMICAL),
    CHEMICALSTORAGEUNITS(Rarity.VERY_COMMON, HorizonsMaterialType.CHEMICAL),
    COMPACTCOMPOSITES(Rarity.VERY_COMMON, HorizonsMaterialType.COMPOSITE),
    COMPOUNDSHIELDING(Rarity.RARE, HorizonsMaterialType.SHIELDING),
    CONDUCTIVECERAMICS(Rarity.STANDARD, HorizonsMaterialType.CONDUCTIVE),
    CONDUCTIVECOMPONENTS(Rarity.COMMON, HorizonsMaterialType.CONDUCTIVE),
    CONDUCTIVEPOLYMERS(Rarity.RARE, HorizonsMaterialType.CONDUCTIVE),
    CONFIGURABLECOMPONENTS(Rarity.RARE, HorizonsMaterialType.MECHANICAL_COMPONENTS),
    FEDCORECOMPOSITES(Rarity.VERY_RARE, HorizonsMaterialType.COMPOSITE),//fedcorecomposites
    CRYSTALSHARDS(Rarity.VERY_COMMON, HorizonsMaterialType.CRYSTALS),
    ELECTROCHEMICALARRAYS(Rarity.STANDARD, HorizonsMaterialType.CAPACITORS),
    EXQUISITEFOCUSCRYSTALS(Rarity.VERY_RARE, HorizonsMaterialType.CRYSTALS),
    FILAMENTCOMPOSITES(Rarity.COMMON, HorizonsMaterialType.COMPOSITE),
    UNCUTFOCUSCRYSTALS(Rarity.COMMON, HorizonsMaterialType.CRYSTALS),//uncutfocuscrystals
    FOCUSCRYSTALS(Rarity.STANDARD, HorizonsMaterialType.CRYSTALS),
    GALVANISINGALLOYS(Rarity.COMMON, HorizonsMaterialType.ALLOYS),
    GRIDRESISTORS(Rarity.VERY_COMMON, HorizonsMaterialType.CAPACITORS),
    GUARDIAN_POWERCELL(Rarity.VERY_COMMON, HorizonsMaterialType.GUARDIAN),//guardian_powercell
    GUARDIAN_POWERCONDUIT(Rarity.COMMON, HorizonsMaterialType.GUARDIAN),//guardian_powerconduit
    GUARDIAN_SENTINEL_WEAPONPARTS(Rarity.STANDARD, HorizonsMaterialType.GUARDIAN),//guardian_sentinel_weaponparts
    GUARDIAN_SENTINEL_WRECKAGECOMPONENTS(Rarity.VERY_COMMON, HorizonsMaterialType.GUARDIAN),//guardian_techcomponent
    GUARDIAN_TECHCOMPONENT(Rarity.STANDARD, HorizonsMaterialType.GUARDIAN),//guardian_sentinel_wreckagecomponents
    HEATCONDUCTIONWIRING(Rarity.VERY_COMMON, HorizonsMaterialType.HEAT),
    HEATDISPERSIONPLATE(Rarity.COMMON, HorizonsMaterialType.HEAT),
    HEATEXCHANGERS(Rarity.STANDARD, HorizonsMaterialType.HEAT),
    HEATRESISTANTCERAMICS(Rarity.COMMON, HorizonsMaterialType.THERMIC),
    HEATVANES(Rarity.RARE, HorizonsMaterialType.HEAT),
    HIGHDENSITYCOMPOSITES(Rarity.STANDARD, HorizonsMaterialType.COMPOSITE),
    HYBRIDCAPACITORS(Rarity.COMMON, HorizonsMaterialType.CAPACITORS),
    IMPERIALSHIELDING(Rarity.VERY_RARE, HorizonsMaterialType.SHIELDING),
    IMPROVISEDCOMPONENTS(Rarity.VERY_RARE, HorizonsMaterialType.MECHANICAL_COMPONENTS),
    MECHANICALCOMPONENTS(Rarity.STANDARD, HorizonsMaterialType.MECHANICAL_COMPONENTS),
    MECHANICALEQUIPMENT(Rarity.COMMON, HorizonsMaterialType.MECHANICAL_COMPONENTS),
    MECHANICALSCRAP(Rarity.VERY_COMMON, HorizonsMaterialType.MECHANICAL_COMPONENTS),
    MILITARYGRADEALLOYS(Rarity.VERY_RARE, HorizonsMaterialType.THERMIC),
    MILITARYSUPERCAPACITORS(Rarity.VERY_RARE, HorizonsMaterialType.CAPACITORS),
    PHARMACEUTICALISOLATORS(Rarity.VERY_RARE, HorizonsMaterialType.CHEMICAL),
    PHASEALLOYS(Rarity.STANDARD, HorizonsMaterialType.ALLOYS),
    POLYMERCAPACITORS(Rarity.RARE, HorizonsMaterialType.CAPACITORS),
    PRECIPITATEDALLOYS(Rarity.STANDARD, HorizonsMaterialType.THERMIC),
    FEDPROPRIETARYCOMPOSITES(Rarity.RARE, HorizonsMaterialType.COMPOSITE),//fedproprietarycomposites
    TG_PROPULSIONELEMENT(Rarity.VERY_RARE, HorizonsMaterialType.THARGOID),//tg_propulsionelement
    PROTOHEATRADIATORS(Rarity.VERY_RARE, HorizonsMaterialType.HEAT),
    PROTOLIGHTALLOYS(Rarity.RARE, HorizonsMaterialType.ALLOYS),
    PROTORADIOLICALLOYS(Rarity.VERY_RARE, HorizonsMaterialType.ALLOYS),
    REFINEDFOCUSCRYSTALS(Rarity.RARE, HorizonsMaterialType.CRYSTALS),
    SALVAGEDALLOYS(Rarity.VERY_COMMON, HorizonsMaterialType.ALLOYS),
    UNKNOWNENERGYSOURCE(Rarity.VERY_RARE, HorizonsMaterialType.THARGOID),//unknownenergysource
    SHIELDEMITTERS(Rarity.COMMON, HorizonsMaterialType.SHIELDING),
    SHIELDINGSENSORS(Rarity.STANDARD, HorizonsMaterialType.SHIELDING),
    TEMPEREDALLOYS(Rarity.VERY_COMMON, HorizonsMaterialType.THERMIC),
    UNKNOWNCARAPACE(Rarity.COMMON, HorizonsMaterialType.THARGOID),
    UNKNOWNENERGYCELL(Rarity.STANDARD, HorizonsMaterialType.THARGOID),
    UNKNOWNORGANICCIRCUITRY(Rarity.VERY_RARE, HorizonsMaterialType.THARGOID),
    UNKNOWNTECHNOLOGYCOMPONENTS(Rarity.RARE, HorizonsMaterialType.THARGOID),
    THERMICALLOYS(Rarity.RARE, HorizonsMaterialType.THERMIC),
    TG_WEAPONPARTS(Rarity.RARE, HorizonsMaterialType.THARGOID),//tg_weaponparts
    WORNSHIELDEMITTERS(Rarity.VERY_COMMON, HorizonsMaterialType.SHIELDING),
    TG_WRECKAGECOMPONENTS(Rarity.STANDARD, HorizonsMaterialType.THARGOID),//tg_wreckagecomponents
    UNKNOWN(Rarity.UNKNOWN, HorizonsMaterialType.UNKNOWN);
    private final Rarity rarity;
    private final HorizonsMaterialType materialType;

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

    public static Manufactured[] materialsForTypeSorted(final HorizonsMaterialType materialType) {
        return Arrays.stream(Manufactured.values())
                .filter(manufactured -> manufactured.getMaterialType().equals(materialType))
                .sorted(Comparator.comparing(Manufactured::getRarity))
                .toList().toArray(Manufactured[]::new);
    }
}