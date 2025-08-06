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
public class FragmentCannonBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.DOUBLE_SHOT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_SHOT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.CARBON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+600%", true, percentagePositive(4.0, 6.0)),
                                    HorizonsModifier.BURST_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_SHOT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+800%", true, percentagePositive(6.0, 8.0)),
                                    HorizonsModifier.BURST_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_SHOT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+1000%", true, percentagePositive(8.0, 10.0)),
                                    HorizonsModifier.BURST_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_SHOT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Encoded.SECURITYFIRMWARE, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+1200%", true, percentagePositive(10.0, 12.0)),
                                    HorizonsModifier.BURST_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_SHOT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.EMBEDDEDFIRMWARE, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("3", true, plus(3.0)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+1400%", true, percentagePositive(12.0, 14.0)),
                                    HorizonsModifier.BURST_SIZE, new HorizonsNumberModifierValue("1", true, plus(1.0)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    )
            ),
            HorizonsBlueprintType.EFFICIENT_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.04, 0.08)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-37.5%", true, percentageNegative(0.325, 0.375))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1, Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.08, 0.12)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.08, 0.12)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-12%", true, percentageNegative(0.0, 0.12)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.0, 0.15)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-42.5%", true, percentageNegative(0.375, 0.425))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1, Encoded.SCRAMBLEDEMISSIONDATA, 1, Manufactured.HEATEXCHANGERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.12, 0.16)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.12, 0.16)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-24%", true, percentageNegative(0.12, 0.24)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-47.5%", true, percentageNegative(0.425, 0.475))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.HEATVANES, 1, Encoded.ARCHIVEDEMISSIONDATA, 1, Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.16, 0.20)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.16, 0.20)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-36%", true, percentageNegative(0.24, 0.36)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-52.5%", true, percentageNegative(0.475, 0.525))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.CADMIUM, 1, Manufactured.PROTOHEATRADIATORS, 1, Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.20, 0.24)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.20, 0.24)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-48%", true, percentageNegative(0.36, 0.48)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.525, 0.60))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    )
            ),
            HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.2, 0.36)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+2%", true, percentagePositive(0.0, 0.02)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.2, 0.36))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+52%", true, percentagePositive(0.36, 0.52)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+4%", true, percentagePositive(0.02, 0.04)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.04, 0.08)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+52%", true, percentagePositive(0.36, 0.52))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Raw.NIOBIUM, 1,
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.52, 0.68)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.04, 0.06)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.08, 0.12)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.52, 0.68))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.TIN, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+84%", true, percentagePositive(0.68, 0.84)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.06, 0.08)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.12, 0.16)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+84%", true, percentagePositive(0.68, 0.84))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.HIGH_CAPACITY_MAGAZINE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.5, 0.6)),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.0)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.08, 0.1)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.2)),
                                    HorizonsModifier.AMMO_CLIP_SIZE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.0))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    )
            ),
            HorizonsBlueprintType.LIGHTWEIGHT_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.15, 0.3)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.3, 0.45)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.2)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.45, 0.6)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.6, 0.75)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-60%", false, percentageNegative(0.5, 0.6)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-90%", true, percentageNegative(0.75, 0.9)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.3, 0.35)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    )
            ),
            HorizonsBlueprintType.OVERCHARGED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.2, 0.3)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.ZINC, 1,
                                    Manufactured.POLYMERCAPACITORS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.3))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ZIRCONIUM, 1,
                                    Encoded.EMBEDDEDFIRMWARE, 1,
                                    Manufactured.CONDUCTIVEPOLYMERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.6, 0.7)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.6, 0.7)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+35%", false, percentagePositive(0.3, 0.35))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    )
            ),
            HorizonsBlueprintType.RAPID_FIRE_MODIFICATION,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08))//TODO replace with burst interval
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-5%", true, percentageNegative(0.0, 0.05)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+17%", true, percentagePositive(0.08, 0.17))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.05, 0.15)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.17, 0.26))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Manufactured.THERMICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+35%", true, percentagePositive(0.26, 0.35))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TECHNETIUM, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
//                                    HorizonsModifier.DAMAGE_PER_SECOND, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.BURST_RATE_OF_FIRE, new HorizonsNumberModifierValue("+44%", true, percentagePositive(0.35, 0.44))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    )
            ),
            HorizonsBlueprintType.STURDY_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.5, 1.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.1, 0.2)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.05, 0.1)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+150%", true, percentagePositive(1.0, 1.5)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.2, 0.3)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.1, 0.15)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.2, 0.4))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.5, 2.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.15, 0.2)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.4, 0.6))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TOD_THE_BLASTER_MCQUINN,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.ZINC, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.MOLYBDENUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+250%", true, percentagePositive(2.0, 2.5)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.6, 0.8))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TECHNETIUM, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.5, 3.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.8, 1.0))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.ZACARIAH_NEMO
                            )
                    )
            )
    );

}
