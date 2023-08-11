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
public class FSDInterdictorBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.4)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.COLONEL_BRIS_DEKKER,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.ENCRYPTEDFILES, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.1, 0.15))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.COLONEL_BRIS_DEKKER,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.ENCRYPTIONCODES, 1,
                                    Manufactured.GRIDRESISTORS, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.COLONEL_BRIS_DEKKER,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.WAKESOLUTIONS, 1,
                                    Encoded.ENCODEDSCANDATA, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.2, 0.25))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.COLONEL_BRIS_DEKKER
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.HYPERSPACETRAJECTORIES, 1,
                                    Encoded.CLASSIFIEDSCANDATA, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("+120%", true, percentagePositive(1.0, 1.2)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.25, 0.3))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON
                            )
                    )
            ),
            HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.ENCRYPTEDFILES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.2))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.COLONEL_BRIS_DEKKER,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.DISRUPTEDWAKEECHOES, 1,
                                    Encoded.ENCRYPTIONCODES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.1, 0.15)),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.1, 0.15)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.COLONEL_BRIS_DEKKER,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Encoded.SYMMETRICKEYS, 1,
                                    Encoded.FSDTELEMETRY, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.15, 0.2)),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.2)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.COLONEL_BRIS_DEKKER,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.WAKESOLUTIONS, 1,
                                    Encoded.ENCRYPTIONARCHIVES, 1,
                                    Encoded.SCANARCHIVES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+25%", false, percentagePositive(0.2, 0.25)),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON,
                                    Engineer.COLONEL_BRIS_DEKKER
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.HYPERSPACETRAJECTORIES, 1,
                                    Encoded.SCANDATABANKS, 1,
                                    Encoded.ADAPTIVEENCRYPTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.25, 0.3)),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.25, 0.3)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.RANGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6))
                            ),
                            List.of(
                                    Engineer.MEL_BRANDON
                            )
                    )
            )
    );

}
