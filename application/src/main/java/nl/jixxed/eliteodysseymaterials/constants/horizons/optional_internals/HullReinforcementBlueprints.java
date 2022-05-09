package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HullReinforcementBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+3%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+19%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+6%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1,
                                    Raw.ZIRCONIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+26%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+9%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.MERCURY, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+33%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+12%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.RUTHENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+15%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))),
            HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.CARBON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+24%", true),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+3%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+8%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+36%", true),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+16%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+48%", true),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+9%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+24%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+32%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+72%", true),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))),
            HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+3%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+19%", true),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+6%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+26%", true),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+9%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+33%", true),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+12%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+15%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))),
            HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+8%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-8%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-12%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+16%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-16%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.GERMANIUM, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-16%", false),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-20%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.MILITARYGRADEALLOYS, 1,
                                    Raw.TIN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.HULL_BOOST, new HorizonsModifierValue("+24%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-24%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))),
            HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+3%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+19%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+6%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+26%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+9%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.HEATVANES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+33%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+12%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.PROTOHEATRADIATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsModifierValue("+15%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.SELENE_JEAN))));
}
