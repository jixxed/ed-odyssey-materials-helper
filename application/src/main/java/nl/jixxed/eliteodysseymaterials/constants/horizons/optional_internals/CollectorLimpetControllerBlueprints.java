package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("java:S1192")
public class CollectorLimpetControllerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-45%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-55%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-65%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-75%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-85%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE))),
            HorizonsBlueprintType.REINFORCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.TIANA_FORTUNE, Engineer.MARSHA_HICKS, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+180%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+90%", false)
                            ),
                            List.of(Engineer.TIANA_FORTUNE, Engineer.MARSHA_HICKS, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+240%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+120%", false)
                            ),
                            List.of(Engineer.TIANA_FORTUNE, Engineer.MARSHA_HICKS, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+150%", false)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE))),
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+60%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+180%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+80%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+240%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.COLLECTOR_LIMPET_CONTROLLER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+100%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true)
                            ),
                            List.of(Engineer.MARSHA_HICKS, Engineer.TIANA_FORTUNE, Engineer.THE_SARGE))));
}
