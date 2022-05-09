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
public class ShieldGeneratorBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+3%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-18%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Raw.GERMANIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-26%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Raw.GERMANIUM, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+9%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-34%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Raw.NIOBIUM, 1,
                                    Manufactured.THERMICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-42%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MILITARYGRADEALLOYS, 1,
                                    Raw.TIN, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-40%", true),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-25%", false),
                                    HorizonsModifier.OPTIMAL_MASS, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-50%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG))),
            HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+10%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-3%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+25%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-6%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-9%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Raw.MERCURY, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+35%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-12%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1,
                                    Raw.RUTHENIUM, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-15%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG))),
            HorizonsBlueprintType.REINFORCED_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("+4%", false),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+14%", true),
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+5%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("+6%", false),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+8%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("+8%", false),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+26%", true),
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+11%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-4%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1,
                                    Raw.MANGANESE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+32%", true),
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+14%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-7%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ARSENIC, 1,
                                    Manufactured.CONDUCTIVEPOLYMERS, 1,
                                    Manufactured.IMPROVISEDCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("+12%", false),
                                    HorizonsModifier.OPTIMAL_STRENGTH, new HorizonsModifierValue("+38%", true),
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+17%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-9%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG))),
            HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+10%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Raw.GERMANIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+25%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+20%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Raw.GERMANIUM, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+30%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Raw.MERCURY, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-16%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+35%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+40%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1,
                                    Raw.RUTHENIUM, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+50%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG))));

}
