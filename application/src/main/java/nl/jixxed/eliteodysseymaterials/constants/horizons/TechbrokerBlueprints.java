package nl.jixxed.eliteodysseymaterials.constants.horizons;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class TechbrokerBlueprints {
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> GUARDIAN_MODULES = Map.of(
            HorizonsBlueprintType.GUARDIAN_HYBRID_POWER_PLANT, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_HYBRID_POWER_PLANT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 18,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 21,
                            Manufactured.HEATRESISTANTCERAMICS, 15,
                            Commodity.POWERGRIDASSEMBLY, 10),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.GUARDIAN_FSD_BOOSTER, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_FSD_BOOSTER,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 21,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 21,
                            Manufactured.FOCUSCRYSTALS, 24,
                            Commodity.HNSHOCKMOUNT, 8),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.GUARDIAN_POWER_DISTRIBUTOR, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_POWER_DISTRIBUTOR,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Encoded.ANCIENTBIOLOGICALDATA, 20,
                            Manufactured.GUARDIAN_POWERCELL, 24,
                            Manufactured.PHASEALLOYS, 18,
                            Commodity.HEATSINKINTERLINK, 6),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.GUARDIAN_HULL_REINFORCEMENT, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_HULL_REINFORCEMENT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 21,
                            Encoded.ANCIENTCULTURALDATA, 16,
                            Encoded.ANCIENTHISTORICALDATA, 16,
                            Commodity.REINFORCEDMOUNTINGPLATE, 12),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.GUARDIAN_MODULE_REINFORCEMENT, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_MODULE_REINFORCEMENT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 18,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 15,
                            Manufactured.GUARDIAN_POWERCONDUIT, 20,
                            Commodity.REINFORCEDMOUNTINGPLATE, 9),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.GUARDIAN_SHIELD_REINFORCEMENT, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_SHIELD_REINFORCEMENT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 17,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 20,
                            Encoded.ANCIENTLANGUAGEDATA, 24,
                            Commodity.DIAGNOSTICSENSOR, 8),
                    Map.of(),
                    List.of()
            ));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> GUARDIAN_WEAPONS = Map.ofEntries(Map.entry(HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 18,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 20,
                            Raw.MANGANESE, 15,
                            Commodity.MAGNETICEMITTERCOIL, 6),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_LARGE, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_LARGE,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 28,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 20,
                            Raw.CHROMIUM, 28,
                            Commodity.COOLINGHOSES, 10),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 19,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 16,
                            Raw.CHROMIUM, 14,
                            Commodity.COOLINGHOSES, 8),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 15),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_LARGE, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_LARGE,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 26,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 24,
                            Raw.CHROMIUM, 26,
                            Commodity.ARTICULATIONMOTORS, 10),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 21,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 20,
                            Raw.CHROMIUM, 16,
                            Commodity.ARTICULATIONMOTORS, 8),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_LARGE, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_LARGE,
                    Map.of(
                            Manufactured.GUARDIAN_TECHCOMPONENT, 28,
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 20,
                            Raw.CARBON, 20,
                            Commodity.MICROCONTROLLERS, 18),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Manufactured.GUARDIAN_TECHCOMPONENT, 18,
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 20,
                            Raw.CARBON, 14,
                            Commodity.POWERTRANSFERCONDUITS, 12),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_LARGE, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_LARGE,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 20,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 26,
                            Raw.CARBON, 28,
                            Commodity.MICROCONTROLLERS, 12),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 16,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 20,
                            Raw.CARBON, 15,
                            Commodity.MICROCONTROLLERS, 12),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 15),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 18,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 20,
                            Raw.NIOBIUM, 15,
                            Commodity.THERMALCOOLINGUNITS, 6),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15,
                            Raw.NIOBIUM, 9),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Manufactured.GUARDIAN_TECHCOMPONENT, 18,
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 20,
                            Raw.GERMANIUM, 14,
                            Commodity.POWERCONVERTER, 12),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15,
                            Raw.GERMANIUM, 6),
                    Map.of(),
                    List.of()
            )));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> GUARDIAN_FIGHTERS = Map.of(
            HorizonsBlueprintType.JAVELIN_FIGHTER, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_FIGHTERS, HorizonsBlueprintType.JAVELIN_FIGHTER,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 25,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 18,
                            Encoded.GUARDIAN_VESSELBLUEPRINT, 1,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 26,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 25),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.LANCE_FIGHTER, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_FIGHTERS, HorizonsBlueprintType.LANCE_FIGHTER,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 25,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 18,
                            Encoded.GUARDIAN_VESSELBLUEPRINT, 1,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 26,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 25),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.TRIDENT_FIGHTER, new HorizonsBlueprint(HorizonsBlueprintName.GUARDIAN_FIGHTERS, HorizonsBlueprintType.TRIDENT_FIGHTER,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 25,
                            Encoded.GUARDIAN_VESSELBLUEPRINT, 1,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 26,
                            Encoded.ANCIENTCULTURALDATA, 18,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 25),
                    Map.of(),
                    List.of()
            ));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> HUMAN_WEAPONS = Map.ofEntries(Map.entry(HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_FIXED, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_FIXED,
                    Map.of(
                            Raw.RHENIUM, 22,
                            Raw.IRON, 30,
                            Raw.MOLYBDENUM, 24,
                            Raw.GERMANIUM, 26,
                            Commodity.CMMCOMPOSITE, 8),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_TURRETED, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_TURRETED,
                    Map.of(
                            Raw.RHENIUM, 20,
                            Raw.IRON, 28,
                            Raw.MOLYBDENUM, 28,
                            Raw.GERMANIUM, 24,
                            Commodity.ARTICULATIONMOTORS, 10),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_FIXED_LARGE, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_FIXED_LARGE,
                    Map.of(
                            Raw.VANADIUM, 28,
                            Raw.TUNGSTEN, 26,
                            Raw.RHENIUM, 24,
                            Raw.TECHNETIUM, 26,
                            Commodity.POWERCONVERTER, 8),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_FIXED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Raw.VANADIUM, 24,
                            Raw.TUNGSTEN, 26,
                            Raw.RHENIUM, 20,
                            Raw.TECHNETIUM, 28,
                            Commodity.IONDISTRIBUTOR, 6),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_FIXED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_FIXED_SMALL,
                    Map.of(
                            Raw.VANADIUM, 8,
                            Raw.TUNGSTEN, 10,
                            Raw.RHENIUM, 8,
                            Raw.TECHNETIUM, 12,
                            Commodity.POWERCONVERTER, 4),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_LARGE, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_LARGE,
                    Map.of(
                            Raw.TUNGSTEN, 24,
                            Raw.RHENIUM, 24,
                            Raw.TECHNETIUM, 22,
                            Raw.VANADIUM, 28,
                            Commodity.POWERTRANSFERCONDUITS, 12),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_MEDIUM,
                    Map.of(
                            Raw.TUNGSTEN, 22,
                            Raw.RHENIUM, 20,
                            Raw.TECHNETIUM, 28,
                            Raw.VANADIUM, 24,
                            Commodity.POWERCONVERTER, 10),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_SMALL,
                    Map.of(
                            Raw.VANADIUM, 10,
                            Raw.TUNGSTEN, 11,
                            Raw.RHENIUM, 8,
                            Raw.TECHNETIUM, 10,
                            Commodity.POWERTRANSFERCONDUITS, 4),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_TURRETED_LARGE, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_TURRETED_LARGE,
                    Map.of(
                            Raw.VANADIUM, 26,
                            Raw.TUNGSTEN, 28,
                            Raw.RHENIUM, 22,
                            Raw.TECHNETIUM, 24,
                            Commodity.IONDISTRIBUTOR, 10),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_TURRETED_MEDIUM, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_TURRETED_MEDIUM,
                    Map.of(
                            Raw.VANADIUM, 24,
                            Raw.TUNGSTEN, 22,
                            Raw.RHENIUM, 20,
                            Raw.TECHNETIUM, 28,
                            Commodity.POWERTRANSFERCONDUITS, 8),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_TURRETED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_TURRETED_SMALL,
                    Map.of(
                            Raw.VANADIUM, 8,
                            Raw.TUNGSTEN, 12,
                            Raw.RHENIUM, 10,
                            Raw.TECHNETIUM, 10,
                            Commodity.IONDISTRIBUTOR, 4),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.ENZYME_MISSILE_RACK, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.ENZYME_MISSILE_RACK,
                    Map.of(
                            Raw.TUNGSTEN, 15,
                            Manufactured.UNKNOWNENERGYCELL, 16,
                            Manufactured.UNKNOWNORGANICCIRCUITRY, 18,
                            Raw.MOLYBDENUM, 16,
                            Commodity.RADIATIONBAFFLE, 6),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.META_ALLOY_HULL_REINFORCEMENT, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.META_ALLOY_HULL_REINFORCEMENT,
                    Map.of(
                            Commodity.METAALLOYS, 16,
                            Manufactured.FOCUSCRYSTALS, 25,
                            Encoded.SHIELDPATTERNANALYSIS, 22,
                            Manufactured.CONFIGURABLECOMPONENTS, 20,
                            Commodity.REINFORCEDMOUNTINGPLATE, 12),
                    Map.of(),
                    List.of()
            )),
            Map.entry(HorizonsBlueprintType.ENGINEERED_MISSILE_RACK_V1,
                    new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.ENGINEERED_MISSILE_RACK_V1,
                            Map.of(
                                    Manufactured.PROTORADIOLICALLOYS, 16,
                                    Raw.PHOSPHORUS, 28,
                                    Commodity.OSMIUM, 10,
                                    Manufactured.CONDUCTIVECERAMICS, 24,
                                    Manufactured.HYBRIDCAPACITORS, 26),
                            Map.of(
                            ),
                            List.of(
                            ))),
            Map.entry(HorizonsBlueprintType.MODIFIED_MINING_LASER_FIXED_SMALL, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.MODIFIED_MINING_LASER_FIXED_SMALL,
                    Map.of(
                            Commodity.OSMIUM, 16,
                            Raw.ARSENIC, 20,
                            Raw.RHENIUM, 24,
                            Raw.PHOSPHORUS, 28),
                    Map.of(),
                    List.of()
            )));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> HUMAN_MODULES = Map.of(
            HorizonsBlueprintType.ENGINEERED_DETAILED_SURFACE_SCANNER_V1,
            new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.ENGINEERED_DETAILED_SURFACE_SCANNER_V1,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 26,
                            Raw.GERMANIUM, 22,
                            Manufactured.MECHANICALCOMPONENTS, 28,
                            Raw.NIOBIUM, 24),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.ENGINEERED_FSD_V1,
            new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.ENGINEERED_FSD_V1,
                    Map.of(
                            Raw.TELLURIUM, 26,
                            Manufactured.ELECTROCHEMICALARRAYS, 26,
                            Manufactured.CHEMICALPROCESSORS, 28,
                            Encoded.DATAMINEDWAKE, 18),
                    Map.of(),
                    List.of()
            ),
            HorizonsBlueprintType.CORROSION_RESISTANT_CARGO_RACK, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.CORROSION_RESISTANT_CARGO_RACK,
                    Map.of(
                            Commodity.METAALLOYS, 16,
                            Raw.IRON, 26,
                            Manufactured.CHEMICALMANIPULATORS, 18,
                            Commodity.NEOFABRICINSULATION, 12,
                            Commodity.RADIATIONBAFFLE, 22),
                    Map.of(),
                    List.of()
            ));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> HUMAN_LIVERY = Map.of(
            HorizonsBlueprintType.BOBBLEHEAD, new HorizonsBlueprint(HorizonsBlueprintName.HUMAN_LIVERY, HorizonsBlueprintType.BOBBLEHEAD,
                    Map.of(
                            Commodity.METAALLOYS, 10,
                            Commodity.THARGOIDHEART, 1),
                    Map.of(),
                    List.of()
            ));

}
