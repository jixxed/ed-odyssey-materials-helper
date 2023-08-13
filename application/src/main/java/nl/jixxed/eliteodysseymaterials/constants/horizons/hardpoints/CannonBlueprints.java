package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class CannonBlueprints {
        public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
                HorizonsBlueprintType.EFFICIENT_WEAPON,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Raw.SULPHUR, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-38%", true, percentageNegative(0.0, 0.38))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Raw.SULPHUR, 1,
                                        Manufactured.HEATDISPERSIONPLATE, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.08, 0.12)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-43%", true, percentageNegative(0.38, 0.43)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.0, 0.15)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-12%", true, percentageNegative(0.0, 0.12))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Raw.CHROMIUM, 1,
                                        Encoded.SCRAMBLEDEMISSIONDATA, 1,
                                        Manufactured.HEATEXCHANGERS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.12, 0.16)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-48%", true, percentageNegative(0.43, 0.48)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-24%", true, percentageNegative(0.12, 0.24))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Raw.SELENIUM, 1,
                                        Encoded.ARCHIVEDEMISSIONDATA, 1,
                                        Manufactured.HEATVANES, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.16, 0.2)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-53%", true, percentageNegative(0.48, 0.53)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-36%", true, percentageNegative(0.24, 0.36))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Raw.CADMIUM, 1,
                                        Encoded.EMISSIONDATA, 1,
                                        Manufactured.PROTOHEATRADIATORS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.2, 0.24)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.53, 0.6)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-48%", true, percentageNegative(0.36, 0.48))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                ),
                HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Manufactured.MECHANICALSCRAP, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+2%", true, percentagePositive(0.0, 0.02)),
                                        HorizonsModifier.CLIP_SIZE, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.0, 0.36)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                        HorizonsModifier.AMMO_CAPACITY, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.0, 0.36)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Raw.VANADIUM, 1,
                                        Manufactured.MECHANICALSCRAP, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+4%", true, percentagePositive(0.02, 0.04)),
                                        HorizonsModifier.CLIP_SIZE, new HorizonsNumberModifierValue("+52%", true, percentagePositive(0.36, 0.52)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3)),
                                        HorizonsModifier.AMMO_CAPACITY, new HorizonsNumberModifierValue("+52%", true, percentagePositive(0.36, 0.52)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.04, 0.08))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Raw.VANADIUM, 1,
                                        Raw.NIOBIUM, 1,
                                        Manufactured.MECHANICALSCRAP, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.04, 0.06)),
                                        HorizonsModifier.CLIP_SIZE, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.52, 0.68)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4)),
                                        HorizonsModifier.AMMO_CAPACITY, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.52, 0.68)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.08, 0.12))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Raw.TIN, 1,
                                        Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                        Manufactured.MECHANICALEQUIPMENT, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.06, 0.08)),
                                        HorizonsModifier.CLIP_SIZE, new HorizonsNumberModifierValue("+84%", true, percentagePositive(0.68, 0.84)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.4, 0.5)),
                                        HorizonsModifier.AMMO_CAPACITY, new HorizonsNumberModifierValue("+84%", true, percentagePositive(0.68, 0.84)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.12, 0.16))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                        Manufactured.MILITARYSUPERCAPACITORS, 1,
                                        Manufactured.MECHANICALCOMPONENTS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.08, 0.1)),
                                        HorizonsModifier.CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.0)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.5, 0.6)),
                                        HorizonsModifier.AMMO_CAPACITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.0)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.2))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                ),
                HorizonsBlueprintType.LIGHTWEIGHT_MOUNT,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Raw.PHOSPHORUS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.0, 0.3)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.0, 0.2))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Raw.MANGANESE, 1,
                                        Manufactured.SALVAGEDALLOYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.3, 0.45)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.2)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Raw.MANGANESE, 1,
                                        Manufactured.CONDUCTIVECERAMICS, 1,
                                        Manufactured.SALVAGEDALLOYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.45, 0.6)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.1, 0.2))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Manufactured.PROTOLIGHTALLOYS, 1,
                                        Manufactured.PHASEALLOYS, 1,
                                        Manufactured.CONDUCTIVECOMPONENTS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.6, 0.75)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.2, 0.3))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Manufactured.CONDUCTIVECERAMICS, 1,
                                        Manufactured.PROTOLIGHTALLOYS, 1,
                                        Manufactured.PROTORADIOLICALLOYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("-90%", true, percentageNegative(0.75, 0.9)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-60%", false, percentageNegative(0.5, 0.6)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.3, 0.35)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.3, 0.4))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                ),
                HorizonsBlueprintType.LONG_RANGE_WEAPON,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Raw.SULPHUR, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                        HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Raw.SULPHUR, 1,
                                        Encoded.CONSUMERFIRMWARE, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.2, 0.4)),
                                        HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.2, 0.4)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.2, 0.4)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Raw.SULPHUR, 1,
                                        Encoded.CONSUMERFIRMWARE, 1,
                                        Manufactured.FOCUSCRYSTALS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.4, 0.6)),
                                        HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.4, 0.6)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.4, 0.6)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Encoded.CONSUMERFIRMWARE, 1,
                                        Manufactured.CONDUCTIVEPOLYMERS, 1,
                                        Manufactured.FOCUSCRYSTALS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.6, 0.8)),
                                        HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.6, 0.8)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.6, 0.8)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Encoded.INDUSTRIALFIRMWARE, 1,
                                        Manufactured.BIOTECHCONDUCTORS, 1,
                                        Manufactured.THERMICALLOYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8, 1.0)),
                                        HorizonsModifier.SHOT_SPEED, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8, 1.0)),
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.3)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8, 1.0)),
                                        HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                ),
                HorizonsBlueprintType.OVERCHARGED_WEAPON,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Raw.NICKEL, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.0, 0.15)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Raw.NICKEL, 1,
                                        Manufactured.CONDUCTIVECOMPONENTS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Raw.NICKEL, 1,
                                        Manufactured.CONDUCTIVECOMPONENTS, 1,
                                        Manufactured.ELECTROCHEMICALARRAYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Raw.ZINC, 1,
                                        Manufactured.CONDUCTIVECERAMICS, 1,
                                        Manufactured.POLYMERCAPACITORS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.3)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Raw.ZIRCONIUM, 1,
                                        Encoded.EMBEDDEDFIRMWARE, 1,
                                        Manufactured.CONDUCTIVEPOLYMERS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+35%", false, percentagePositive(0.3, 0.35)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.6, 0.7))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                ),
                HorizonsBlueprintType.RAPID_FIRE_MODIFICATION,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Manufactured.MECHANICALSCRAP, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.0, 0.25)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08)),
                                        HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Manufactured.HEATDISPERSIONPLATE, 1,
                                        Manufactured.MECHANICALSCRAP, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+17%", true, percentagePositive(0.08, 0.17)),
                                        HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-5%", true, percentageNegative(0.0, 0.05)),
                                        HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Encoded.LEGACYFIRMWARE, 1,
                                        Manufactured.PRECIPITATEDALLOYS, 1,
                                        Manufactured.MECHANICALEQUIPMENT, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.17, 0.26)),
                                        HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.05, 0.15)),
                                        HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Encoded.CONSUMERFIRMWARE, 1,
                                        Manufactured.MECHANICALCOMPONENTS, 1,
                                        Manufactured.THERMICALLOYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+35%", true, percentagePositive(0.26, 0.35)),
                                        HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                        HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Raw.TECHNETIUM, 1,
                                        Manufactured.PRECIPITATEDALLOYS, 1,
                                        Manufactured.CONFIGURABLECOMPONENTS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+44%", true, percentagePositive(0.35, 0.44)),
                                        HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65)),
                                        HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                        HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                ),
                HorizonsBlueprintType.SHORT_RANGE_BLASTER,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Raw.NICKEL, 1
                                ),
                                Map.of(
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+27%", true, percentagePositive(0.0, 0.27)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Raw.NICKEL, 1,
                                        Encoded.CONSUMERFIRMWARE, 1
                                ),
                                Map.of(
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+39%", true, percentagePositive(0.27, 0.39)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Raw.NICKEL, 1,
                                        Encoded.CONSUMERFIRMWARE, 1,
                                        Manufactured.ELECTROCHEMICALARRAYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+51%", true, percentagePositive(0.39, 0.51)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Encoded.CONSUMERFIRMWARE, 1,
                                        Manufactured.CONDUCTIVEPOLYMERS, 1,
                                        Manufactured.ELECTROCHEMICALARRAYS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+63%", true, percentagePositive(0.51, 0.63)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Encoded.INDUSTRIALFIRMWARE, 1,
                                        Manufactured.BIOTECHCONDUCTORS, 1,
                                        Manufactured.CONFIGURABLECOMPONENTS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4)),
                                        HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+75%", true, percentagePositive(0.63, 0.75)),
                                        HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                ),
                HorizonsBlueprintType.STURDY_MOUNT,
                Map.of(
                        HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                                Map.of(
                                        Raw.NICKEL, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                        HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                                Map.of(
                                        Raw.NICKEL, 1,
                                        Manufactured.SHIELDEMITTERS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.2, 0.4)),
                                        HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.2, 0.3)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+150%", true, percentagePositive(1.0, 1.5)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.1, 0.15))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.TOD_THE_BLASTER_MCQUINN,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                                Map.of(
                                        Raw.TUNGSTEN, 1,
                                        Raw.NICKEL, 1,
                                        Manufactured.SHIELDEMITTERS, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.4, 0.6)),
                                        HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.5, 2.0)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.15, 0.2))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                                Map.of(
                                        Raw.TUNGSTEN, 1,
                                        Raw.ZINC, 1,
                                        Raw.MOLYBDENUM, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.6, 0.8)),
                                        HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+250%", true, percentagePositive(2.0, 2.5)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        ),
                        HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                                Map.of(
                                        Raw.TECHNETIUM, 1,
                                        Raw.MOLYBDENUM, 1,
                                        Manufactured.HIGHDENSITYCOMPOSITES, 1
                                ),
                                Map.of(
                                        HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.8, 1.0)),
                                        HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6)),
                                        HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.5, 3.0)),
                                        HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3))
                                ),
                                List.of(
                                        Engineer.MARSHA_HICKS,
                                        Engineer.THE_SARGE
                                )
                        )
                )
        );

}
