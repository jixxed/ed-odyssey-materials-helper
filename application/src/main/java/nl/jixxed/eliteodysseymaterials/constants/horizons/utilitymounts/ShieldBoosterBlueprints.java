package nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

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
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+8%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+12%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+17%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+22%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.BLAST_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.SHIELDPATTERNANALYSIS, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("+27%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN))),
            HorizonsBlueprintType.HEAVY_DUTY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+3%", true),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsModifierValue("+10%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+100%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+6%", true),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsModifierValue("+17%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+150%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.SHIELDCYCLERECORDINGS, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1,
                                    Raw.NIOBIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+15%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+9%", true),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsModifierValue("+24%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+200%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Encoded.SHIELDSOAKANALYSIS, 1,
                                    Raw.TIN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsModifierValue("+31%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+250%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.HEAVY_DUTY, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ANTIMONY, 1,
                                    Manufactured.POLYMERCAPACITORS, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+25%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.SHIELD_BOOST, new HorizonsModifierValue("+38%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+300%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN))),
            HorizonsBlueprintType.KINETIC_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+8%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-1%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-1%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+17%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-2%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.GALVANISINGALLOYS, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+22%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-3%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.KINETIC_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Encoded.SHIELDPATTERNANALYSIS, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("+27%", true),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-4%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN))),
            HorizonsBlueprintType.RESISTANCE_AUGMENTED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+5%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+5%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+8%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+15%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-8%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+11%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+15%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.RESISTANCE_AUGMENTED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.IMPERIALSHIELDING, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+25%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.ALL_RESISTANCES, new HorizonsModifierValue("+17%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN))),
            HorizonsBlueprintType.THERMAL_RESISTANT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+8%", true),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-1%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+12%", true),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-1%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.FOCUSCRYSTALS, 1,
                                    Manufactured.HEATCONDUCTIONWIRING, 1,
                                    Manufactured.HEATDISPERSIONPLATE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+17%", true),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-2%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LEI_CHEUNG, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.HEATDISPERSIONPLATE, 1,
                                    Encoded.SHIELDDENSITYREPORTS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+22%", true),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-3%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_BOOSTER, HorizonsBlueprintType.THERMAL_RESISTANT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HEATEXCHANGERS, 1,
                                    Encoded.SHIELDPATTERNANALYSIS, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.KINETIC_RESISTANCE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.THERMAL_RESISTANCE, new HorizonsModifierValue("+27%", true),
                                    HorizonsModifier.EXPLOSIVE_RESISTANCE, new HorizonsModifierValue("-4%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.DIDI_VATERMANN))));
}
