package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

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
public class TorpedoPylonBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LIGHTWEIGHT_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.15, 0.3)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.3, 0.45)),
//                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-60%", true, percentageNegative(0.45, 0.6)),
//                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.6, 0.75)),
//                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.LIZ_RYDER
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.PROTORADIOLICALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-90%", true, percentageNegative(0.75, 0.9)),
//                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.3, 0.35)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-60%", false, percentageNegative(0.5, 0.6)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-40%", true, percentageNegative(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.LIZ_RYDER
                            )
                    )
            ),
            HorizonsBlueprintType.STURDY_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+100%", true, percentagePositive(0.5, 1.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.1, 0.2)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.05, 0.1))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.2, 0.4)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+150%", true, percentagePositive(1.0, 1.5)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.2, 0.3)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.1, 0.15))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.5, 2.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.15, 0.2))
                            ),
                            List.of(
                                    Engineer.LIZ_RYDER,
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.ZINC, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+250%", true, percentagePositive(2.0, 2.5)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.2, 0.25))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.LIZ_RYDER
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.5, 3.0)),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.5, 0.6)),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.3))
                            ),
                            List.of(
                                    Engineer.PETRA_OLMANOVA,
                                    Engineer.LIZ_RYDER
                            )
                    )
            )
    );

}
