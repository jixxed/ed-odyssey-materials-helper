package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

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
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+4%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-4%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+4%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-6%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+8%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+8%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+80%", true),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-8%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+12%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+10%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+16%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.ARMOURED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-12%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.HERA_TANI))),
            HorizonsBlueprintType.LOW_EMISSIONS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+4%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("-3%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1,
                                    Encoded.ARCHIVEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+8%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("-6%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Raw.IRON, 1,
                                    Encoded.ARCHIVEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+12%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-45%", true),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("-9%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.EMISSIONDATA, 1,
                                    Manufactured.HEATVANES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+16%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-56%", true),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("-12%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.LOW_EMISSIONS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.DECODEDEMISSIONDATA, 1,
                                    Manufactured.PROTOHEATRADIATORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("-65%", true),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("-15%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.HERA_TANI))),
            HorizonsBlueprintType.OVERCHARGED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+12%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.FELICITY_FARSEER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+19%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("+15%", false),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+26%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.CADMIUM, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+33%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_PLANT, HorizonsBlueprintType.OVERCHARGED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.TELLURIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-25%", false),
                                    HorizonsModifier.HEAT_EFFICIENCY, new HorizonsModifierValue("+25%", false),
                                    HorizonsModifier.POWER_GENERATION, new HorizonsModifierValue("+40%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.HERA_TANI))));
}
