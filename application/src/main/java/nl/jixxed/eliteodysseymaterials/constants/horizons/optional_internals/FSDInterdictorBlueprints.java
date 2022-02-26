package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class FSDInterdictorBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-10%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.COLONEL_BRIS_DEKKER, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1,
                                    Encoded.ENCRYPTEDFILES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-15%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.COLONEL_BRIS_DEKKER, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.GRIDRESISTORS, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Encoded.ENCRYPTIONCODES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+30%", false),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("+80%", true),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-20%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.COLONEL_BRIS_DEKKER, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.ENCODEDSCANDATA, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1,
                                    Encoded.WAKESOLUTIONS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-25%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.COLONEL_BRIS_DEKKER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.EXPANDED_FSD_INTERDICTOR_CAPTURE_ARC, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.CLASSIFIEDSCANDATA, 1,
                                    Encoded.HYPERSPACETRAJECTORIES, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+50%", false),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-30%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON))),
            HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.ENCRYPTEDFILES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+10%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.FELICITY_FARSEER, Engineer.COLONEL_BRIS_DEKKER, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Encoded.DISRUPTEDWAKEECHOES, 1,
                                    Encoded.ENCRYPTIONCODES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+15%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.COLONEL_BRIS_DEKKER, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Encoded.FSDTELEMETRY, 1,
                                    Encoded.SYMMETRICKEYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+30%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.COLONEL_BRIS_DEKKER, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SCANARCHIVES, 1,
                                    Encoded.WAKESOLUTIONS, 1,
                                    Encoded.ENCRYPTIONARCHIVES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("-25%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+25%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON, Engineer.COLONEL_BRIS_DEKKER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.FRAME_SHIFT_DRIVE_INTERDICTOR, HorizonsBlueprintType.LONG_RANGE_FSD_INTERDICTOR, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SCANDATABANKS, 1,
                                    Encoded.HYPERSPACETRAJECTORIES, 1,
                                    Encoded.ADAPTIVEENCRYPTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+50%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.FACING_LIMIT, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.MEL_BRANDON))));
}
