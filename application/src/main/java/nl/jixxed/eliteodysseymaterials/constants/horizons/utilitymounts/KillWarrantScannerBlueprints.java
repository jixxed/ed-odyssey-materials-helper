package nl.jixxed.eliteodysseymaterials.constants.horizons.utilitymounts;

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
public class KillWarrantScannerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0D, 0.1)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0D, 0.45))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.65, 0.75))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-85%", true, percentageNegative(0.75, 0.85))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.REINFORCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0D, 0.6)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0D, 0.3))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.6, 1.2)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.3, 0.6))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(1.2, 1.8)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+90%", false, percentagePositive(0.6, 0.9))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TUNGSTEN, 1,
                                    Raw.ZINC, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%",  true, percentagePositive(1.8, 2.4)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+120%", false, percentagePositive(0.9, 1.2))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Raw.TECHNETIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%",  true, percentagePositive(2.4, 3.0)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+150%", false, percentagePositive(1.2, 1.5))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0/100D,20/100D)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0/100D,60/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(20/100D,40/100D)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(60/100D,120/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+60%", false, percentagePositive(40/100D,60/100D)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(120/100D,180/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Raw.VANADIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+80%", false, percentagePositive(60/100D,80/100D)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%", true, percentagePositive(180/100D,240/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.COMPOUNDSHIELDING, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+100%", false, percentagePositive(80/100D,100/100D)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(240/100D,300/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.LONG_RANGE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0/100D,10/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0/100D,24/100D)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0/100D,10/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(10/100D,15/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("+48%", true, percentagePositive(24/100D,48/100D)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(10/100D,20/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1, Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(15/100D,20/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("+72%", true, percentagePositive(48/100D,72/100D)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(20/100D,30/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1, Manufactured.ELECTROCHEMICALARRAYS, 1, Encoded.DECODEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-25%", false, percentageNegative(20/100D,25/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("+96%", true, percentagePositive(72/100D,96/100D)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(30/100D,40/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1, Manufactured.POLYMERCAPACITORS, 1, Encoded.COMPACTEMISSIONSDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(25/100D,30/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("+120%", true, percentagePositive(96/100D,120/100D)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+50%", false, percentagePositive(40/100D,50/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.WIDE_ANGLE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0/100D,40/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0/100D,10/100D)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0/100D,20/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+80%", true, percentagePositive(40/100D,80/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+20%", false, percentagePositive(10/100D,20/100D)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(20/100D,40/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1, Encoded.SCANDATABANKS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+120%", true, percentagePositive(80/100D,120/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+30%", false, percentagePositive(20/100D,30/100D)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(40/100D,60/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1, Raw.NIOBIUM, 1, Encoded.ENCODEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+160%", true, percentagePositive(120/100D,160/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+40%", false, percentagePositive(30/100D,40/100D)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(60/100D,80/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1, Raw.TIN, 1, Encoded.CLASSIFIEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+200%", true, percentagePositive(160/100D,200/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+50%", false, percentagePositive(40/100D,50/100D)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(80/100D,100/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))),
            HorizonsBlueprintType.FAST_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0/100D,10/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0/100D,5/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0/100D,20/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.PHOSPHORUS, 1, Manufactured.UNCUTFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(10/100D,20/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(5/100D,10/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-35%", true, percentageNegative(20/100D,35/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.PHOSPHORUS, 1, Manufactured.UNCUTFOCUSCRYSTALS, 1, Encoded.SYMMETRICKEYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(20/100D,30/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(10/100D,15/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-50%", true, percentageNegative(35/100D,50/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE, Engineer.BILL_TURNER, Engineer.LORI_JAMESON)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MANGANESE, 1, Manufactured.FOCUSCRYSTALS, 1, Encoded.ENCRYPTIONARCHIVES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(30/100D,40/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(15/100D,20/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-65%", true, percentageNegative(50/100D,65/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.KILL_WARRANT_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ARSENIC, 1, Manufactured.REFINEDFOCUSCRYSTALS, 1, Encoded.ADAPTIVEENCRYPTORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(40/100D,50/100D)),
                                    HorizonsModifier.SCAN_RANGE, new HorizonsNumberModifierValue("-25%", false, percentageNegative(20/100D,25/100D)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-80%", true, percentageNegative(65/100D,80/100D))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.TIANA_FORTUNE))));
}
