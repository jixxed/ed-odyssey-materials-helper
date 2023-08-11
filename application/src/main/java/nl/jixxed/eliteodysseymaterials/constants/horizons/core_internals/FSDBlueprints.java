package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

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
public class FSDBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.FASTER_FSD_BOOT_SEQUENCE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+3%", true, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-3%", false, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-20%", true, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+4%", false, percentageNegative(0.0, 0.04))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+6%", true, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-6%", false, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-35%", true, percentagePositive(0.20, 0.35)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+8%", false, percentageNegative(0.04, 0.08))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.GRIDRESISTORS, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+9%", true, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-9%", false, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-50%", true, percentagePositive(0.35, 0.50)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+12%", false, percentageNegative(0.08, 0.12))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.CADMIUM, 1,
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+12%", true, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-12%", false, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-65%", true, percentagePositive(0.50, 0.65)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+16%", false, percentageNegative(0.12, 0.16))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Manufactured.HEATVANES, 1,
                                    Raw.TELLURIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+15%", true, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-80%", true, percentagePositive(0.65, 0.80)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("+20%", false, percentageNegative(0.16, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK))
            ),
            HorizonsBlueprintType.INCREASED_FSD_RANGE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.DISRUPTEDWAKEECHOES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-3%", false, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.10))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.DISRUPTEDWAKEECHOES, 1,
                                    Manufactured.CHEMICALPROCESSORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.15, 0.25)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-6%", false, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.10, 0.15))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CHEMICALPROCESSORS, 1,
                                    Raw.PHOSPHORUS, 1,
                                    Encoded.WAKESOLUTIONS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+35%", true, percentagePositive(0.25, 0.35)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-9%", false, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CHEMICALDISTILLERY, 1,
                                    Encoded.HYPERSPACETRAJECTORIES, 1,
                                    Raw.MANGANESE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+45%", true, percentagePositive(0.35, 0.45)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-12%", false, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.20, 0.25))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ARSENIC, 1,
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Encoded.DATAMINEDWAKE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+55%", true, percentagePositive(0.45, 0.55)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-15%", false, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.30))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK))
            ),
            HorizonsBlueprintType.SHIELDED_FSD,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.0, 0.25)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.25, 0.50)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.04, 0.08))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+75%", true, percentagePositive(0.50, 0.75)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.15, 0.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.08, 0.12))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.75, 1.00)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.20, 0.25)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.12, 0.16))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE, HorizonsBlueprintType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.IMPERIALSHIELDING, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+125%", true, percentagePositive(1.00, 1.25)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.30)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK))
            )
    );
}
