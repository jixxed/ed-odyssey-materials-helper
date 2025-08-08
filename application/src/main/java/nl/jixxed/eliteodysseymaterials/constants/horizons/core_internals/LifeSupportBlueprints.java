package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

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
public class LifeSupportBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(

            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(

                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.10, 0.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.20, 0.30)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.30, 0.40)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.65, 0.75))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.40, 0.50)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-85%", true, percentageNegative(0.75, 0.85))
                            ),
                            List.of(Engineer.ETIENNE_DORN)
                    )
            ),
            HorizonsBlueprintType.REINFORCED,
            Map.of(

                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.0, 0.60)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.0, 0.30))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.60, 1.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.30, 0.60))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(1.20, 1.80)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+90%", false, percentagePositive(0.60, 0.90))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%", true, percentagePositive(1.80, 2.40)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+120%", false, percentagePositive(0.90, 1.20))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.40, 3.00)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+150%", false, percentagePositive(1.20, 1.50))
                            ),
                            List.of(Engineer.ETIENNE_DORN)
                    )
            ),
            HorizonsBlueprintType.SHIELDED,
            Map.of(

                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.0, 0.60))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.20, 0.40)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.60, 1.20))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.40, 0.60)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(0.90, 1.80))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.60, 0.80)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%", true, percentagePositive(1.20, 2.40))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.LIFE_SUPPORT, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.80, 1.00)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.40, 3.00))
                            ),
                            List.of(Engineer.ETIENNE_DORN)
                    )
            ));
}
