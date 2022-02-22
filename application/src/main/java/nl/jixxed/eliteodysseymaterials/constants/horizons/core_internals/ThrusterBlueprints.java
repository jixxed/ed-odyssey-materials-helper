package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class ThrusterBlueprints {
    public static final Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintModificationType.CLEAN_DRIVE_TUNING,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+8%", true),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-2%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+4%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+13%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-30%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Encoded.LEGACYFIRMWARE, 1,
                                    Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+8%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+18%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-40%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Encoded.DECODEDEMISSIONDATA, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+12%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+23%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-50%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.COMPACTEMISSIONSDATA, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.TIN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+16%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-16%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+28%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-60%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI))),
            HorizonsBlueprintModificationType.DIRTY_DRIVE_TUNING,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+4%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+6%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+19%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+8%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+26%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+33%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+50%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.CADMIUM, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.PHARMACEUTICALISOLATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+12%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-13%", false),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI))),
            HorizonsBlueprintModificationType.DRIVE_STRENGTHENING,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.CARBON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+5%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+10%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+70%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+15%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-40%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+90%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.THRUSTERS, HorizonsBlueprintModificationType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.IMPERIALSHIELDING, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-50%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+110%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+25%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI))));
}
