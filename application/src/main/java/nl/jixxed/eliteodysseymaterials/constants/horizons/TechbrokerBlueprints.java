package nl.jixxed.eliteodysseymaterials.constants.horizons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsTechBrokerBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TechbrokerBlueprints {
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> GUARDIAN_MODULES = Map.of(
            HorizonsBlueprintType.GUARDIAN_HYBRID_POWER_PLANT, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_HYBRID_POWER_PLANT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 18,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 21,
                            Manufactured.HEATRESISTANTCERAMICS, 15,
                            Commodity.POWERGRIDASSEMBLY, 10),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ),
            HorizonsBlueprintType.GUARDIAN_FSD_BOOSTER, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_FSD_BOOSTER,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 21,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 21,
                            Manufactured.FOCUSCRYSTALS, 24,
                            Commodity.HNSHOCKMOUNT, 8),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ),
            HorizonsBlueprintType.GUARDIAN_POWER_DISTRIBUTOR, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_POWER_DISTRIBUTOR,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Encoded.ANCIENTBIOLOGICALDATA, 20,
                            Manufactured.GUARDIAN_POWERCELL, 24,
                            Manufactured.PHASEALLOYS, 18,
                            Commodity.HEATSINKINTERLINK, 6),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ),
            HorizonsBlueprintType.GUARDIAN_HULL_REINFORCEMENT, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_HULL_REINFORCEMENT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 21,
                            Encoded.ANCIENTCULTURALDATA, 16,
                            Encoded.ANCIENTHISTORICALDATA, 16,
                            Commodity.REINFORCEDMOUNTINGPLATE, 12),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ),
            HorizonsBlueprintType.GUARDIAN_MODULE_REINFORCEMENT, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_MODULE_REINFORCEMENT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 18,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 15,
                            Manufactured.GUARDIAN_POWERCONDUIT, 20,
                            Commodity.REINFORCEDMOUNTINGPLATE, 9),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ),
            HorizonsBlueprintType.GUARDIAN_SHIELD_REINFORCEMENT, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_MODULES, HorizonsBlueprintType.GUARDIAN_SHIELD_REINFORCEMENT,
                    Map.of(
                            Encoded.GUARDIAN_MODULEBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 17,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 20,
                            Encoded.ANCIENTLANGUAGEDATA, 24,
                            Commodity.DIAGNOSTICSENSOR, 8),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> GUARDIAN_WEAPONS = Map.ofEntries(Map.entry(HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 18,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 20,
                            Raw.MANGANESE, 15,
                            Commodity.MAGNETICEMITTERCOIL, 6),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_GAUSS_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_LARGE, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_LARGE,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 28,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 20,
                            Raw.CHROMIUM, 28,
                            Commodity.COOLINGHOSES, 10),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 19,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 16,
                            Raw.CHROMIUM, 14,
                            Commodity.COOLINGHOSES, 8),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 15),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_LARGE, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_LARGE,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 26,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 24,
                            Raw.CHROMIUM, 26,
                            Commodity.ARTICULATIONMOTORS, 10),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_POWERCONDUIT, 21,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 20,
                            Raw.CHROMIUM, 16,
                            Commodity.ARTICULATIONMOTORS, 8),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_PLASMA_CHARGER_TURRETED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_LARGE, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_LARGE,
                    Map.of(
                            Manufactured.GUARDIAN_TECHCOMPONENT, 28,
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 20,
                            Raw.CARBON, 20,
                            Commodity.MICROCONTROLLERS, 18),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Manufactured.GUARDIAN_TECHCOMPONENT, 18,
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 20,
                            Raw.CARBON, 14,
                            Commodity.POWERTRANSFERCONDUITS, 12),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 15),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_LARGE, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_LARGE,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 20,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 26,
                            Raw.CARBON, 28,
                            Commodity.MICROCONTROLLERS, 12),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 2,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 16,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 20,
                            Raw.CARBON, 15,
                            Commodity.MICROCONTROLLERS, 12),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.GUARDIAN_SHARD_CANNON_TURRETED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 12,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 12,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 15),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 5,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 5,
                            Raw.NIOBIUM, 4,
                            Commodity.THERMALCOOLINGUNITS, 2),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_GAUSS_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 3,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 3,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 4,
                            Raw.NIOBIUM, 2),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Manufactured.GUARDIAN_TECHCOMPONENT, 5,
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 5,
                            Raw.GERMANIUM, 4,
                            Commodity.POWERCONVERTER, 2),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_SHARD_CANNON_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 3,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 3,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 4,
                            Raw.GERMANIUM, 2),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_PLASMA_CHARGER_FIXED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_PLASMA_CHARGER_FIXED_MEDIUM,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCONDUIT, 5,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 5,
                            Raw.ARSENIC, 4,
                            Commodity.POWERTRANSFERCONDUITS, 2),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.MODIFIED_PLASMA_CHARGER_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_WEAPONS, HorizonsBlueprintType.MODIFIED_PLASMA_CHARGER_FIXED_SMALL,
                    Map.of(
                            Encoded.GUARDIAN_WEAPONBLUEPRINT, 1,
                            Manufactured.GUARDIAN_POWERCELL, 3,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 3,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 4,
                            Raw.ARSENIC, 2),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SALVATION)
            )));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> GUARDIAN_FIGHTERS = Map.of(
            HorizonsBlueprintType.JAVELIN_FIGHTER, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_FIGHTERS, HorizonsBlueprintType.JAVELIN_FIGHTER,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 25,
                            Manufactured.GUARDIAN_SENTINEL_WRECKAGECOMPONENTS, 18,
                            Encoded.GUARDIAN_VESSELBLUEPRINT, 1,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 26,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 25),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ),
            HorizonsBlueprintType.LANCE_FIGHTER, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_FIGHTERS, HorizonsBlueprintType.LANCE_FIGHTER,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 25,
                            Manufactured.GUARDIAN_SENTINEL_WEAPONPARTS, 18,
                            Encoded.GUARDIAN_VESSELBLUEPRINT, 1,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 26,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 25),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ),
            HorizonsBlueprintType.TRIDENT_FIGHTER, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.GUARDIAN_FIGHTERS, HorizonsBlueprintType.TRIDENT_FIGHTER,
                    Map.of(
                            Manufactured.GUARDIAN_POWERCELL, 25,
                            Encoded.GUARDIAN_VESSELBLUEPRINT, 1,
                            Encoded.ANCIENTTECHNOLOGICALDATA, 26,
                            Encoded.ANCIENTCULTURALDATA, 18,
                            Manufactured.GUARDIAN_TECHCOMPONENT, 25),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.GUARDIAN, HorizonsBrokerType.SALVATION)
            ));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> HUMAN_WEAPONS = Map.ofEntries(Map.entry(HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_FIXED, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_FIXED,
                    Map.of(
                            Raw.RHENIUM, 22,
                            Raw.IRON, 30,
                            Raw.MOLYBDENUM, 24,
                            Raw.GERMANIUM, 26,
                            Commodity.CMMCOMPOSITE, 8),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_TURRETED, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.REMOTE_RELEASE_FLECHETTE_LAUNCHER_TURRETED,
                    Map.of(
                            Raw.RHENIUM, 20,
                            Raw.IRON, 28,
                            Raw.MOLYBDENUM, 28,
                            Raw.GERMANIUM, 24,
                            Commodity.ARTICULATIONMOTORS, 10),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_FIXED_LARGE, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_FIXED_LARGE,
                    Map.of(
                            Raw.VANADIUM, 28,
                            Raw.TUNGSTEN, 26,
                            Raw.RHENIUM, 24,
                            Raw.TECHNETIUM, 26,
                            Commodity.POWERCONVERTER, 8),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_FIXED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_FIXED_MEDIUM,
                    Map.of(
                            Raw.VANADIUM, 24,
                            Raw.TUNGSTEN, 26,
                            Raw.RHENIUM, 20,
                            Raw.TECHNETIUM, 28,
                            Commodity.IONDISTRIBUTOR, 6),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_FIXED_SMALL,
                    Map.of(
                            Raw.VANADIUM, 8,
                            Raw.TUNGSTEN, 10,
                            Raw.RHENIUM, 8,
                            Raw.TECHNETIUM, 12,
                            Commodity.POWERCONVERTER, 4),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_LARGE, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_LARGE,
                    Map.of(
                            Raw.TUNGSTEN, 24,
                            Raw.RHENIUM, 24,
                            Raw.TECHNETIUM, 22,
                            Raw.VANADIUM, 28,
                            Commodity.POWERTRANSFERCONDUITS, 12),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_MEDIUM,
                    Map.of(
                            Raw.TUNGSTEN, 22,
                            Raw.RHENIUM, 20,
                            Raw.TECHNETIUM, 28,
                            Raw.VANADIUM, 24,
                            Commodity.POWERCONVERTER, 10),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_GIMBALLED_SMALL,
                    Map.of(
                            Raw.VANADIUM, 10,
                            Raw.TUNGSTEN, 11,
                            Raw.RHENIUM, 8,
                            Raw.TECHNETIUM, 10,
                            Commodity.POWERTRANSFERCONDUITS, 4),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_TURRETED_LARGE, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_TURRETED_LARGE,
                    Map.of(
                            Raw.VANADIUM, 26,
                            Raw.TUNGSTEN, 28,
                            Raw.RHENIUM, 22,
                            Raw.TECHNETIUM, 24,
                            Commodity.IONDISTRIBUTOR, 10),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_TURRETED_MEDIUM, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_TURRETED_MEDIUM,
                    Map.of(
                            Raw.VANADIUM, 24,
                            Raw.TUNGSTEN, 22,
                            Raw.RHENIUM, 20,
                            Raw.TECHNETIUM, 28,
                            Commodity.POWERTRANSFERCONDUITS, 8),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.SHOCK_CANNON_TURRETED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.SHOCK_CANNON_TURRETED_SMALL,
                    Map.of(
                            Raw.VANADIUM, 8,
                            Raw.TUNGSTEN, 12,
                            Raw.RHENIUM, 10,
                            Raw.TECHNETIUM, 10,
                            Commodity.IONDISTRIBUTOR, 4),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.ENZYME_MISSILE_RACK, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.ENZYME_MISSILE_RACK,
                    Map.of(
                            Raw.TUNGSTEN, 15,
                            Manufactured.UNKNOWNENERGYCELL, 16,
                            Manufactured.UNKNOWNORGANICCIRCUITRY, 18,
                            Raw.MOLYBDENUM, 16,
                            Commodity.RADIATIONBAFFLE, 6),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.ENGINEERED_MISSILE_RACK_V1,
                    new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.ENGINEERED_MISSILE_RACK_V1,
                            Map.of(
                                    Manufactured.PROTORADIOLICALLOYS, 16,
                                    Raw.PHOSPHORUS, 28,
                                    Commodity.OSMIUM, 10,
                                    Manufactured.CONDUCTIVECERAMICS, 24,
                                    Manufactured.HYBRIDCAPACITORS, 26),
                            Map.of(),
                            List.of(),
                            List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS)
                    )),
            Map.entry(HorizonsBlueprintType.MODIFIED_MINING_LASER_FIXED_SMALL, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.MODIFIED_MINING_LASER_FIXED_SMALL,
                    Map.of(
                            Commodity.OSMIUM, 16,
                            Raw.ARSENIC, 20,
                            Raw.RHENIUM, 24,
                            Raw.PHOSPHORUS, 28),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.TORVAL)
            )),
            Map.entry(HorizonsBlueprintType.E2_SIRIUS_CORPORATION_AX_MISSILE_RACK, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.E2_SIRIUS_CORPORATION_AX_MISSILE_RACK,
                    Map.of(
                            Manufactured.MECHANICALEQUIPMENT, 12,
                            Manufactured.HEATDISPERSIONPLATE, 6,
                            Raw.VANADIUM, 8,
                            Manufactured.HIGHDENSITYCOMPOSITES, 5,
                            Manufactured.MILITARYSUPERCAPACITORS, 5
                    ),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.C3_SIRIUS_CORPORATION_AX_MISSILE_RACK, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.C3_SIRIUS_CORPORATION_AX_MISSILE_RACK,
                    Map.of(
                            Manufactured.MECHANICALEQUIPMENT, 20,
                            Manufactured.HEATDISPERSIONPLATE, 9,
                            Raw.VANADIUM, 13,
                            Manufactured.HIGHDENSITYCOMPOSITES, 10,
                            Raw.TECHNETIUM,3,
                            Manufactured.MILITARYSUPERCAPACITORS, 10
                    ),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SIRIUS)
            )),
            Map.entry(HorizonsBlueprintType.E2_AZIMUTH_ENHANCED_AX_MULTICANNON, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.E2_AZIMUTH_ENHANCED_AX_MULTICANNON,
                    Map.of(
                            Raw.IRON, 9,
                            Raw.ZIRCONIUM, 11,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 6,
                            Manufactured.TG_WRECKAGECOMPONENTS, 7,
                            Manufactured.TG_WEAPONPARTS, 14
                    ),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.AEGIS, HorizonsBrokerType.SALVATION)
            )),
            Map.entry(HorizonsBlueprintType.C3_AZIMUTH_ENHANCED_AX_MULTICANNON, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_WEAPONS, HorizonsBlueprintType.C3_AZIMUTH_ENHANCED_AX_MULTICANNON,
                    Map.of(
                            Raw.IRON, 11,
                            Raw.ZIRCONIUM, 16,
                            Encoded.TG_SHIPSYSTEMSDATA, 6,
                            Manufactured.TG_BIOMECHANICALCONDUITS, 9,
                            Manufactured.TG_WRECKAGECOMPONENTS, 12,
                            Manufactured.TG_WEAPONPARTS, 17
                    ),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.AEGIS, HorizonsBrokerType.SALVATION)
            )));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> HUMAN_MODULES = Map.of(
            HorizonsBlueprintType.ENGINEERED_DETAILED_SURFACE_SCANNER_V1,
            new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.ENGINEERED_DETAILED_SURFACE_SCANNER_V1,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 26,
                            Raw.GERMANIUM, 22,
                            Manufactured.MECHANICALCOMPONENTS, 28,
                            Raw.NIOBIUM, 24),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.TORVAL, HorizonsBrokerType.SIRIUS)
            ),
            HorizonsBlueprintType.ENGINEERED_FSD_V1,
            new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.ENGINEERED_FSD_V1,
                    Map.of(
                            Raw.TELLURIUM, 26,
                            Manufactured.ELECTROCHEMICALARRAYS, 26,
                            Manufactured.CHEMICALPROCESSORS, 28,
                            Encoded.DATAMINEDWAKE, 18),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.TORVAL, HorizonsBrokerType.SIRIUS)
            ),
            HorizonsBlueprintType.SIRIUS_HEAT_SINK_LAUNCHER,
            new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.SIRIUS_HEAT_SINK_LAUNCHER,
                    Map.of(
                            Manufactured.MECHANICALSCRAP, 8,
                            Raw.NIOBIUM, 6,
                            Raw.VANADIUM, 6,
                            Manufactured.MECHANICALCOMPONENTS, 5
                    ),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.SIRIUS)
            ),
            HorizonsBlueprintType.CAUSTIC_SINK_LAUNCHER,
            new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.CAUSTIC_SINK_LAUNCHER,
                    Map.of(
                            Manufactured.CHEMICALSTORAGEUNITS, 15,
                            Manufactured.GALVANISINGALLOYS, 10,
                            Manufactured.TG_CAUSTICSHARD, 20,
                            Manufactured.TG_CAUSTICGENERATORPARTS, 10,
                            Commodity.THARGOIDGENERATORTISSUESAMPLE, 5
                    ),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.AEGIS),
                    GameVersion.LIVE
            ),
            HorizonsBlueprintType.THARGOID_PULSE_NEUTRALISER,
            new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.THARGOID_PULSE_NEUTRALISER,
                    Map.of(
                            Encoded.TG_SHUTDOWNDATA, 5,
                            Manufactured.UNKNOWNENERGYSOURCE, 5,
                            Manufactured.TG_PROPULSIONELEMENT, 5,
                            Commodity.ANCIENTRELICTG, 1
                    ),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.AEGIS),
                    GameVersion.LIVE
            ),
            HorizonsBlueprintType.META_ALLOY_HULL_REINFORCEMENT, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.META_ALLOY_HULL_REINFORCEMENT,
                    Map.of(
                            Commodity.METAALLOYS, 16,
                            Manufactured.FOCUSCRYSTALS, 25,
                            Encoded.SHIELDPATTERNANALYSIS, 22,
                            Manufactured.CONFIGURABLECOMPONENTS, 20,
                            Commodity.REINFORCEDMOUNTINGPLATE, 12),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS, HorizonsBrokerType.AEGIS)
            ),
            HorizonsBlueprintType.CORROSION_RESISTANT_CARGO_RACK, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_MODULES, HorizonsBlueprintType.CORROSION_RESISTANT_CARGO_RACK,
                    Map.of(
                            Commodity.METAALLOYS, 16,
                            Raw.IRON, 26,
                            Manufactured.CHEMICALMANIPULATORS, 18,
                            Commodity.NEOFABRICINSULATION, 12,
                            Commodity.RADIATIONBAFFLE, 22),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN, HorizonsBrokerType.SIRIUS, HorizonsBrokerType.AEGIS)
            ));
    public static final Map<HorizonsBlueprintType, HorizonsBlueprint> HUMAN_LIVERY = Map.of(
            HorizonsBlueprintType.BOBBLEHEAD, new HorizonsTechBrokerBlueprint(HorizonsBlueprintName.HUMAN_LIVERY, HorizonsBlueprintType.BOBBLEHEAD,
                    Map.of(
                            Commodity.METAALLOYS, 10,
                            Commodity.THARGOIDHEART, 1),
                    Map.of(),
                    List.of(),
                    List.of(HorizonsBrokerType.HUMAN)
            ));

}
