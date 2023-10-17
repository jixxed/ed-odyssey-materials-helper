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
public class PulseLaserBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.EFFICIENT_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-38%", true, percentageNegative(0.0, 0.38)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.SULPHUR, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.0, 0.15)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.08, 0.12)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-12%", true, percentageNegative(0.0, 0.12)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-43%", true, percentageNegative(0.38, 0.43))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Encoded.SCRAMBLEDEMISSIONDATA, 1,
                                    Manufactured.HEATEXCHANGERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.12, 0.16)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-24%", true, percentageNegative(0.12, 0.24)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-48%", true, percentageNegative(0.43, 0.48))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.SELENIUM, 1,
                                    Encoded.ARCHIVEDEMISSIONDATA, 1,
                                    Manufactured.HEATVANES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.16, 0.2)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-36%", true, percentageNegative(0.24, 0.36)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-53%", true, percentageNegative(0.48, 0.53))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.CADMIUM, 1,
                                    Encoded.EMISSIONDATA, 1,
                                    Manufactured.PROTOHEATRADIATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.2, 0.24)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-48%", true, percentageNegative(0.36, 0.48)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.53, 0.6))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            ),
            HorizonsBlueprintType.FOCUSED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.4)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+1%", false, percentagePositive(0.0, 0.01)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.0, 0.36))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+2%", false, percentagePositive(0.01, 0.02)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+52%", true, percentagePositive(0.36, 0.52))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Raw.IRON, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.02, 0.03)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+68%", true, percentagePositive(0.52, 0.68))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Manufactured.POLYMERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.03, 0.04)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+84%", true, percentagePositive(0.68, 0.84))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.FOCUSED_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+120%", true, percentagePositive(1.0, 1.2)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+5%", false, percentagePositive(0.04, 0.05)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.84, 1.0))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            ),
            HorizonsBlueprintType.LIGHTWEIGHT_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.0, 0.3)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.0, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.3, 0.45)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.45, 0.6)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.6, 0.75)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PROTORADIOLICALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-90%", true, percentageNegative(0.75, 0.9)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.3, 0.35)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-60%", false, percentageNegative(0.5, 0.6)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            ),
            HorizonsBlueprintType.LONG_RANGE_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.SULPHUR, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.2, 0.4)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.2, 0.4))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.SULPHUR, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.4, 0.6))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Manufactured.CONDUCTIVEPOLYMERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.6, 0.8))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.BIOTECHCONDUCTORS, 1,
                                    Manufactured.THERMICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.3)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8, 1.0))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            ),
            HorizonsBlueprintType.OVERCHARGED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.3)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.0, 0.15))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.ZINC, 1,
                                    Manufactured.POLYMERCAPACITORS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.3))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ZIRCONIUM, 1,
                                    Encoded.EMBEDDEDFIRMWARE, 1,
                                    Manufactured.CONDUCTIVEPOLYMERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.6, 0.7)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+35%", false, percentagePositive(0.3, 0.35))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            ),
            HorizonsBlueprintType.RAPID_FIRE_MODIFICATION,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
//                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.0, 0.25)),
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+17%", true, percentagePositive(0.08, 0.17)),
//                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-5%", true, percentageNegative(0.0, 0.05)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.17, 0.26)),
//                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.05, 0.15)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.THERMICALLOYS, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+35%", true, percentagePositive(0.26, 0.35)),
//                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.RAPID_FIRE_MODIFICATION, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TECHNETIUM, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.RATE_OF_FIRE, new HorizonsNumberModifierValue("+44%", true, percentagePositive(0.35, 0.44)),
//                                    HorizonsModifier.RELOAD_TIME, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.JITTER, new HorizonsNumberModifierValue("0,5", false, plus(0.5))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            ),
            HorizonsBlueprintType.SHORT_RANGE_BLASTER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+27%", true, percentagePositive(0.0, 0.27))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+39%", true, percentagePositive(0.27, 0.39)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+51%", true, percentagePositive(0.39, 0.51)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.CONDUCTIVEPOLYMERS, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+63%", true, percentagePositive(0.51, 0.63)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Manufactured.BIOTECHCONDUCTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+75%", true, percentagePositive(0.63, 0.75)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            ),
            HorizonsBlueprintType.STURDY_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.2, 0.4)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+150%", true, percentagePositive(1.0, 1.5)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.2, 0.3)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.1, 0.15))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.5, 2.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.15, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.ZINC, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+250%", true, percentagePositive(2.0, 2.5)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.THE_DWELLER,
                                    Engineer.BROO_TARQUIN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PULSE_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.5, 3.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.BROO_TARQUIN
                            )
                    )
            )
    );

}
