package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class AutoFieldMaintenanceUnitBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true)
                            ),
                            List.of(Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.PETRA_OLMANOVA)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+60%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+180%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+80%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+240%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.AUTO_FIELD_MAINTENANCE_UNIT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+100%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true)
                            ),
                            List.of(Engineer.PETRA_OLMANOVA))));
}
