package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.constants.UTF8Constants;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBooleanModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PowerDistributorBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.CHARGE_ENHANCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.0, 0.09)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.0, 0.09)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+9%", true, percentagePositive(0.0, 0.09))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CHEMICALPROCESSORS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+18%", true, percentagePositive(0.09, 0.18)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+18%", true, percentagePositive(0.09, 0.18)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+18%", true, percentagePositive(0.09, 0.18))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CHEMICALDISTILLERY, 1,
                                    Manufactured.GRIDRESISTORS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+27%", true, percentagePositive(0.18, 0.27)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+27%", true, percentagePositive(0.18, 0.27)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+27%", true, percentagePositive(0.18, 0.27))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.27, 0.36)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.27, 0.36)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+36%", true, percentagePositive(0.27, 0.36))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+45%", true, percentagePositive(0.36, 0.45)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+45%", true, percentagePositive(0.36, 0.45)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+45%", true, percentagePositive(0.36, 0.45))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    )
            ),
            HorizonsBlueprintType.ENGINE_FOCUSED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.0, 0.16))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.20, 0.30)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+23%", true, percentagePositive(0.16, 0.23))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Raw.CHROMIUM, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.30, 0.40)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.23, 0.30))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SCANARCHIVES, 1,
                                    Raw.SELENIUM, 1,
                                    Manufactured.POLYMERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.40, 0.50)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+37%", true, percentagePositive(0.30, 0.37))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SCANDATABANKS, 1,
                                    Raw.CADMIUM, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.50, 0.60)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("+44%", true, percentagePositive(0.37, 0.44))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    )
            ),
            HorizonsBlueprintType.HIGH_CHARGE_CAPACITY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.0, 0.02)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.10))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.10, 0.15)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.02, 0.06)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.02, 0.06)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.02, 0.06)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+18%", true, percentagePositive(0.10, 0.18)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+18%", true, percentagePositive(0.10, 0.18)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+18%", true, percentagePositive(0.10, 0.18))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.15, 0.20)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.06, 0.10)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.06, 0.10)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.06, 0.10)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.18, 0.26)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.18, 0.26)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+26%", true, percentagePositive(0.18, 0.26))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+25%", true, percentagePositive(0.20, 0.25)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+34%", true, percentagePositive(0.26, 0.34)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+34%", true, percentagePositive(0.26, 0.34)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+34%", true, percentagePositive(0.26, 0.34))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.25, 0.30)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-18%", false, percentageNegative(0.15, 0.18)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-18%", false, percentageNegative(0.15, 0.18)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-18%", false, percentageNegative(0.15, 0.18)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("+42%", true, percentagePositive(0.34, 0.42)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+42%", true, percentagePositive(0.34, 0.42)),
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+42%", true, percentagePositive(0.34, 0.42))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    )
            ),
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-10%", true, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.40)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+3%", false, percentagePositive(0.0, 0.03))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-15%", true, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.40, 0.80)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+6%", false, percentagePositive(0.03, 0.06))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.15, 0.20)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.80, 1.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+9%", false, percentagePositive(0.06, 0.09))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-25%", true, percentageNegative(0.20, 0.25)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+160%", true, percentagePositive(1.20, 1.60)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+12%", false, percentagePositive(0.09, 0.12))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("-30%", true, percentageNegative(0.25, 0.30)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.60, 2.00)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+15%", false, percentagePositive(0.12, 0.15))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    )
            ),
            HorizonsBlueprintType.SYSTEM_FOCUSED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.0, 0.16)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+23%", true, percentagePositive(0.16, 0.23)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.20, 0.30)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Raw.CHROMIUM, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.23, 0.30)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.30, 0.40)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.09, 0.09)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SCANARCHIVES, 1,
                                    Raw.SELENIUM, 1,
                                    Manufactured.POLYMERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+37%", true, percentagePositive(0.30, 0.37)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.40, 0.50)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.12, 0.12)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(
                            HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SCANDATABANKS, 1,
                                    Raw.CADMIUM, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("+44%", true, percentagePositive(0.37, 0.44)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.50, 0.60)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.15, 0.15)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)
                    )
            ),
            HorizonsBlueprintType.WEAPON_FOCUSED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.0, 0.20)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-1%", false, percentageNegative(0.0, 0.01)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+16%", true, percentagePositive(0.0, 0.16)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.0, 0.03))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.20, 0.30)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-2%", false, percentageNegative(0.01, 0.02)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+23%", true, percentagePositive(0.16, 0.23)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-6%", false, percentageNegative(0.03, 0.06))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.30, 0.40)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-3%", false, percentageNegative(0.02, 0.03)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.23, 0.30)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-9%", false, percentageNegative(0.06, 0.09))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SCANARCHIVES, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Raw.CADMIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.40, 0.50)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.03, 0.04)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+37%", true, percentagePositive(0.30, 0.37)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.09, 0.12))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SCANDATABANKS, 1,
                                    Manufactured.POLYMERCAPACITORS, 1,
                                    Raw.TELLURIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.50, 0.60)),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.04, 0.05)),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15)),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsNumberModifierValue("+44%", true, percentagePositive(0.37, 0.44)),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.12, 0.15))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER))
            ));
            public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> GUARDIAN_BLUEPRINTS = Map.of(
                    HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE,
                    Map.of(
                            HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.POWER_DISTRIBUTOR, HorizonsBlueprintType.ANTI_GUARDIAN_ZONE_RESISTANCE, HorizonsBlueprintGrade.GRADE_1,
                                    Map.of(
                                            Manufactured.TG_ABRASION03,2,
                                            Manufactured.TG_CAUSTICCRYSTAL,1
                                    ),
                                    Map.of(
                                            HorizonsModifier.ANTI_GUARDIAN_ZONE_RESISTANCE, new HorizonsBooleanModifierValue(UTF8Constants.CHECK_TRUE, true, bool(Boolean.TRUE))
                                    ),
                                    List.of(
                                            Engineer.RAM_TAH
                                    ),
                                    GameVersion.LIVE
                            )
                    )
            );
}
