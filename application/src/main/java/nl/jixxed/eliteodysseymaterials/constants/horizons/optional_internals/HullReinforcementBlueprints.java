package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;
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
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Raw.ZIRCONIUM, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MERCURY, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.GALVANISINGALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.BLAST_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.RUTHENIUM, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.PHASEALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.4)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    )
            ),
            HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.CARBON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.0, 0.24)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.0, 0.08)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.24, 0.36)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.08, 0.16)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+48%", true, percentagePositive(0.36, 0.48)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+24%", false, percentagePositive(0.16, 0.24)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.48, 0.6)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+32%", false, percentagePositive(0.24, 0.32)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.HEAVY_DUTY_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+72%", true, percentagePositive(0.6, 0.72)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.32, 0.4)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    )
            ),
            HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.GALVANISINGALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.KINETIC_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.4)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    )
            ),
            HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04)),
                                    HorizonsModifier.HULL_BOOST, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-8%", true, percentageNegative(0.0, 0.08))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.04, 0.08)),
                                    HorizonsModifier.HULL_BOOST, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.08, 0.12)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-12%", true, percentageNegative(0.08, 0.12))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.IRON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.08, 0.12)),
                                    HorizonsModifier.HULL_BOOST, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.12, 0.16)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-16%", true, percentageNegative(0.12, 0.16))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("-16%", false, percentageNegative(0.12, 0.16)),
                                    HorizonsModifier.HULL_BOOST, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.16, 0.2)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.16, 0.2))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.LIGHTWEIGHT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TIN, 1,
                                    Manufactured.MILITARYGRADEALLOYS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.16, 0.2)),
                                    HorizonsModifier.HULL_BOOST, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.2, 0.24)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-24%", true, percentageNegative(0.2, 0.24))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    )
            ),
            HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Manufactured.HEATVANES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HULL_REINFORCEMENT_PACKAGE, HorizonsBlueprintType.THERMAL_RESISTANT_HULL_REINFORCEMENT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOHEATRADIATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1)),
                                    HorizonsModifier.HULL_REINFORCEMENT, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.4))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.SELENE_JEAN
                            )
                    )
            )
    );

}
