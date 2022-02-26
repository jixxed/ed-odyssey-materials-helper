package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class ArmourBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.BLAST_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-4%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+19%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-6%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1,
                                    Raw.ZIRCONIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+26%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-8%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.MERCURY, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+33%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-10%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.RUTHENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-12%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.HEAVY_DUTY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.CARBON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+1%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+10%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+2%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+17%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+15%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+3%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+22%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+4%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+27%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+25%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+5%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+32%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.KINETIC_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+12%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+19%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+26%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+33%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+40%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+3%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-15%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-25%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+9%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-35%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-45%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.TIN, 1,
                                    Manufactured.MILITARYGRADEALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-56%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ),
            HorizonsBlueprintType.THERMAL_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-4%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.SELENE_JEAN, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+19%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-6%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+26%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-8%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.HEATVANES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+33%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-10%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.ARMOUR, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.PROTOHEATRADIATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-12%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))
            ));
}
