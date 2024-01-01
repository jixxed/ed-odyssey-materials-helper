package nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts;

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
public class ShieldBoosterBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.BLAST_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+7%", true, resistancePositive(0.0, 0.078)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-1%", false, resistanceNegative(0.0, 0.01)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-1%", false, resistanceNegative(0.0, 0.01))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, resistancePositive(0.07, 0.12)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-1.75%", false, resistanceNegative(0.01, 0.0175)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-1.75%", false, resistanceNegative(0.01, 0.0175))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.IRON, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+17%", true, resistancePositive(0.12, 0.17)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-2.5%", false, resistanceNegative(0.0175, 0.025)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-2.5%", false, resistanceNegative(0.0175, 0.025))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+22%", true, resistancePositive(0.17, 0.22)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-3.25%", false, resistanceNegative(0.025, 0.0325)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-3.25%", false, resistanceNegative(0.025, 0.0325))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.SHIELDPATTERNANALYSIS, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+27%", true, resistancePositive(0.22, 0.27)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, resistanceNegative(0.0325, 0.04)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, resistanceNegative(0.0325, 0.04))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    )
            ),
            HorizonsBlueprintType.HEAVY_DUTY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.0, 1.0)),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+5%", false, percentagePositive(0.0, 0.05)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+3%", true, percentagePositive(0.0, 0.03))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+150%", false, percentagePositive(1.0, 1.5)),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsNumberModifierValue("+17%", true, percentagePositive(0.1, 0.17)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.05, 0.1)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.03, 0.06))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+200%", false, percentagePositive(1.5, 2.0)),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.17, 0.24)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.06, 0.09))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.TIN, 1,
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+250%", false, percentagePositive(2.0, 2.5)),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsNumberModifierValue("+31%", true, percentagePositive(0.24, 0.31)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+12%", true, percentagePositive(0.09, 0.12))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ANTIMONY, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.POLYMERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+300%", false, percentagePositive(2.5, 3.0)),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsNumberModifierValue("+38%", true, percentagePositive(0.31, 0.38)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.12, 0.15))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    )
            ),
            HorizonsBlueprintType.KINETIC_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-1%", false, resistanceNegative(0.0, 0.01)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+7%", true, resistancePositive(0.0, 0.07)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-1%", false, resistanceNegative(0.0, 0.01))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-1.75%", false, resistanceNegative(0.01, 0.0175)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, resistancePositive(0.07, 0.12)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-1.75%", false, resistanceNegative(0.01, 0.0175))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HYBRIDCAPACITORS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-2.5%", false, resistanceNegative(0.0175, 0.025)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+17%", true, resistancePositive(0.12, 0.17)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-2.5%", false, resistanceNegative(0.0175, 0.025))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1,
                                    Manufactured.GALVANISINGALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-3.25%", false, resistanceNegative(0.025, 0.0325)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+22%", true, resistancePositive(0.17, 0.22)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-3.25%", false, resistanceNegative(0.025, 0.0325))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SHIELDPATTERNANALYSIS, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1,
                                    Manufactured.PHASEALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, resistanceNegative(0.0325, 0.04)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+27%", true, resistancePositive(0.22, 0.27)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, resistanceNegative(0.0325, 0.04))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    )
            ),
            HorizonsBlueprintType.RESISTANCE_AUGMENTED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+5%", true, resistancePositive(0.0, 0.05)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+5%", true, resistancePositive(0.0, 0.05)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+5%", true, resistancePositive(0.0, 0.05)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+5%", false, percentagePositive(0.0, 0.05)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.PHOSPHORUS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+8%", true, resistancePositive(0.05, 0.08)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+8%", true, resistancePositive(0.05, 0.08)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+8%", true, resistancePositive(0.05, 0.08)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.05, 0.1)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.04, 0.06))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.PHOSPHORUS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+11%", true, resistancePositive(0.08, 0.11)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+11%", true, resistancePositive(0.08, 0.11)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+11%", true, resistancePositive(0.08, 0.11)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.06, 0.08))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+14%", true, resistancePositive(0.11, 0.14)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+14%", true, resistancePositive(0.11, 0.14)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+14%", true, resistancePositive(0.11, 0.14)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.08, 0.1))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.IMPERIALSHIELDING, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+17%", true, resistancePositive(0.14, 0.17)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("+17%", true, resistancePositive(0.14, 0.17)),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("+17%", true, resistancePositive(0.14, 0.17)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.1, 0.12))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    )
            ),
            HorizonsBlueprintType.THERMAL_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-1%", false, resistanceNegative(0.0, 0.01)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-1%", false, resistanceNegative(0.0, 0.01)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+7%", true, resistancePositive(0.0, 0.07))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-1.75%", false, resistanceNegative(0.01, 0.0175)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-1.75%", false, resistanceNegative(0.01, 0.0175)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+12%", true, resistancePositive(0.07, 0.12))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-2.5%", false, resistanceNegative(0.0175, 0.025)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-2.5%", false, resistanceNegative(0.0175, 0.025)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+17%", true, resistancePositive(0.12, 0.17))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-3.25%", false, resistanceNegative(0.025, 0.0325)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-3.25%", false, resistanceNegative(0.025, 0.0325)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+22%", true, resistancePositive(0.17, 0.22))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SHIELDPATTERNANALYSIS, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1,
                                    Manufactured.HEATEXCHANGERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, resistanceNegative(0.0325, 0.04)),
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsNumberModifierValue("-4%", false, resistanceNegative(0.0325, 0.04)),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsNumberModifierValue("+27%", true, resistancePositive(0.22, 0.27))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.DIDI_VATERMANN
                            )
                    )
            )
    );

}
