package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class ShieldCellBankBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.RAPID_CHARGE,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DURATION, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.SPIN_UP_TIME, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+5%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.GRIDRESISTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DURATION, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.SPIN_UP_TIME, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("+15%", false),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+10%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.HYBRIDCAPACITORS, 1,
                                    Manufactured.PRECIPITATEDALLOYS, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DURATION, new HorizonsModifierValue("-18%", false),
                                    HorizonsModifier.SPIN_UP_TIME, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+15%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.RAPID_CHARGE, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Manufactured.THERMICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DURATION, new HorizonsModifierValue("-24%", false),
                                    HorizonsModifier.SPIN_UP_TIME, new HorizonsModifierValue("-40%", true),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("+25%", false),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+20%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON))),
            HorizonsBlueprintType.SPECIALISED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-8%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-6%", true),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+4%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.ELVIRA_MARTUUK, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+15%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-16%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-12%", true),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+6%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Encoded.SCRAMBLEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-24%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-18%", true),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+8%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.SHIELD_CELL_BANK, HorizonsBlueprintType.SPECIALISED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Raw.YTTRIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+25%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.BOOT_TIME, new HorizonsModifierValue("-32%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-24%", true),
                                    HorizonsModifier.SHIELD_REINFORCEMENT, new HorizonsModifierValue("+10%", true)
                            ),
                            List.of(Engineer.MEL_BRANDON))));
}
