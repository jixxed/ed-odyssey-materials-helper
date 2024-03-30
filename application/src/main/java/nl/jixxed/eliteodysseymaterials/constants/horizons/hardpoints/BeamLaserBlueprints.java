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
public class BeamLaserBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LIGHTWEIGHT_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.0, 0.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.0, 0.30))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.20, 0.30)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.10, 0.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.30, 0.45))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.10, 0.20)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.30, 0.40)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.20, 0.25)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.45, 0.60))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.20, 0.30)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.40, 0.50)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.30)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.60, 0.75))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.30, 0.40)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-60%", false, percentageNegative(0.50, 0.60)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.40, 0.35)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-90%", true, percentageNegative(0.75, 0.90))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    )
            ),
            HorizonsBlueprintType.LONG_RANGE_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+20%", true, falloffPercentagePositive(2500.0, 0.0, 0.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.10))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.20, 0.40)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+40%", true, falloffPercentagePositive(2500.0, 0.20, 0.40)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.10, 0.15))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.40, 0.60)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+60%", true, falloffPercentagePositive(2500.0, 0.40, 0.60)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVEPOLYMERS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.60, 0.80)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+80%", true, falloffPercentagePositive(2500.0, 0.60, 0.80)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.20, 0.25))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.LONG_RANGE_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.THERMICALLOYS, 1,
                                    Manufactured.BIOTECHCONDUCTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.80, 1.0)),
                                    HorizonsModifier.DAMAGE_FALLOFF_START, new HorizonsNumberModifierValue("+100%", true, falloffPercentagePositive(2500.0, 0.80, 1.0)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.30))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    )
            ),
            HorizonsBlueprintType.SHORT_RANGE_BLASTER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+27%", true, percentagePositive(0.0, 0.27)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.10))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+39%", true, percentagePositive(0.27, 0.39)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.10, 0.20)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.10))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+51%", true, percentagePositive(0.39, 0.51)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.20, 0.30)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.10, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVEPOLYMERS, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+63%", true, percentagePositive(0.51, 0.63)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.30, 0.40)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.20, 0.30))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.SHORT_RANGE_BLASTER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.BIOTECHCONDUCTORS, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+75%", true, percentagePositive(0.63, 0.75)),
                                    HorizonsModifier.MAXIMUM_RANGE, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.40, 0.50)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.30, 0.40))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    )
            ),
            HorizonsBlueprintType.STURDY_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+150%", true, percentagePositive(1.0, 1.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.20, 0.30)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.20, 0.40))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.5, 2.0)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.15, 0.20)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.30, 0.40)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.40, 0.60))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+250%", true, percentagePositive(2.0, 2.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.20, 0.25)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.40, 0.50)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.60, 0.80))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.5, 3.0)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.30)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.50, 0.60)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.80, 1.0))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    )
            ),
            HorizonsBlueprintType.EFFICIENT_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-37.5%", true, percentageNegative(0.0, 0.375))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1, Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.08, 0.12)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-12%", true, percentageNegative(0.0, 0.12)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.0, 0.15)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-42.5%", true, percentageNegative(0.375, 0.425))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1, Encoded.SCRAMBLEDEMISSIONDATA, 1, Manufactured.HEATEXCHANGERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.12, 0.16)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-24%", true, percentageNegative(0.12, 0.24)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-47.5%", true, percentageNegative(0.425, 0.475))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.HEATVANES, 1, Encoded.ARCHIVEDEMISSIONDATA, 1, Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.16, 0.20)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-36%", true, percentageNegative(0.24, 0.36)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-52.5%", true, percentageNegative(0.475, 0.525))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.EFFICIENT_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.CADMIUM, 1, Manufactured.PROTOHEATRADIATORS, 1, Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.20, 0.24)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-48%", true, percentageNegative(0.36, 0.48)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.525, 0.60))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    )
            ),
            HorizonsBlueprintType.OVERCHARGED_WEAPON,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.0, 0.15)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1, Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.30, 0.40)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.20)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1, Manufactured.ELECTROCHEMICALARRAYS, 1, Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.40, 0.50)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.20, 0.25)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.THE_DWELLER, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1, Manufactured.POLYMERCAPACITORS, 1, Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.50, 0.60)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.30)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.BEAM_LASER, HorizonsBlueprintType.OVERCHARGED_WEAPON, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVEPOLYMERS, 1, Encoded.EMBEDDEDFIRMWARE, 1, Raw.ZIRCONIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DAMAGE, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.60, 0.70)),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("+35%", false, percentagePositive(0.30, 0.35)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.BROO_TARQUIN)
                    )
            ));
}
