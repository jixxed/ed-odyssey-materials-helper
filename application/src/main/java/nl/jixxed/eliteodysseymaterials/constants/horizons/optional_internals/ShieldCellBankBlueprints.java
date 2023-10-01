package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

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
public class ShieldCellBankBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.RAPID_CHARGE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SHIELDBANK_SPIN_UP, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+5%", true, percentagePositive(0.0, 0.05)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.SHIELDBANK_DURATION, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.0, 0.06))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LORI_JAMESON
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SHIELDBANK_SPIN_UP, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.1, 0.2)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.05, 0.1)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15)),
                                    HorizonsModifier.SHIELDBANK_DURATION, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.06, 0.12))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LORI_JAMESON
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.SULPHUR, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SHIELDBANK_SPIN_UP, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.2, 0.3)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.1, 0.15)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                    HorizonsModifier.SHIELDBANK_DURATION, new HorizonsNumberModifierValue("-18%", false, percentageNegative(0.12, 0.18))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LORI_JAMESON
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Manufactured.THERMICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SHIELDBANK_SPIN_UP, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.3, 0.4)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.15, 0.2)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.SHIELDBANK_DURATION, new HorizonsNumberModifierValue("-24%", false, percentageNegative(0.18, 0.24))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON
                            )
                    )
            ),
            HorizonsBlueprintType.SPECIALISED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.0, 0.05)),
                                    HorizonsModifier.SHIELDBANK_HEAT, new HorizonsNumberModifierValue("-6%", true, percentageNegative(0.0, 0.06)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-8%", true, percentageNegative(0.0, 0.08)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+4%", true, percentagePositive(0.0, 0.04))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.ELVIRA_MARTUUK,
                                    Engineer.LORI_JAMESON
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.05, 0.1)),
                                    HorizonsModifier.SHIELDBANK_HEAT, new HorizonsNumberModifierValue("-12%", true, percentageNegative(0.06, 0.12)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-16%", true, percentageNegative(0.08, 0.16)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+6%", true, percentagePositive(0.04, 0.06))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LORI_JAMESON
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.SCRAMBLEDEMISSIONDATA, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.1, 0.15)),
                                    HorizonsModifier.SHIELDBANK_HEAT, new HorizonsNumberModifierValue("-18%", true, percentageNegative(0.12, 0.18)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-24%", true, percentageNegative(0.16, 0.24)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+8%", true, percentagePositive(0.06, 0.08))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.LORI_JAMESON
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.YTTRIUM, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.2)),
                                    HorizonsModifier.SHIELDBANK_HEAT, new HorizonsNumberModifierValue("-24%", true, percentageNegative(0.18, 0.24)),
                                    HorizonsModifier.BOOT_TIME, new HorizonsNumberModifierValue("-32%", true, percentageNegative(0.24, 0.32)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.SHIELDBANK_REINFORCEMENT, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.08, 0.1))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON
                            )
                    )
            )
    );

}
