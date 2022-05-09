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
@SuppressWarnings("java:S1192")
public class HeatSinkLauncherBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.AMMO_CAPACITY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.AMMO_CAPACITY, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1,
                                    Raw.NIOBIUM, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.RELOAD_TIME, new HorizonsModifierValue("+50%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+100%", false),
                                    HorizonsModifier.AMMO_MAXIMUM, new HorizonsModifierValue("+50%", true)
                            ),
                            List.of(Engineer.RAM_TAH, Engineer.PETRA_OLMANOVA))),
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-45%", true)
                            ),
                            List.of(Engineer.RAM_TAH, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-56%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-65%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-75%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-85%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH))),
            HorizonsBlueprintType.REINFORCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.RAM_TAH, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+180%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+90%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+240%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+120%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+150%", false)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH))),
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true)
                            ),
                            List.of(Engineer.RAM_TAH, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+60%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+180%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+80%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+240%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.HEAT_SINK_LAUNCHER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+100%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.RAM_TAH))));
}
