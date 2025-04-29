package nl.jixxed.eliteodysseymaterials.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum ColonisationBuildable {
    STAR_PORT_CORIOLIS(2, ColonisationCategory.STAR_PORT, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.NO_TRUSS, ColonisationLayout.DUAL_TRUSS, ColonisationLayout.QUAD_TRUSS), Economy.COLONY, Economy.NONE, 1, 0, 1, 2, 3, 2, 1),
    STAR_PORT_ASTEROID_BASE(2, ColonisationCategory.STAR_PORT, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.ASTEROID), Economy.EXTRACTION, Economy.NONE, 1, 0, 3, 5, -4, 7, 1),
    STAR_PORT_OCELLUS(3, ColonisationCategory.STAR_PORT, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.OCELLUS), Economy.COLONY, Economy.NONE, 5, 1, 6, 7, 5, 8, 5),
    STAR_PORT_ORBIS(3, ColonisationCategory.STAR_PORT, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.APOLLO, ColonisationLayout.ARTEMIS), Economy.COLONY, Economy.NONE, 5, 1, 6, 7, 5, 8, 5),
    OUTPOST_COMMERCIAL(1, ColonisationCategory.OUTPOST, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.PLUTUS), Economy.COLONY, Economy.NONE, 0, 0, 0, 2, 5, 0, 0),
    OUTPOST_INDUSTRIAL(1, ColonisationCategory.OUTPOST, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.VULCAN), Economy.INDUSTRIAL, Economy.NONE, 0, 0, 3, 0, 0, 2, 0),
    OUTPOST_CRIMINAL(1, ColonisationCategory.OUTPOST, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.DYSNOMIA), Economy.COLONY, Economy.CONTRABAND, 0, 0, 0, 2, 0, 0, 0),
    OUTPOST_CIVILIAN(1, ColonisationCategory.OUTPOST, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.VESTA), Economy.COLONY, Economy.NONE, 0, 0, 0, 1, 1, 1, 0),
    OUTPOST_SCIENTIFIC(1, ColonisationCategory.OUTPOST, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.PROMETHEUS), Economy.HIGHTECH, Economy.NONE, 1, 0, 3, 0, 0, 0, 1),
    OUTPOST_MILITARY(1, ColonisationCategory.OUTPOST, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.NEMESIS), Economy.MILITARY, Economy.NONE, 1, 0, 0, 0, 0, 0, 1),
    INSTALLATION_SATELLITE(1, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.HERMES, ColonisationLayout.ANGELIA, ColonisationLayout.EIRENE), Economy.NONE, Economy.NONE, 0, 0, 0, 1, 1, 1, 0),
    INSTALLATION_COMMUNICATION_STATION(1, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.PISTIS, ColonisationLayout.SOTER, ColonisationLayout.ALETHEIA), Economy.NONE, Economy.NONE, 0, 0, 3, 0, 0, 0, 0),
    INSTALLATION_SPACE_FARM(1, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.DEMETER), Economy.NONE, Economy.AGRI, 0, 0, 0, 0, 5, 1, 0),
    INSTALLATION_PIRATE_BASE(1, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.APATE, ColonisationLayout.LAVERNA), Economy.NONE, Economy.CONTRABAND, 0, 0, 0, 3, 0, 0, 0),
    INSTALLATION_MINING_OUTPOST(1, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.EUTHENIA, ColonisationLayout.PHORCYS), Economy.NONE, Economy.EXTRACTION, 0, 0, 0, 3, -2, 0, 0),
    INSTALLATION_RELAY_STATION(1, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.ENODIA, ColonisationLayout.ICHNAEA), Economy.NONE, Economy.HIGHTECH, 0, 0, 0, 0, 0, 1, 0),
    INSTALLATION_MILITARY(2, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.VACUNA, ColonisationLayout.ALASTOR), Economy.NONE, Economy.MILITARY, 0, 0, 0, 0, 0, 0, 0),
    INSTALLATION_SECURITY_STATION(2, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.DICAEOSYNE, ColonisationLayout.POENA, ColonisationLayout.EUNOMIA, ColonisationLayout.NOMOS), Economy.NONE, Economy.MILITARY, 0, 0, 0, 0, 3, 2, 0),
    INSTALLATION_GOVERNMENT(2, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.HARMONIA), Economy.NONE, Economy.NONE, 0, 0, 0, 0, 6, 2, 0),
    INSTALLATION_MEDICAL(2, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.ASCLEPIUS, ColonisationLayout.EUPRAXIA), Economy.NONE, Economy.HIGHTECH, 0, 0, 3, 0, 5, 0, 0),
    INSTALLATION_RESEARCH_STATION(2, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.ASTRAEUS, ColonisationLayout.COEUS, ColonisationLayout.DODONA, ColonisationLayout.DIONE), Economy.NONE, Economy.HIGHTECH, 0, 0, 8, 0, 0, 2, 0),
    INSTALLATION_TOURIST(2, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.HEDONE, ColonisationLayout.OPORA, ColonisationLayout.PASITHEA), Economy.NONE, Economy.TOURISM, 0, 0, 0, 6, 0, 2, 0),
    INSTALLATION_SPACE_BAR(2, ColonisationCategory.INSTALLATION, ColonisationLocation.ORBITAL, List.of(ColonisationLayout.DIONYSUS, ColonisationLayout.BACCHUS), Economy.NONE, Economy.TOURISM, 0, 0, 0, 2, 3, 0, 0),
    PLANETARY_PORT_CIVILIAN_TIER_1(1, ColonisationCategory.PLANETARY_PORT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.HESTIA, ColonisationLayout.DECIMA, ColonisationLayout.ATROPOS, ColonisationLayout.NONA, ColonisationLayout.LACHESIS, ColonisationLayout.CLOTHO), Economy.COLONY, Economy.NONE, 2, 0, 0, 0, 3, 0, 2),
    PLANETARY_PORT_INDUSTRIAL(1, ColonisationCategory.PLANETARY_PORT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.HEPHAESTUS, ColonisationLayout.OPIS, ColonisationLayout.PONOS, ColonisationLayout.TETHYS, ColonisationLayout.BIA, ColonisationLayout.MEFITIS), Economy.INDUSTRIAL, Economy.NONE, 1, 0, 0, 2, 0, 0, 1),
    PLANETARY_PORT_SCIENTIFIC(1, ColonisationCategory.PLANETARY_PORT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.NECESSITAS, ColonisationLayout.ANANKE, ColonisationLayout.FAUNA, ColonisationLayout.PROVIDENTIA, ColonisationLayout.ANTEVORTA, ColonisationLayout.PORRIMA), Economy.HIGHTECH, Economy.NONE, 1, 0, 5, 0, 0, 1, 1),
    PLANETARY_PORT_CIVILIAN_TIER_3(3, ColonisationCategory.PLANETARY_PORT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.ZEUS, ColonisationLayout.HERA, ColonisationLayout.POSEIDON, ColonisationLayout.APHRODITE), Economy.COLONY, Economy.NONE, 10, 10, 5, 5, 6, 10, 10),
    SETTLEMENT_AGRICULTURE_TIER_1_SMALL(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.CONSUS), Economy.AGRI, Economy.AGRI, 0, 0, 0, 0, 3, 0, 0),
    SETTLEMENT_AGRICULTURE_TIER_1_MEDIUM(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.PICUMNUS, ColonisationLayout.ANNONA), Economy.AGRI, Economy.AGRI, 0, 0, 0, 0, 6, 0, 0),
    SETTLEMENT_AGRICULTURE_TIER_2_LARGE(2, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.CERES, ColonisationLayout.FORNAX), Economy.AGRI, Economy.AGRI, 0, 0, 0, 0, 10, 0, 0),
    SETTLEMENT_EXTRACTION_TIER_1_SMALL(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.OUREA), Economy.EXTRACTION, Economy.EXTRACTION, 0, 0, 0, 2, 0, 0, 0),
    SETTLEMENT_EXTRACTION_TIER_1_MEDIUM(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.MANTUS, ColonisationLayout.ORCUS), Economy.EXTRACTION, Economy.EXTRACTION, 0, 0, 0, 5, 0, 0, 0),
    SETTLEMENT_EXTRACTION_TIER_2_LARGE(2, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.EREBUS, ColonisationLayout.AERECURA), Economy.EXTRACTION, Economy.EXTRACTION, 0, 0, 1, 7, -2, 0, 0),
    SETTLEMENT_INDUSTRIAL_TIER_1_SMALL(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.FONTUS), Economy.INDUSTRIAL, Economy.INDUSTRIAL, 0, 0, 0, 0, 0, 2, 0),
    SETTLEMENT_INDUSTRIAL_TIER_1_MEDIUM(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.METEOPE, ColonisationLayout.PALICI, ColonisationLayout.MINTHE), Economy.INDUSTRIAL, Economy.INDUSTRIAL, 0, 0, 0, 0, 0, 5, 0),
    SETTLEMENT_INDUSTRIAL_TIER_2_LARGE(2, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.GAEA), Economy.INDUSTRIAL, Economy.INDUSTRIAL, 0, 0, 0, 2, 0, 8, 0),
    SETTLEMENT_MILITARY_TIER_1_SMALL(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.IOKE), Economy.MILITARY, Economy.MILITARY, 0, 0, 0, 0, 0, 0, 0),
    SETTLEMENT_MILITARY_TIER_1_MEDIUM(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.BELLONA, ColonisationLayout.ENYO, ColonisationLayout.POLEMOS), Economy.MILITARY, Economy.MILITARY, 0, 0, 0, 0, 0, 0, 0),
    SETTLEMENT_MILITARY_TIER_2_LARGE(2, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.MINERVA), Economy.MILITARY, Economy.MILITARY, 0, 0, 0, 0, 0, 2, 0),
    SETTLEMENT_RESEARCH_BIO_TIER_1_SMALL(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.PHEOBE), Economy.HIGHTECH, Economy.HIGHTECH, 0, 0, 3, 0, 0, 1, 0),
    SETTLEMENT_RESEARCH_BIO_TIER_1_MEDIUM(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.ASTERIA, ColonisationLayout.CAERUS), Economy.HIGHTECH, Economy.HIGHTECH, 0, 0, 6, 0, 0, 1, 0),
    SETTLEMENT_RESEARCH_BIO_TIER_2_LARGE(2, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.CHRONOS), Economy.HIGHTECH, Economy.HIGHTECH, 0, 0, 10, 0, 0, 2, 0),
    SETTLEMENT_TOURISM_TIER_1_SMALL(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.AERGIA), Economy.TOURISM, Economy.TOURISM, 0, 0, 0, 1, 0, 0, 0),
    SETTLEMENT_TOURISM_TIER_1_MEDIUM(1, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.COMOS, ColonisationLayout.GELOS), Economy.TOURISM, Economy.TOURISM, 0, 0, 0, 2, 0, 0, 0),
    SETTLEMENT_TOURISM_TIER_2_LARGE(2, ColonisationCategory.SETTLEMENT, ColonisationLocation.SURFACE, List.of(ColonisationLayout.FUFLUNS), Economy.TOURISM, Economy.TOURISM, 0, 0, 0, 5, 0, 0, 0),
    HUB_EXTRACTION(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.TARTARUS), Economy.NONE, Economy.EXTRACTION, 0, 0, 0, 10, -4, 2, 0),
    HUB_CIVILIAN(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.AEGLE), Economy.NONE, Economy.NONE, 0, 0, 0, 0, 3, 2, 0),
    HUB_EXPLORATION(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.TELLUS), Economy.NONE, Economy.TOURISM, 0, 0, 6, 0, 0, 2, 0),
    HUB_OUTPOST(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.IO), Economy.NONE, Economy.NONE, 0, 0, 0, 0, 3, 2, 0),
    HUB_SCIENTIFIC(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.ATHENA, ColonisationLayout.CAELUS), Economy.NONE, Economy.HIGHTECH, 0, 0, 10, 0, 0, 0, 0),
    HUB_MILITARY(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.ALALA, ColonisationLayout.ARES), Economy.NONE, Economy.MILITARY, 0, 0, 0, 0, 0, 0, 0),
    HUB_REFINERY(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.SILENUS), Economy.NONE, Economy.REFINERY, 0, 0, 3, 5, -2, 7, 0),
    HUB_HIGH_TECH(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.JANUS), Economy.NONE, Economy.HIGHTECH, 0, 0, 10, -2, 0, 0, 0),
    HUB_INDUSTRIAL(2, ColonisationCategory.HUB, ColonisationLocation.SURFACE, List.of(ColonisationLayout.MOLAE, ColonisationLayout.TELLUS, ColonisationLayout.EUNOSTUS), Economy.NONE, Economy.INDUSTRIAL, 0, 0, 3, 5, -4, 2, 0),
    UNKNOWN(1, ColonisationCategory.HUB, ColonisationLocation.ORBITAL, List.of(), Economy.NONE, Economy.NONE, 0, 0, 0, 0, 0, 0, 0);
    private final int tier;
    private final ColonisationCategory colonisationCategory;
    private final ColonisationLocation colonisationLocation;
    private final List<ColonisationLayout> colonisationLayouts;
    private final Economy economy;
    private final Economy systemEconomyInfluence;
    private final int initialPopulationIncrease;
    private final int maxPopulationIncrease;
    private final int security;
    private final int techLevel;
    private final int wealth;
    private final int standardOfLiving;
    private final int developmentLevel;

    public List<ColonisationBuildable> getRequirement() {
        return switch (this) {
            case INSTALLATION_MILITARY ->
                    List.of(SETTLEMENT_MILITARY_TIER_1_SMALL, SETTLEMENT_MILITARY_TIER_1_MEDIUM, SETTLEMENT_MILITARY_TIER_2_LARGE);
            case INSTALLATION_SECURITY_STATION -> List.of(INSTALLATION_RELAY_STATION);
            case INSTALLATION_RESEARCH_STATION ->
                    List.of(SETTLEMENT_RESEARCH_BIO_TIER_1_SMALL, SETTLEMENT_RESEARCH_BIO_TIER_1_MEDIUM, SETTLEMENT_RESEARCH_BIO_TIER_2_LARGE);
            case INSTALLATION_TOURIST ->
                    List.of(SETTLEMENT_TOURISM_TIER_1_SMALL, SETTLEMENT_TOURISM_TIER_1_MEDIUM, SETTLEMENT_TOURISM_TIER_2_LARGE);
            case SETTLEMENT_TOURISM_TIER_1_SMALL -> List.of(INSTALLATION_SATELLITE);
            case SETTLEMENT_TOURISM_TIER_1_MEDIUM -> List.of(INSTALLATION_SATELLITE);
            case SETTLEMENT_TOURISM_TIER_2_LARGE -> List.of(INSTALLATION_SATELLITE);
            case HUB_EXTRACTION ->
                    List.of(SETTLEMENT_EXTRACTION_TIER_1_SMALL, SETTLEMENT_EXTRACTION_TIER_1_MEDIUM, SETTLEMENT_EXTRACTION_TIER_2_LARGE);
            case HUB_CIVILIAN ->
                    List.of(SETTLEMENT_AGRICULTURE_TIER_1_SMALL, SETTLEMENT_AGRICULTURE_TIER_1_MEDIUM, SETTLEMENT_AGRICULTURE_TIER_2_LARGE);
            case HUB_EXPLORATION -> List.of(INSTALLATION_COMMUNICATION_STATION);
            case HUB_OUTPOST -> List.of(INSTALLATION_SPACE_FARM);
            case HUB_MILITARY -> List.of(INSTALLATION_MILITARY);
            case HUB_INDUSTRIAL -> List.of(INSTALLATION_MINING_OUTPOST);
            default -> Collections.emptyList();
        };
    }

    public Map<Commodity, Integer> getBlueprintCost() {
        return switch (this) {
            case UNKNOWN -> Collections.emptyMap();
            case STAR_PORT_CORIOLIS, STAR_PORT_ASTEROID_BASE -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 3781),
                    Map.entry(RegularCommodity.WATER, 1609),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 1207),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 11261),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 644),
                    Map.entry(RegularCommodity.POLYMERS, 1046),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 161),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 282),
                    Map.entry(RegularCommodity.ALUMINIUM, 10055),
                    Map.entry(RegularCommodity.COPPER, 644),
                    Map.entry(RegularCommodity.STEEL, 14076),
                    Map.entry(RegularCommodity.TITANIUM, 8205),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 145),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 25),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 242),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 145),
                    Map.entry(RegularCommodity.NONLETHALWEAPONS, 25),
                    Map.entry(RegularCommodity.POWERGENERATORS, 65),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 105)
            );
            case STAR_PORT_OCELLUS, STAR_PORT_ORBIS -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 15124),
                    Map.entry(RegularCommodity.WATER, 6436),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 4828),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 45044),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 1288),
                    Map.entry(RegularCommodity.POLYMERS, 2092),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 322),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 564),
                    Map.entry(RegularCommodity.ALUMINIUM, 40220),
                    Map.entry(RegularCommodity.COPPER, 2576),
                    Map.entry(RegularCommodity.STEEL, 56304),
                    Map.entry(RegularCommodity.TITANIUM, 32820),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 290),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 50),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 484),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 290),
                    Map.entry(RegularCommodity.NONLETHALWEAPONS, 50),
                    Map.entry(RegularCommodity.POWERGENERATORS, 130),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 210)
            );
            case OUTPOST_COMMERCIAL, OUTPOST_INDUSTRIAL, OUTPOST_CRIMINAL, OUTPOST_CIVILIAN, OUTPOST_SCIENTIFIC,
                 OUTPOST_MILITARY -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1553),
                    Map.entry(RegularCommodity.WATER, 621),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 497),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 3912),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 311),
                    Map.entry(RegularCommodity.POLYMERS, 497),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 56),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 100),
                    Map.entry(RegularCommodity.ALUMINIUM, 515),
                    Map.entry(RegularCommodity.COPPER, 218),
                    Map.entry(RegularCommodity.STEEL, 5588),
                    Map.entry(RegularCommodity.TITANIUM, 4843),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 50),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 13),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 94),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 50),
                    Map.entry(RegularCommodity.NONLETHALWEAPONS, 13),
                    Map.entry(RegularCommodity.POWERGENERATORS, 19),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 38)
            );
            case INSTALLATION_SATELLITE, INSTALLATION_COMMUNICATION_STATION -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 678),
                    Map.entry(RegularCommodity.WATER, 22),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 85),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 106),
                    Map.entry(RegularCommodity.POLYMERS, 170),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 34),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 60),
                    Map.entry(RegularCommodity.ALUMINIUM, 1322),
                    Map.entry(RegularCommodity.COPPER, 85),
                    Map.entry(RegularCommodity.STEEL, 2542),
                    Map.entry(RegularCommodity.TITANIUM, 1525),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 22),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 26),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 9),
                    Map.entry(RegularCommodity.POWERGENERATORS, 9),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 13),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 13)
            );
            case INSTALLATION_SPACE_FARM -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1071),
                    Map.entry(RegularCommodity.WATER, 173),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 77),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 192),
                    Map.entry(RegularCommodity.POLYMERS, 230),
                    Map.entry(RegularCommodity.ALUMINIUM, 842),
                    Map.entry(RegularCommodity.COPPER, 46),
                    Map.entry(RegularCommodity.STEEL, 1912),
                    Map.entry(RegularCommodity.TITANIUM, 1453),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 12),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 46),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 69),
                    Map.entry(RegularCommodity.POWERGENERATORS, 8),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 31),
                    Map.entry(RegularCommodity.GRAIN, 92),
                    Map.entry(RegularCommodity.PESTICIDES, 123),
                    Map.entry(RegularCommodity.AGRICULTURALMEDICINES, 115),
                    Map.entry(RegularCommodity.CROPHARVESTERS, 77),
                    Map.entry(RegularCommodity.BIOWASTE, 153)
            );
            case INSTALLATION_PIRATE_BASE -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 911),
                    Map.entry(RegularCommodity.WATER, 65),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 95),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 95),
                    Map.entry(RegularCommodity.POLYMERS, 190),
                    Map.entry(RegularCommodity.ALUMINIUM, 1233),
                    Map.entry(RegularCommodity.COPPER, 57),
                    Map.entry(RegularCommodity.STEEL, 1707),
                    Map.entry(RegularCommodity.TITANIUM, 1594),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 19),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 61),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 16),
                    Map.entry(RegularCommodity.POWERGENERATORS, 8),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 16),
                    Map.entry(RegularCommodity.BEER, 380),
                    Map.entry(RegularCommodity.LIQUOR, 228),
                    Map.entry(RegularCommodity.BATTLEWEAPONS, 23),
                    Map.entry(RegularCommodity.REACTIVEARMOUR, 23)
            );
            case INSTALLATION_MINING_OUTPOST -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1028),
                    Map.entry(RegularCommodity.WATER, 73),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 86),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 129),
                    Map.entry(RegularCommodity.POLYMERS, 215),
                    Map.entry(RegularCommodity.ALUMINIUM, 921),
                    Map.entry(RegularCommodity.COPPER, 52),
                    Map.entry(RegularCommodity.STEEL, 2141),
                    Map.entry(RegularCommodity.TITANIUM, 1628),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 13),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 52),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 18),
                    Map.entry(RegularCommodity.POWERGENERATORS, 9),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 22),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 13),
                    Map.entry(RegularCommodity.ROBOTICS, 60),
                    Map.entry(RegularCommodity.RESONATINGSEPARATORS, 22),
                    Map.entry(RegularCommodity.BIOREDUCINGLICHEN, 172),
                    Map.entry(RegularCommodity.GEOLOGICALEQUIPMENT, 26),
                    Map.entry(RegularCommodity.MUTOMIMAGER, 43)
            );
            case INSTALLATION_RELAY_STATION -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 780),
                    Map.entry(RegularCommodity.WATER, 25),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 98),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 122),
                    Map.entry(RegularCommodity.POLYMERS, 195),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 39),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 59),
                    Map.entry(RegularCommodity.ALUMINIUM, 1033),
                    Map.entry(RegularCommodity.COPPER, 88),
                    Map.entry(RegularCommodity.STEEL, 2437),
                    Map.entry(RegularCommodity.TITANIUM, 1755),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 25),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 30),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 10),
                    Map.entry(RegularCommodity.POWERGENERATORS, 10),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 15)
            );
            case INSTALLATION_MILITARY -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1424),
                    Map.entry(RegularCommodity.WATER, 238),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 238),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 356),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 178),
                    Map.entry(RegularCommodity.POLYMERS, 416),
                    Map.entry(RegularCommodity.ALUMINIUM, 1317),
                    Map.entry(RegularCommodity.COPPER, 131),
                    Map.entry(RegularCommodity.STEEL, 2966),
                    Map.entry(RegularCommodity.TITANIUM, 2373),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 42),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 107),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 48),
                    Map.entry(RegularCommodity.POWERGENERATORS, 18),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 42),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 18),
                    Map.entry(RegularCommodity.BATTLEWEAPONS, 30),
                    Map.entry(RegularCommodity.REACTIVEARMOUR, 30),
                    Map.entry(RegularCommodity.BASICMEDICINES, 24),
                    Map.entry(RegularCommodity.COMBATSTABILISERS, 48),
                    Map.entry(RegularCommodity.MILITARYGRADEFABRICS, 36)
            );
            case INSTALLATION_SECURITY_STATION -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1458),
                    Map.entry(RegularCommodity.WATER, 31),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 122),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 183),
                    Map.entry(RegularCommodity.POLYMERS, 243),
                    Map.entry(RegularCommodity.ALUMINIUM, 1774),
                    Map.entry(RegularCommodity.COPPER, 122),
                    Map.entry(RegularCommodity.STEEL, 3645),
                    Map.entry(RegularCommodity.TITANIUM, 2309),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 31),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 37),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 13),
                    Map.entry(RegularCommodity.NONLETHALWEAPONS, 19),
                    Map.entry(RegularCommodity.POWERGENERATORS, 13),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 19),
                    Map.entry(RegularCommodity.BATTLEWEAPONS, 19),
                    Map.entry(RegularCommodity.COMBATSTABILISERS, 25),
                    Map.entry(RegularCommodity.MILITARYGRADEFABRICS, 19)
            );
            case INSTALLATION_GOVERNMENT -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1479),
                    Map.entry(RegularCommodity.WATER, 154),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 154),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 247),
                    Map.entry(RegularCommodity.POLYMERS, 370),
                    Map.entry(RegularCommodity.ALUMINIUM, 1959),
                    Map.entry(RegularCommodity.COPPER, 93),
                    Map.entry(RegularCommodity.STEEL, 3080),
                    Map.entry(RegularCommodity.TITANIUM, 2341),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 13),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 93),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 37),
                    Map.entry(RegularCommodity.NONLETHALWEAPONS, 13),
                    Map.entry(RegularCommodity.POWERGENERATORS, 13),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 31)
            );
            case INSTALLATION_MEDICAL -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1727),
                    Map.entry(RegularCommodity.WATER, 278),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 155),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 309),
                    Map.entry(RegularCommodity.POLYMERS, 371),
                    Map.entry(RegularCommodity.ALUMINIUM, 1357),
                    Map.entry(RegularCommodity.COPPER, 112),
                    Map.entry(RegularCommodity.STEEL, 3084),
                    Map.entry(RegularCommodity.TITANIUM, 2282),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 31),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 99),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 75),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 50),
                    Map.entry(RegularCommodity.POWERGENERATORS, 19),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 38),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 25),
                    Map.entry(RegularCommodity.BASICMEDICINES, 50),
                    Map.entry(RegularCommodity.COMBATSTABILISERS, 19)
            );
            case INSTALLATION_RESEARCH_STATION -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1506),
                    Map.entry(RegularCommodity.WATER, 157),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 157),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 314),
                    Map.entry(RegularCommodity.POLYMERS, 377),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 51),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 88),
                    Map.entry(RegularCommodity.ALUMINIUM, 1380),
                    Map.entry(RegularCommodity.COPPER, 126),
                    Map.entry(RegularCommodity.STEEL, 3136),
                    Map.entry(RegularCommodity.TITANIUM, 2383),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 38),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 51),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 76),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 26),
                    Map.entry(RegularCommodity.POWERGENERATORS, 13),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 38),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 13),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 32),
                    Map.entry(RegularCommodity.GEOLOGICALEQUIPMENT, 26),
                    Map.entry(RegularCommodity.MUTOMIMAGER, 38),
                    Map.entry(RegularCommodity.BASICMEDICINES, 13),
                    Map.entry(RegularCommodity.ADVANCEDCATALYSERS, 44)
            );
            case INSTALLATION_TOURIST -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1813),
                    Map.entry(RegularCommodity.WATER, 324),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 130),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 259),
                    Map.entry(RegularCommodity.POLYMERS, 324),
                    Map.entry(RegularCommodity.ALUMINIUM, 1269),
                    Map.entry(RegularCommodity.COPPER, 78),
                    Map.entry(RegularCommodity.STEEL, 2598),
                    Map.entry(RegularCommodity.TITANIUM, 1813),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 13),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 65),
                    Map.entry(RegularCommodity.POWERGENERATORS, 13),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 39),
                    Map.entry(RegularCommodity.BEER, 389),
                    Map.entry(RegularCommodity.WINE, 259),
                    Map.entry(RegularCommodity.LIQUOR, 195),
                    Map.entry(RegularCommodity.ANIMALMEAT, 78),
                    Map.entry(RegularCommodity.FISH, 39),
                    Map.entry(RegularCommodity.TEA, 259),
                    Map.entry(RegularCommodity.COFFEE, 130)
            );
            case INSTALLATION_SPACE_BAR -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 1738),
                    Map.entry(RegularCommodity.WATER, 311),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 125),
                    Map.entry(RegularCommodity.INSULATINGMEMBRANE, 249),
                    Map.entry(RegularCommodity.POLYMERS, 311),
                    Map.entry(RegularCommodity.ALUMINIUM, 1217),
                    Map.entry(RegularCommodity.COPPER, 75),
                    Map.entry(RegularCommodity.STEEL, 2483),
                    Map.entry(RegularCommodity.TITANIUM, 1738),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 13),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 38),
                    Map.entry(RegularCommodity.POWERGENERATORS, 13),
                    Map.entry(RegularCommodity.WATERPURIFIERS, 38),
                    Map.entry(RegularCommodity.BEER, 497),
                    Map.entry(RegularCommodity.WINE, 621),
                    Map.entry(RegularCommodity.LIQUOR, 311),
                    Map.entry(RegularCommodity.ANIMALMEAT, 38),
                    Map.entry(RegularCommodity.FISH, 19),
                    Map.entry(RegularCommodity.TEA, 178),
                    Map.entry(RegularCommodity.COFFEE, 63)
            );
            case PLANETARY_PORT_CIVILIAN_TIER_1, PLANETARY_PORT_INDUSTRIAL, PLANETARY_PORT_SCIENTIFIC -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 2372),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 814),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 6776),
                    Map.entry(RegularCommodity.POLYMERS, 678),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 102),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 136),
                    Map.entry(RegularCommodity.ALUMINIUM, 7047),
                    Map.entry(RegularCommodity.COPPER, 407),
                    Map.entry(RegularCommodity.STEEL, 10164),
                    Map.entry(RegularCommodity.TITANIUM, 5760),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 102),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 48),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 136),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 95),
                    Map.entry(RegularCommodity.NONLETHALWEAPONS, 34),
                    Map.entry(RegularCommodity.POWERGENERATORS, 68),
                    Map.entry(RegularCommodity.TERRAINENRICHMENTSYSTEMS, 68),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 610),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 407),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 678),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 204),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 68),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 55)
            );
            case PLANETARY_PORT_CIVILIAN_TIER_3 -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 14232),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 4884),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 40656),
                    Map.entry(RegularCommodity.POLYMERS, 2712),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 408),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 544),
                    Map.entry(RegularCommodity.ALUMINIUM, 42282),
                    Map.entry(RegularCommodity.COPPER, 2442),
                    Map.entry(RegularCommodity.STEEL, 60984),
                    Map.entry(RegularCommodity.TITANIUM, 34560),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 408),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 44),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 544),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 380),
                    Map.entry(RegularCommodity.NONLETHALWEAPONS, 136),
                    Map.entry(RegularCommodity.POWERGENERATORS, 272),
                    Map.entry(RegularCommodity.TERRAINENRICHMENTSYSTEMS, 272),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 3660),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 2442),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 2712),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 816),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 272),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 220)
            );
            case SETTLEMENT_AGRICULTURE_TIER_1_SMALL -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 224),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 28),
                    Map.entry(RegularCommodity.POLYMERS, 91),
                    Map.entry(RegularCommodity.ALUMINIUM, 559),
                    Map.entry(RegularCommodity.COPPER, 21),
                    Map.entry(RegularCommodity.STEEL, 768),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 14),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 42),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 63),
                    Map.entry(RegularCommodity.TERRAINENRICHMENTSYSTEMS, 70),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 56),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 42),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 56),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 14),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 84),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 14),
                    Map.entry(RegularCommodity.GRAIN, 77),
                    Map.entry(RegularCommodity.PESTICIDES, 126),
                    Map.entry(RegularCommodity.CROPHARVESTERS, 210),
                    Map.entry(RegularCommodity.BIOWASTE, 280)
            );
            case SETTLEMENT_AGRICULTURE_TIER_1_MEDIUM -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_AGRICULTURE_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 2);
                yield map;
            }
            case SETTLEMENT_AGRICULTURE_TIER_2_LARGE -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_AGRICULTURE_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 3);
                yield map;
            }
            case SETTLEMENT_EXTRACTION_TIER_1_SMALL -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 177),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 59),
                    Map.entry(RegularCommodity.POLYMERS, 118),
                    Map.entry(RegularCommodity.ALUMINIUM, 632),
                    Map.entry(RegularCommodity.COPPER, 30),
                    Map.entry(RegularCommodity.STEEL, 1028),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 30),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 45),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 30),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 89),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 59),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 74),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 30),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 96),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 15),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 15),
                    Map.entry(RegularCommodity.ROBOTICS, 59),
                    Map.entry(RegularCommodity.BIOREDUCINGLICHEN, 162),
                    Map.entry(RegularCommodity.GEOLOGICALEQUIPMENT, 52),
                    Map.entry(RegularCommodity.MUTOMIMAGER, 45)
            );
            case SETTLEMENT_EXTRACTION_TIER_1_MEDIUM -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_EXTRACTION_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 2);
                yield map;
            }
            case SETTLEMENT_EXTRACTION_TIER_2_LARGE -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_EXTRACTION_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 3);
                yield map;
            }
            case SETTLEMENT_INDUSTRIAL_TIER_1_SMALL -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 110),
                    Map.entry(RegularCommodity.POLYMERS, 109),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 14),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 14),
                    Map.entry(RegularCommodity.ALUMINIUM, 585),
                    Map.entry(RegularCommodity.COPPER, 28),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 41),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 28),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 136),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 110),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 136),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 42),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 56),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 28),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 28),
                    Map.entry(RegularCommodity.THERMALCOOLINGUNITS, 28),
                    Map.entry(RegularCommodity.HELIOSTATICFURNACES, 164),
                    Map.entry(RegularCommodity.MINERALEXTRACTORS, 328),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 28),
                    Map.entry(RegularCommodity.ROBOTICS, 110),
                    Map.entry(RegularCommodity.RESONATINGSEPARATORS, 28),
                    Map.entry(RegularCommodity.ADVANCEDCATALYSERS, 82)
            );
            case SETTLEMENT_INDUSTRIAL_TIER_1_MEDIUM -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_INDUSTRIAL_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 2);
                yield map;
            }
            case SETTLEMENT_INDUSTRIAL_TIER_2_LARGE -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_INDUSTRIAL_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 3);
                yield map;
            }
            case SETTLEMENT_MILITARY_TIER_1_SMALL -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 172),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 60),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 356),
                    Map.entry(RegularCommodity.POLYMERS, 119),
                    Map.entry(RegularCommodity.ALUMINIUM, 593),
                    Map.entry(RegularCommodity.COPPER, 30),
                    Map.entry(RegularCommodity.STEEL, 949),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 42),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 36),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 24),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 60),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 60),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 101),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 36),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 24),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 24),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 12),
                    Map.entry(RegularCommodity.BATTLEWEAPONS, 24),
                    Map.entry(RegularCommodity.REACTIVEARMOUR, 24),
                    Map.entry(RegularCommodity.BASICMEDICINES, 36),
                    Map.entry(RegularCommodity.COMBATSTABILISERS, 36),
                    Map.entry(RegularCommodity.MILITARYGRADEFABRICS, 24)
            );
            case SETTLEMENT_MILITARY_TIER_1_MEDIUM -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_MILITARY_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 2);
                yield map;
            }
            case SETTLEMENT_MILITARY_TIER_2_LARGE -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_MILITARY_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 3);
                yield map;
            }
            case SETTLEMENT_RESEARCH_BIO_TIER_1_SMALL -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 351),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 41),
                    Map.entry(RegularCommodity.POLYMERS, 115),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 21),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 21),
                    Map.entry(RegularCommodity.ALUMINIUM, 607),
                    Map.entry(RegularCommodity.COPPER, 34),
                    Map.entry(RegularCommodity.STEEL, 945),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 41),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 41),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 41),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 27),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 81),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 54),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 81),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 27),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 27),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 14),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 14),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 27),
                    Map.entry(RegularCommodity.GEOLOGICALEQUIPMENT, 27),
                    Map.entry(RegularCommodity.MUTOMIMAGER, 95),
                    Map.entry(RegularCommodity.BASICMEDICINES, 14),
                    Map.entry(RegularCommodity.ADVANCEDCATALYSERS, 95)
            );
            case SETTLEMENT_RESEARCH_BIO_TIER_1_MEDIUM -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_RESEARCH_BIO_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 2);
                yield map;
            }
            case SETTLEMENT_RESEARCH_BIO_TIER_2_LARGE -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_RESEARCH_BIO_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 3);
                yield map;
            }
            case SETTLEMENT_TOURISM_TIER_1_SMALL -> Map.ofEntries(
                    Map.entry(RegularCommodity.LIQUIDOXYGEN, 227),
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 43),
                    Map.entry(RegularCommodity.POLYMERS, 114),
                    Map.entry(RegularCommodity.ALUMINIUM, 509),
                    Map.entry(RegularCommodity.COPPER, 22),
                    Map.entry(RegularCommodity.STEEL, 707),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 15),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 71),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 57),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 29),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 57),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 22),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 15),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 15),
                    Map.entry(RegularCommodity.BEER, 311),
                    Map.entry(RegularCommodity.WINE, 156),
                    Map.entry(RegularCommodity.LIQUOR, 128),
                    Map.entry(RegularCommodity.ANIMALMEAT, 71),
                    Map.entry(RegularCommodity.FISH, 36),
                    Map.entry(RegularCommodity.TEA, 170),
                    Map.entry(RegularCommodity.COFFEE, 71)
            );
            case SETTLEMENT_TOURISM_TIER_1_MEDIUM -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_TOURISM_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 2);
                yield map;
            }
            case SETTLEMENT_TOURISM_TIER_2_LARGE -> {
                Map<Commodity, Integer> map = new HashMap<>(SETTLEMENT_TOURISM_TIER_1_SMALL.getBlueprintCost());
                map.replaceAll((commodity, integer) -> integer * 3);
                yield map;
            }
            case HUB_EXTRACTION -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 252),
                    Map.entry(RegularCommodity.POLYMERS, 672),
                    Map.entry(RegularCommodity.ALUMINIUM, 2352),
                    Map.entry(RegularCommodity.COPPER, 84),
                    Map.entry(RegularCommodity.STEEL, 3359),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 38),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 51),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 34),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 756),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 210),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 504),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 51),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 42),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 26),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 126),
                    Map.entry(RegularCommodity.ROBOTICS, 252),
                    Map.entry(RegularCommodity.BIOREDUCINGLICHEN, 714),
                    Map.entry(RegularCommodity.GEOLOGICALEQUIPMENT, 210),
                    Map.entry(RegularCommodity.MUTOMIMAGER, 160)
            );
            case HUB_CIVILIAN -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 126),
                    Map.entry(RegularCommodity.POLYMERS, 630),
                    Map.entry(RegularCommodity.ALUMINIUM, 2433),
                    Map.entry(RegularCommodity.COPPER, 68),
                    Map.entry(RegularCommodity.STEEL, 3775),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 21),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 68),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 42),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 588),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 84),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 336),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 68),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 42),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 38),
                    Map.entry(RegularCommodity.PESTICIDES, 378),
                    Map.entry(RegularCommodity.AGRICULTURALMEDICINES, 235),
                    Map.entry(RegularCommodity.CROPHARVESTERS, 210),
                    Map.entry(RegularCommodity.BIOWASTE, 630)
            );
            case HUB_EXPLORATION -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 134),
                    Map.entry(RegularCommodity.POLYMERS, 666),
                    Map.entry(RegularCommodity.ALUMINIUM, 2483),
                    Map.entry(RegularCommodity.COPPER, 178),
                    Map.entry(RegularCommodity.STEEL, 3547),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 49),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 54),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 36),
                    Map.entry(RegularCommodity.POWERGENERATORS, 32),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 1419),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 89),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 267),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 36),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 45),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 18),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 249),
                    Map.entry(RegularCommodity.GEOLOGICALEQUIPMENT, 621)
            );
            case HUB_OUTPOST -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 145),
                    Map.entry(RegularCommodity.ALUMINIUM, 2797),
                    Map.entry(RegularCommodity.COPPER, 78),
                    Map.entry(RegularCommodity.STEEL, 4051),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 25),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 174),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 87),
                    Map.entry(RegularCommodity.POWERGENERATORS, 25),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 1061),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 290),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 338),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 49),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 49),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 29)
            );
            case HUB_SCIENTIFIC -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 223),
                    Map.entry(RegularCommodity.POLYMERS, 712),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 72),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 72),
                    Map.entry(RegularCommodity.ALUMINIUM, 2581),
                    Map.entry(RegularCommodity.COPPER, 112),
                    Map.entry(RegularCommodity.STEEL, 3560),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 67),
                    Map.entry(RegularCommodity.MEDICALDIAGNOSTICEQUIPMENT, 161),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 54),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 36),
                    Map.entry(RegularCommodity.POWERGENERATORS, 36),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 534),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 178),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 445),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 36),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 63),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 27),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 49),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 156),
                    Map.entry(RegularCommodity.GEOLOGICALEQUIPMENT, 401),
                    Map.entry(RegularCommodity.MUTOMIMAGER, 107),
                    Map.entry(RegularCommodity.BASICMEDICINES, 72),
                    Map.entry(RegularCommodity.ADVANCEDCATALYSERS, 170)
            );
            case HUB_MILITARY -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 375),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 333),
                    Map.entry(RegularCommodity.POLYMERS, 708),
                    Map.entry(RegularCommodity.ALUMINIUM, 2414),
                    Map.entry(RegularCommodity.COPPER, 105),
                    Map.entry(RegularCommodity.STEEL, 3746),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 75),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 125),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 42),
                    Map.entry(RegularCommodity.POWERGENERATORS, 38),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 583),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 333),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 542),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 75),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 63),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 50),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 55),
                    Map.entry(RegularCommodity.BATTLEWEAPONS, 42),
                    Map.entry(RegularCommodity.REACTIVEARMOUR, 42),
                    Map.entry(RegularCommodity.BASICMEDICINES, 109),
                    Map.entry(RegularCommodity.COMBATSTABILISERS, 25),
                    Map.entry(RegularCommodity.MILITARYGRADEFABRICS, 42)
            );
            case HUB_REFINERY -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 279),
                    Map.entry(RegularCommodity.POLYMERS, 744),
                    Map.entry(RegularCommodity.ALUMINIUM, 2697),
                    Map.entry(RegularCommodity.COPPER, 93),
                    Map.entry(RegularCommodity.STEEL, 3720),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 42),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 56),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 38),
                    Map.entry(RegularCommodity.POWERGENERATORS, 28),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 558),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 233),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 465),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 56),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 47),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 28),
                    Map.entry(RegularCommodity.THERMALCOOLINGUNITS, 103),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 117),
                    Map.entry(RegularCommodity.ROBOTICS, 419),
                    Map.entry(RegularCommodity.RESONATINGSEPARATORS, 84),
                    Map.entry(RegularCommodity.ADVANCEDCATALYSERS, 112)
            );
            case HUB_HIGH_TECH -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 225),
                    Map.entry(RegularCommodity.CMMCOMPOSITE, 270),
                    Map.entry(RegularCommodity.POLYMERS, 673),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 108),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 108),
                    Map.entry(RegularCommodity.ALUMINIUM, 2512),
                    Map.entry(RegularCommodity.COPPER, 113),
                    Map.entry(RegularCommodity.STEEL, 3768),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 68),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 54),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 36),
                    Map.entry(RegularCommodity.POWERGENERATORS, 27),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 539),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 135),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 404),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 45),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 59),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 27),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 90),
                    Map.entry(RegularCommodity.THERMALCOOLINGUNITS, 211),
                    Map.entry(RegularCommodity.ROBOTICS, 449)
            );
            case HUB_INDUSTRIAL -> Map.ofEntries(
                    Map.entry(RegularCommodity.CERAMICCOMPOSITES, 249),
                    Map.entry(RegularCommodity.POLYMERS, 664),
                    Map.entry(RegularCommodity.SEMICONDUCTORS, 75),
                    Map.entry(RegularCommodity.SUPERCONDUCTORS, 75),
                    Map.entry(RegularCommodity.ALUMINIUM, 2407),
                    Map.entry(RegularCommodity.COPPER, 83),
                    Map.entry(RegularCommodity.STEEL, 3320),
                    Map.entry(RegularCommodity.COMPUTERCOMPONENTS, 38),
                    Map.entry(RegularCommodity.FOODCARTRIDGES, 50),
                    Map.entry(RegularCommodity.FRUITANDVEGETABLES, 34),
                    Map.entry(RegularCommodity.POWERGENERATORS, 25),
                    Map.entry(RegularCommodity.SURFACESTABILISERS, 498),
                    Map.entry(RegularCommodity.BUILDINGFABRICATORS, 208),
                    Map.entry(RegularCommodity.STRUCTURALREGULATORS, 498),
                    Map.entry(RegularCommodity.EVACUATIONSHELTER, 50),
                    Map.entry(RegularCommodity.EMERGENCYPOWERCELLS, 42),
                    Map.entry(RegularCommodity.SURVIVALEQUIPMENT, 25),
                    Map.entry(RegularCommodity.MICROCONTROLLERS, 50),
                    Map.entry(RegularCommodity.THERMALCOOLINGUNITS, 117),
                    Map.entry(RegularCommodity.HELIOSTATICFURNACES, 415),
                    Map.entry(RegularCommodity.MINERALEXTRACTORS, 415),
                    Map.entry(RegularCommodity.HAZARDOUSENVIRONMENTSUITS, 83),
                    Map.entry(RegularCommodity.ROBOTICS, 332)
            );
        };
    }

    public String getLocalizationKey() {
        return "colonisation.buildable." + this.name().toLowerCase();
    }
}
