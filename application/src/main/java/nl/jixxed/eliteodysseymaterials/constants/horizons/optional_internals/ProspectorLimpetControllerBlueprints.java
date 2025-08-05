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
@SuppressWarnings("java:S1192")
public class ProspectorLimpetControllerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.MARSHA_HICKS,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.MARSHA_HICKS,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.65, 0.75)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-85%", true, percentageNegative(0.75, 0.85)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE
                            )
                    )
            ),
            HorizonsBlueprintType.REINFORCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.0, 0.3)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.0, 0.6))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.3, 0.6)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.6, 1.2))
                            ),
                            List.of(
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.MARSHA_HICKS,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+90%", false, percentagePositive(0.6, 0.9)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(1.2, 1.8))
                            ),
                            List.of(
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.MARSHA_HICKS,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.ZINC, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+120%", false, percentagePositive(0.9, 1.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%", true, percentagePositive(1.8, 2.4))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+150%", false, percentagePositive(1.2, 1.5)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.4, 3.0))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE
                            )
                    )
            ),
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.0, 0.6))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.2, 0.4)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.6, 1.2))
                            ),
                            List.of(
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.MARSHA_HICKS,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(1.2, 1.8))
                            ),
                            List.of(
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.MARSHA_HICKS,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%", true, percentagePositive(1.8, 2.4))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE,
                                    Engineer.RAM_TAH
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.PROSPECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.4, 3.0))
                            ),
                            List.of(
                                    Engineer.MARSHA_HICKS,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.THE_SARGE
                            )
                    )
            )
    );

}
