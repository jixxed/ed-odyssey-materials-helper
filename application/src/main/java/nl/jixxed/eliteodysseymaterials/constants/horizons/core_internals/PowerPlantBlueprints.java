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
public class PowerPlantBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.ARMOURED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+4%", true, percentagePositive(0.0, 0.04)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.40)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-4%", true, percentageNegative(0.0, 0.04)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.04, 0.06)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.40, 0.60)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-6%", true, percentageNegative(0.04, 0.06)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.04, 0.08))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.06, 0.08)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.60, 0.80)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-8%", true, percentageNegative(0.06, 0.08)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.08, 0.12))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.08, 0.10)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.80, 1.00)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.08, 0.10)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.12, 0.16))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.10, 0.12)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(1.00, 1.20)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-12%", true, percentageNegative(0.10, 0.12)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.20))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.HERA_TANI))
            ),
            HorizonsBlueprintType.LOW_EMISSIONS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.0, 0.25)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1,
                                    Encoded.ARCHIVEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.04, 0.08)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.25, 0.35)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Raw.IRON, 1,
                                    Encoded.ARCHIVEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.08, 0.12)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.EMISSIONDATA, 1,
                                    Manufactured.HEATVANES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+16%", false, percentagePositive(0.12, 0.16)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.DECODEDEMISSIONDATA, 1,
                                    Manufactured.PROTOHEATRADIATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.16, 0.20)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.HERA_TANI))
            ),
            HorizonsBlueprintType.OVERCHARGED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.0, 0.05)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("+5%", false, percentagePositive(0.0, 0.05)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.0, 0.12))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.05, 0.10)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.05, 0.10)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+19%", true, percentagePositive(0.12, 0.19))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.10, 0.15)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.19, 0.26))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.CADMIUM, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.20)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.20)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+33%", true, percentagePositive(0.26, 0.33))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.TELLURIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.20, 0.25)),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.20, 0.25)),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.33, 0.40))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.HERA_TANI))
            )


    );
}
