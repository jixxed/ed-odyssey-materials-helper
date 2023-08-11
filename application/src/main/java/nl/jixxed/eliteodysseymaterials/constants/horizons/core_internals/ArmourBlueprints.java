package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier.*;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArmourBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.BLAST_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04))
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1,
                                    Raw.ZIRCONIUM, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.MERCURY, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.10)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.10))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.RUTHENIUM, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.40)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.10, 0.12)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.10, 0.12))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.HEAVY_DUTY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.CARBON, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+1%", true, percentagePositive(0.0, 0.01)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12)),
                                    MASS, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.10))
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+2%", true, percentagePositive(0.01, 0.02)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("+17%", true, percentagePositive(0.12, 0.17)),
                                    MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.10, 0.15))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.02, 0.03)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("+22%", true, percentagePositive(0.17, 0.22)),
                                    MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.20))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+4%", true, percentagePositive(0.03, 0.04)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("+27%", true, percentagePositive(0.22, 0.27)),
                                    MASS, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.20, 0.25))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.04, 0.05)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("+32%", true, percentagePositive(0.27, 0.32)),
                                    MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.30))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.KINETIC_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12))
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.10)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.10)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.10, 0.12)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.10, 0.12)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.40))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                    MASS, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.0, 0.15))
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    MASS, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.15, 0.25))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    MASS, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.TIN, 1,
                                    Manufactured.MILITARYGRADEALLOYS, 1
                            ),
                            Map.of(
                                    ALL_RESISTANCES, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HULL_BOOST, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    MASS, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.THERMAL_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04))
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.HEATVANES, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.PROTOHEATRADIATORS, 1
                            ),
                            Map.of(
                                    EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.1, 0.12)),
                                    THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.4)),
                                    KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.1, 0.12))
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ));
}
