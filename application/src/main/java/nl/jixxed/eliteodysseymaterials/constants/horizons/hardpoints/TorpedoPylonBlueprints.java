package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

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
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-30%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-45%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-60%", true)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-75%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.LIZ_RYDER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.LIGHTWEIGHT_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-40%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-60%", false),
                                    HorizonsModifier.DISTRIBUTOR_DRAW, new HorizonsModifierValue("-35%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-90%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.LIZ_RYDER))),
            HorizonsBlueprintType.STURDY_MOUNT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+100%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+150%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-15%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+200%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.LIZ_RYDER, Engineer.PETRA_OLMANOVA, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+250%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+80%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.LIZ_RYDER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.TORPEDO_PYLON, HorizonsBlueprintType.STURDY_MOUNT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true),
                                    HorizonsModifier.THERMAL_LOAD, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.ARMOUR_PIERCING, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+100%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.LIZ_RYDER))));
}
