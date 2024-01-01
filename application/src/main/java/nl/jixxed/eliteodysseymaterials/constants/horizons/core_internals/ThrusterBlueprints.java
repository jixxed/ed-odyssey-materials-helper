package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentageNegative;
import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;

public class ThrusterBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.CLEAN_DRIVE_TUNING,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.20)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.0, 0.08)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.02, 0.04)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+13%", true, percentagePositive(0.08, 0.13)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.20, 0.30))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Encoded.LEGACYFIRMWARE, 1,
                                    Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.04, 0.08)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.04, 0.08)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+18%", true, percentagePositive(0.13, 0.18)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.30, 0.40))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Encoded.DECODEDEMISSIONDATA, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.08, 0.12)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.08, 0.12)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+23%", true, percentagePositive(0.18, 0.23)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.40, 0.50))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.CLEAN_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.COMPACTEMISSIONSDATA, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.TIN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.12, 0.16)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-16%", false, percentageNegative(0.12, 0.16)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.10)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+28%", true, percentagePositive(0.23, 0.28)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.50, 0.60))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI))
            ),
//            ct_dt : { name:'Dirty Tuning', maxgrade:5, integ:[-3,-6,-9,-12,-15], pwrdraw:[4,6,8,10,12], engoptmass:[-2.5,-5,-7.5,-10,-12.5], engoptmul:[12,19,26,33,40], engheat:[20,30,40,50,60], mats:[ {splefi:1}, {splefi:1, meeq:1}, {splefi:1, chr:1, meco:1}, {mocofi:1, sel:1, confco:1}, {crinfi:1, cad:1, phis:1} ], fdname:'Engine_Dirty' },
            HorizonsBlueprintType.DIRTY_DRIVE_TUNING,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-2.5%", false, percentageNegative(0.0, 0.025)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.04, 0.06)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.025, 0.05)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.20, 0.30))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.06, 0.08)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-7.5%", false, percentageNegative(0.05, 0.075)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.30, 0.40))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.08, 0.10)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.075, 0.10)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.40, 0.50))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DIRTY_DRIVE_TUNING, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.CADMIUM, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.PHARMACEUTICALISOLATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.10, 0.12)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.ENGINE_OPTIMAL_MASS, new HorizonsNumberModifierValue("-12.5%", false, percentageNegative(0.10, 0.125)),
                                    HorizonsModifier.OPTIMAL_MULTIPLIER, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.40)),
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.50, 0.60))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI))
            ),
            HorizonsBlueprintType.DRIVE_STRENGTHENING,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.CARBON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.0, 0.30)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+5%", false, percentagePositive(0.0, 0.05))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.10, 0.20)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.30, 0.50)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.05, 0.10))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.ELVIRA_MARTUUK, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.20, 0.30)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+70%", true, percentagePositive(0.50, 0.70)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.10, 0.15))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.30, 0.40)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+90%", true, percentagePositive(0.70, 0.90)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.20))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI)),

                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.THRUSTERS, HorizonsBlueprintType.DRIVE_STRENGTHENING, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Manufactured.IMPERIALSHIELDING, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINE_THERMAL_LOAD, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.40, 0.50)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+110%", true, percentagePositive(0.90, 1.10)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.20, 0.25))
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.PROFESSOR_PALIN, Engineer.CHLOE_SEDESI))
            ));

}
