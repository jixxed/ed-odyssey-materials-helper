package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class PowerDistributorBlueprints {
    public static final Map<HorizonsBlueprintModificationType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintModificationType.CHARGE_ENHANCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("+9%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CHEMICALPROCESSORS, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("+18%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CHEMICALDISTILLERY, 1,
                                    Manufactured.GRIDRESISTORS, 1,
                                    Encoded.CONSUMERFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("+27%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("+36%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.CHARGE_ENHANCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CHEMICALMANIPULATORS, 1,
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.EXQUISITEFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("+45%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER))),
            HorizonsBlueprintModificationType.ENGINE_FOCUSED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("+16%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("+23%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Raw.CHROMIUM, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("+30%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SCANARCHIVES, 1,
                                    Raw.SELENIUM, 1,
                                    Manufactured.POLYMERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("+37%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.ENGINE_FOCUSED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SCANDATABANKS, 1,
                                    Raw.CADMIUM, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("+44%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER))),
            HorizonsBlueprintModificationType.HIGH_CHARGE_CAPACITY,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+10%", true),
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("+10%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("+18%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CHROMIUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Encoded.LEGACYFIRMWARE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("+26%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.CONSUMERFIRMWARE, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+25%", true),
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("+34%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.HIGH_CHARGE_CAPACITY, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.INDUSTRIALFIRMWARE, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_RECHARGE, new HorizonsModifierValue("-18%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.POWER_CAPACITY, new HorizonsModifierValue("+42%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER))),
            HorizonsBlueprintModificationType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-10%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+3%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-15%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+80%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+6%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-20%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+9%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-25%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+160%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+12%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("-30%", true),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+200%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+15%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER))),
            HorizonsBlueprintModificationType.SYSTEM_FOCUSED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("+16%", true),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-1%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("+23%", true),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-2%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Raw.CHROMIUM, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-3%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SCANARCHIVES, 1,
                                    Raw.SELENIUM, 1,
                                    Manufactured.POLYMERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("+37%", true),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-4%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.SYSTEM_FOCUSED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SCANDATABANKS, 1,
                                    Raw.CADMIUM, 1,
                                    Manufactured.MILITARYSUPERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("+44%", true),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-5%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER))),
            HorizonsBlueprintModificationType.WEAPON_FOCUSED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("+20%", true),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-1%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("+16%", true),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-3%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Raw.SULPHUR, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-2%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-6%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("+23%", true),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-6%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Encoded.BULKSCANDATA, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1,
                                    Raw.SELENIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-3%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-9%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-9%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER, Engineer.MARCO_QWENT, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Encoded.SCANARCHIVES, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1,
                                    Raw.CADMIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("+50%", true),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-4%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-12%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("+37%", true),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-12%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintObjectName.POWER_DISTRIBUTOR, HorizonsBlueprintModificationType.WEAPON_FOCUSED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Encoded.SCANDATABANKS, 1,
                                    Manufactured.POLYMERCAPACITORS, 1,
                                    Raw.TELLURIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.WEAPONS_CAPACITY, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.SYSTEMS_RECHARGE, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.SYSTEMS_CAPACITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.ENGINES_CAPACITY, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.WEAPONS_RECHARGE, new HorizonsModifierValue("+44%", true),
                                    HorizonsModifier.ENGINES_RECHARGE, new HorizonsModifierValue("-15%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.THE_DWELLER))));
}
