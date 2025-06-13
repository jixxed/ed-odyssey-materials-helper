package nl.jixxed.eliteodysseymaterials.helper;

import nl.jixxed.eliteodysseymaterials.domain.ConstructionProgress;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.RegularCommodity;

import java.util.List;
import java.util.Map;

public class ColonisationTypeHelper {

    @SuppressWarnings("unchecked")
    public static List<ColonisationBuildable> detectBuildables(Map<Commodity, ConstructionProgress> constructionProgress) {
        final Map<Commodity, Integer> requiredMaterials = Map.ofEntries(constructionProgress.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().required()))
                .toArray(Map.Entry[]::new));
        int totalMaterials = requiredMaterials.values().stream().mapToInt(Integer::intValue).sum();
        if (isOrbital(requiredMaterials)) {
            if (isInstallation(requiredMaterials)) {
                return getInstallations(requiredMaterials);
            } else {//outpost/starport
                return getOrbitalPorts(totalMaterials);
            }
        } else {//planetary
            if (isHub(requiredMaterials)) {
                return getPlanetaryHubs(requiredMaterials);
            } else if (isPort(requiredMaterials)) {
                return getPorts(totalMaterials);
            } else {//settlement
                return getSettlements(requiredMaterials);
            }
        }

    }

    private static List<ColonisationBuildable> getInstallations(Map<Commodity, Integer> requiredMaterials) {
        if (requiredMaterials.containsKey(RegularCommodity.GRAIN)) {//unique
            return List.of(ColonisationBuildable.INSTALLATION_SPACE_FARM);
        }
        if (requiredMaterials.containsKey(RegularCommodity.ADVANCEDCATALYSERS)) {//unique
            return List.of(ColonisationBuildable.INSTALLATION_RESEARCH_STATION);
        }
        if (requiredMaterials.containsKey(RegularCommodity.BIOREDUCINGLICHEN)) {//unique
            return List.of(ColonisationBuildable.INSTALLATION_MINING_OUTPOST);
        }
        if (requiredMaterials.containsKey(RegularCommodity.WINE)) {//unique
            return List.of(ColonisationBuildable.INSTALLATION_TOURIST, ColonisationBuildable.INSTALLATION_SPACE_BAR);
        }
        if (requiredMaterials.containsKey(RegularCommodity.BEER)) {//unique if no wine
            return List.of(ColonisationBuildable.INSTALLATION_PIRATE_BASE);
        }
        if (requiredMaterials.containsKey(RegularCommodity.CMMCOMPOSITE)) {//unique
            return List.of(ColonisationBuildable.INSTALLATION_MILITARY);
        }
        if (requiredMaterials.containsKey(RegularCommodity.BASICMEDICINES)) {
            return List.of(ColonisationBuildable.INSTALLATION_MEDICAL);
        }
        if (requiredMaterials.containsKey(RegularCommodity.COMBATSTABILISERS)) {
            return List.of(ColonisationBuildable.INSTALLATION_SECURITY_STATION);
        }
        if (requiredMaterials.containsKey(RegularCommodity.MICROCONTROLLERS)) {
            return List.of(ColonisationBuildable.INSTALLATION_SATELLITE, ColonisationBuildable.INSTALLATION_COMMUNICATION_STATION);
        }
        if (requiredMaterials.containsKey(RegularCommodity.NONLETHALWEAPONS)) {
            return List.of(ColonisationBuildable.INSTALLATION_GOVERNMENT);
        }
        if (requiredMaterials.containsKey(RegularCommodity.SEMICONDUCTORS)) {
            return List.of(ColonisationBuildable.INSTALLATION_RELAY_STATION);
        }
        return List.of(
                ColonisationBuildable.INSTALLATION_SPACE_FARM,
                ColonisationBuildable.INSTALLATION_RESEARCH_STATION,
                ColonisationBuildable.INSTALLATION_MINING_OUTPOST,
                ColonisationBuildable.INSTALLATION_TOURIST,
                ColonisationBuildable.INSTALLATION_SPACE_BAR,
                ColonisationBuildable.INSTALLATION_PIRATE_BASE,
                ColonisationBuildable.INSTALLATION_MILITARY,
                ColonisationBuildable.INSTALLATION_MEDICAL,
                ColonisationBuildable.INSTALLATION_SECURITY_STATION,
                ColonisationBuildable.INSTALLATION_SATELLITE,
                ColonisationBuildable.INSTALLATION_COMMUNICATION_STATION,
                ColonisationBuildable.INSTALLATION_GOVERNMENT,
                ColonisationBuildable.INSTALLATION_RELAY_STATION
        );

    }

    private static List<ColonisationBuildable> getSettlements(Map<Commodity, Integer> requiredMaterials) {
        int totalMaterials = requiredMaterials.values().stream().mapToInt(Integer::intValue).sum();
        if (totalMaterials < 4_000) {
            return getSmallSettlements(requiredMaterials);
        } else if (totalMaterials < 7_000) {
            return getMediumSettlements(requiredMaterials);
        } else {
            return getLargeSettlements(requiredMaterials);
        }
    }

    private static List<ColonisationBuildable> getSmallSettlements(Map<Commodity, Integer> requiredMaterials) {
        if (requiredMaterials.containsKey(RegularCommodity.BIOWASTE)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_AGRICULTURE_TIER_1_SMALL);
        }
        if (requiredMaterials.containsKey(RegularCommodity.BIOREDUCINGLICHEN)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_EXTRACTION_TIER_1_SMALL);
        }
        if (requiredMaterials.containsKey(RegularCommodity.RESONATINGSEPARATORS)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_INDUSTRIAL_TIER_1_SMALL);
        }
        if (requiredMaterials.containsKey(RegularCommodity.WINE)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_TOURISM_TIER_1_SMALL);
        }
        if (requiredMaterials.containsKey(RegularCommodity.MILITARYGRADEFABRICS)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_MILITARY_TIER_1_SMALL);
        }
        if (requiredMaterials.containsKey(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_RESEARCH_BIO_TIER_1_SMALL);
        }
        return List.of(
                ColonisationBuildable.SETTLEMENT_AGRICULTURE_TIER_1_SMALL,
                ColonisationBuildable.SETTLEMENT_EXTRACTION_TIER_1_SMALL,
                ColonisationBuildable.SETTLEMENT_INDUSTRIAL_TIER_1_SMALL,
                ColonisationBuildable.SETTLEMENT_TOURISM_TIER_1_SMALL,
                ColonisationBuildable.SETTLEMENT_MILITARY_TIER_1_SMALL,
                ColonisationBuildable.SETTLEMENT_RESEARCH_BIO_TIER_1_SMALL
        );
    }

    private static List<ColonisationBuildable> getMediumSettlements(Map<Commodity, Integer> requiredMaterials) {
        if (requiredMaterials.containsKey(RegularCommodity.BIOWASTE)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_AGRICULTURE_TIER_1_MEDIUM);
        }
        if (requiredMaterials.containsKey(RegularCommodity.BIOREDUCINGLICHEN)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_EXTRACTION_TIER_1_MEDIUM);
        }
        if (requiredMaterials.containsKey(RegularCommodity.RESONATINGSEPARATORS)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_INDUSTRIAL_TIER_1_MEDIUM);
        }
        if (requiredMaterials.containsKey(RegularCommodity.WINE)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_TOURISM_TIER_1_MEDIUM);
        }
        if (requiredMaterials.containsKey(RegularCommodity.MILITARYGRADEFABRICS)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_MILITARY_TIER_1_MEDIUM);
        }
        if (requiredMaterials.containsKey(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_RESEARCH_BIO_TIER_1_MEDIUM);
        }
        return List.of(
                ColonisationBuildable.SETTLEMENT_AGRICULTURE_TIER_1_MEDIUM,
                ColonisationBuildable.SETTLEMENT_EXTRACTION_TIER_1_MEDIUM,
                ColonisationBuildable.SETTLEMENT_INDUSTRIAL_TIER_1_MEDIUM,
                ColonisationBuildable.SETTLEMENT_TOURISM_TIER_1_MEDIUM,
                ColonisationBuildable.SETTLEMENT_MILITARY_TIER_1_MEDIUM,
                ColonisationBuildable.SETTLEMENT_RESEARCH_BIO_TIER_1_MEDIUM
        );
    }

    private static List<ColonisationBuildable> getLargeSettlements(Map<Commodity, Integer> requiredMaterials) {
        if (requiredMaterials.containsKey(RegularCommodity.BIOWASTE)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_AGRICULTURE_TIER_2_LARGE);
        }
        if (requiredMaterials.containsKey(RegularCommodity.BIOREDUCINGLICHEN)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_EXTRACTION_TIER_2_LARGE);
        }
        if (requiredMaterials.containsKey(RegularCommodity.RESONATINGSEPARATORS)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_INDUSTRIAL_TIER_2_LARGE);
        }
        if (requiredMaterials.containsKey(RegularCommodity.WINE)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_TOURISM_TIER_2_LARGE);
        }
        if (requiredMaterials.containsKey(RegularCommodity.MILITARYGRADEFABRICS)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_MILITARY_TIER_2_LARGE);
        }
        if (requiredMaterials.containsKey(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT)) {//unique
            return List.of(ColonisationBuildable.SETTLEMENT_RESEARCH_BIO_TIER_2_LARGE);
        }
        return List.of(
                ColonisationBuildable.SETTLEMENT_AGRICULTURE_TIER_2_LARGE,
                ColonisationBuildable.SETTLEMENT_EXTRACTION_TIER_2_LARGE,
                ColonisationBuildable.SETTLEMENT_INDUSTRIAL_TIER_2_LARGE,
                ColonisationBuildable.SETTLEMENT_TOURISM_TIER_2_LARGE,
                ColonisationBuildable.SETTLEMENT_MILITARY_TIER_2_LARGE,
                ColonisationBuildable.SETTLEMENT_RESEARCH_BIO_TIER_2_LARGE
        );
    }


    private static List<ColonisationBuildable> getPorts(int totalMaterials) {
        if (totalMaterials > 100_000) {
            return List.of(ColonisationBuildable.PLANETARY_PORT_CIVILIAN_TIER_3);
        }
        return List.of(ColonisationBuildable.PLANETARY_PORT_CIVILIAN_TIER_1, ColonisationBuildable.PLANETARY_PORT_INDUSTRIAL, ColonisationBuildable.PLANETARY_PORT_SCIENTIFIC);
    }

    private static List<ColonisationBuildable> getPlanetaryHubs(Map<Commodity, Integer> requiredMaterials) {
        if (requiredMaterials.containsKey(RegularCommodity.PESTICIDES)) {//unique
            return List.of(ColonisationBuildable.HUB_CIVILIAN);
        }
        if (requiredMaterials.containsKey(RegularCommodity.HELIOSTATICFURNACES)) {//unique
            return List.of(ColonisationBuildable.HUB_INDUSTRIAL);
        }
        if (requiredMaterials.containsKey(RegularCommodity.COMBATSTABILISERS)) {//unique
            return List.of(ColonisationBuildable.HUB_MILITARY);
        }
        if (requiredMaterials.containsKey(RegularCommodity.RESONATINGSEPARATORS)) {//unique
            return List.of(ColonisationBuildable.HUB_REFINERY);
        }
        if (requiredMaterials.containsKey(RegularCommodity.CMMCOMPOSITE) && requiredMaterials.containsKey(RegularCommodity.SEMICONDUCTORS)) {//unique combo
            return List.of(ColonisationBuildable.HUB_HIGH_TECH);
        }
        if (!requiredMaterials.containsKey(RegularCommodity.POLYMERS)) {//unique missing
            return List.of(ColonisationBuildable.HUB_OUTPOST);
        }
        if (requiredMaterials.containsKey(RegularCommodity.SEMICONDUCTORS)) {//unique for remaining options
            return List.of(ColonisationBuildable.HUB_SCIENTIFIC);
        }
        if (requiredMaterials.containsKey(RegularCommodity.BIOREDUCINGLICHEN)) {//unique for remaining options
            return List.of(ColonisationBuildable.HUB_EXTRACTION);
        }
        if (!requiredMaterials.containsKey(RegularCommodity.MUTOMIMAGER)) {
            return List.of(ColonisationBuildable.HUB_EXPLORATION);
        }
        return List.of(
                ColonisationBuildable.HUB_CIVILIAN,
                ColonisationBuildable.HUB_INDUSTRIAL,
                ColonisationBuildable.HUB_MILITARY,
                ColonisationBuildable.HUB_REFINERY,
                ColonisationBuildable.HUB_HIGH_TECH,
                ColonisationBuildable.HUB_OUTPOST,
                ColonisationBuildable.HUB_SCIENTIFIC,
                ColonisationBuildable.HUB_EXTRACTION,
                ColonisationBuildable.HUB_EXPLORATION
        );
    }

    private static List<ColonisationBuildable> getOrbitalPorts(int totalMaterials) {
        if (totalMaterials > 100_000) {
            return List.of(ColonisationBuildable.STAR_PORT_OCELLUS, ColonisationBuildable.STAR_PORT_ORBIS);
        }
        if (totalMaterials > 40_000) {
            return List.of(ColonisationBuildable.STAR_PORT_CORIOLIS, ColonisationBuildable.STAR_PORT_ASTEROID_BASE);
        }
        return List.of(ColonisationBuildable.OUTPOST_CIVILIAN,
                ColonisationBuildable.OUTPOST_COMMERCIAL,
                ColonisationBuildable.OUTPOST_CRIMINAL,
                ColonisationBuildable.OUTPOST_INDUSTRIAL,
                ColonisationBuildable.OUTPOST_MILITARY,
                ColonisationBuildable.OUTPOST_SCIENTIFIC);
    }

    private static boolean isPort(Map<Commodity, Integer> requiredMaterials) {
        return requiredMaterials.containsKey(RegularCommodity.NONLETHALWEAPONS);
    }

    private static boolean isHub(Map<Commodity, Integer> requiredMaterials) {
        return !requiredMaterials.containsKey(RegularCommodity.LIQUIDOXYGEN);
    }

    private static boolean isInstallation(Map<Commodity, Integer> requiredMaterials) {
        return !requiredMaterials.containsKey(RegularCommodity.CMMCOMPOSITE) || !requiredMaterials.containsKey(RegularCommodity.SEMICONDUCTORS);
    }

    private static boolean isOrbital(Map<Commodity, Integer> requiredMaterials) {
        return requiredMaterials.containsKey(RegularCommodity.WATER);
    }
}
