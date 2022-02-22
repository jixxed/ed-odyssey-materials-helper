package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class FSDBlueprints {
    public static final Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintModificationType.FASTER_FSD_BOOT_SEQUENCE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+3%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+4%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+8%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.GRIDRESISTORS, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+9%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-50%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+12%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.CADMIUM, 1,
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-65%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+16%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.FASTER_FSD_BOOT_SEQUENCE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Manufactured.HEATVANES, 1,
                                    Raw.TELLURIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-80%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK))
            ),
            HorizonsBlueprintModificationType.INCREASED_FSD_RANGE,
            Map.of(

                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.DISRUPTEDWAKEECHOES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+3%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+10%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.DISRUPTEDWAKEECHOES, 1,
                                    Manufactured.CHEMICALPROCESSORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+6%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+25%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+15%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CHEMICALPROCESSORS, 1,
                                    Raw.PHOSPHORUS, 1,
                                    Encoded.WAKESOLUTIONS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+9%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+35%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CHEMICALDISTILLERY, 1,
                                    Encoded.HYPERSPACETRAJECTORIES, 1,
                                    Raw.MANGANESE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+12%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+45%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+25%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.INCREASED_FSD_RANGE, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ARSENIC, 1,
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Encoded.DATAMINEDWAKE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+15%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+55%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK))
            ),
            HorizonsBlueprintModificationType.SHIELDED_FSD,
            Map.of(

                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+3%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+25%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+4%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-15%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+8%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+9%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+75%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+12%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.COLONEL_BRIS_DEKKER, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+16%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.FRAME_SHIFT_DRIVE, HorizonsBlueprintModificationType.SHIELDED_FSD, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.IMPERIALSHIELDING, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+125%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK))));
}
