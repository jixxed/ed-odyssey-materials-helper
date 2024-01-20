package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

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
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.0, 0.05)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-18%", true, percentageNegative(0.0, 0.18)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.2)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_MASS, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_MASS, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_MASS, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.05, 0.1)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-26%", true, percentageNegative(0.18, 0.26)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_MASS, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_MASS, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_MASS, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.1, 0.15)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-34%", true, percentageNegative(0.26, 0.34)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_MASS, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_MASS, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_MASS, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Manufactured.THERMICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.2)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-42%", true, percentageNegative(0.34, 0.42)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.3, 0.35)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_MASS, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_MASS, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_MASS, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.ENHANCED_LOW_POWER_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TIN, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.MILITARYGRADEALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.42, 0.5)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.35, 0.4)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_MASS, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.05, 0.06)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_MASS, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.05, 0.06)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_MASS, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.05, 0.06))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    )
            ),
            HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+10%", true, resistancePositive(0.0, 0.1)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-3%", false, resistanceNegative(0.0, 0.03))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+20%", true, resistancePositive(0.1, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-6%", false, resistanceNegative(0.03, 0.06))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.SELENIUM, 1,
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+30%", true, resistancePositive(0.2, 0.3)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.25, 0.3)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-9%", false, resistanceNegative(0.06, 0.09))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MERCURY, 1,
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, resistancePositive(0.3, 0.4)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+35%", true, percentagePositive(0.3, 0.35)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, resistanceNegative(0.09, 0.12))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.KINETIC_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.RUTHENIUM, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+50%", true, resistancePositive(0.4, 0.5)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.35, 0.4)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-15%", false, resistanceNegative(0.12, 0.15))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    )
            ),
            HorizonsBlueprintType.REINFORCED_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+4.5%", true, resistancePositive(0.0, 0.045)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+4.5%", true, resistancePositive(0.0, 0.045)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+4.5%", true, resistancePositive(0.0, 0.045)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+14%", true, percentagePositive(0.0, 0.14)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+14%", true, percentagePositive(0.0, 0.14)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+14%", true, percentagePositive(0.0, 0.14)),
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.ENERGY_PER_REGEN, new HorizonsNumberModifierValue("+4%", false, percentagePositive(0.0, 0.04))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.PHOSPHORUS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+7.5%", true, resistancePositive(0.045, 0.075)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+7.5%", true, resistancePositive(0.045, 0.075)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+7.5%", true, resistancePositive(0.045, 0.075)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.14, 0.2)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.14, 0.2)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.14, 0.2)),
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.1, 0.1)),
                                    HorizonsModifier.ENERGY_PER_REGEN, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.04, 0.06))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.PHOSPHORUS, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.1, 0.1)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+10.5%", true, resistancePositive(0.075, 0.105)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+10.5%", true, resistancePositive(0.075, 0.105)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+10.5%", true, resistancePositive(0.075, 0.105)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.2, 0.26)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.2, 0.26)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.2, 0.26)),
                                    HorizonsModifier.ENERGY_PER_REGEN, new HorizonsNumberModifierValue("+8%", false, percentagePositive(0.06, 0.08))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.CONFIGURABLECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.1, 0.1)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+13.5%", true, resistancePositive(0.105, 0.135)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+13.5%", true, resistancePositive(0.105, 0.135)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+13.5%", true, resistancePositive(0.105, 0.135)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+32%", true, percentagePositive(0.26, 0.32)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+32%", true, percentagePositive(0.26, 0.32)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+32%", true, percentagePositive(0.26, 0.32)),
                                    HorizonsModifier.ENERGY_PER_REGEN, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.08, 0.1))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.REINFORCED_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ARSENIC, 1,
                                    Manufactured.CONDUCTIVEPOLYMERS, 1,
                                    Manufactured.IMPROVISEDCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.BROKEN_REGEN_RATE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.1, 0.1)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+16.5%", true, resistancePositive(0.135, 0.165)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+16.5%", true, resistancePositive(0.135, 0.165)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+16.5%", true, resistancePositive(0.135, 0.165)),
                                    HorizonsModifier.SHIELDGEN_MINIMUM_STRENGTH, new HorizonsNumberModifierValue("+38%", true, percentagePositive(0.32, 0.38)),
                                    HorizonsModifier.SHIELDGEN_OPTIMAL_STRENGTH, new HorizonsNumberModifierValue("+38%", true, percentagePositive(0.32, 0.38)),
                                    HorizonsModifier.SHIELDGEN_MAXIMUM_STRENGTH, new HorizonsNumberModifierValue("+38%", true, percentagePositive(0.32, 0.38)),
                                    HorizonsModifier.ENERGY_PER_REGEN, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.1, 0.12))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    )
            ),
            HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, resistanceNegative(0.0, 0.04)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+10%", true, resistancePositive(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-8%", false, resistanceNegative(0.04, 0.08)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+20%", true, resistancePositive(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.SELENIUM, 1,
                                    Raw.GERMANIUM, 1,
                                    Encoded.SHIELDCYCLERECORDINGS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-12%", false, resistanceNegative(0.08, 0.12)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.25, 0.3)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+30%", true, resistancePositive(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MERCURY, 1,
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-16%", false, resistanceNegative(0.12, 0.16)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+35%", true, percentagePositive(0.3, 0.35)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+40%", true, resistancePositive(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_GENERATOR, HorizonsBlueprintType.THERMAL_RESISTANT_SHIELDS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.RUTHENIUM, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-20%", false, resistanceNegative(0.16, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.35, 0.4)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+50%", true, resistancePositive(0.4, 0.5))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG
                            )
                    )
            )
    );


}
