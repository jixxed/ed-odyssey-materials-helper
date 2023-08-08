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
public class KillWarrantScannerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-45%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-56%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-65%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-75%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-85%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.REINFORCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+180%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+90%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+240%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+120%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+150%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+60%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+120%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+60%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+180%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+80%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+240%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+100%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("+300%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.LONG_RANGE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("+24%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("+48%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1, Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("+72%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+30%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1, Manufactured.ELECTROCHEMICALARRAYS, 1, Encoded.DECODEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-25%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("+96%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1, Manufactured.POLYMERCAPACITORS, 1, Encoded.COMPACTEMISSIONSDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+50%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.WIDE_ANGLE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+80%", true),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1, Encoded.SCANDATABANKS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("+30%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1, Raw.NIOBIUM, 1, Encoded.ENCODEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+160%", true),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+80%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1, Raw.TIN, 1, Encoded.CLASSIFIEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+200%", true),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("+50%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+100%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.FAST_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("-20%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.PHOSPHORUS, 1, Manufactured.UNCUTFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("-35%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.PHOSPHORUS, 1, Manufactured.UNCUTFOCUSCRYSTALS, 1, Encoded.SYMMETRICKEYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("-50%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MANGANESE, 1, Manufactured.FOCUSCRYSTALS, 1, Encoded.ENCRYPTIONARCHIVES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("-65%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ARSENIC, 1, Manufactured.REFINEDFOCUSCRYSTALS, 1, Encoded.ADAPTIVEENCRYPTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsModifierValue("-25%", false),
                                    HorizonsModifier.SCAN_TIME, new HorizonsModifierValue("-80%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))));
}
