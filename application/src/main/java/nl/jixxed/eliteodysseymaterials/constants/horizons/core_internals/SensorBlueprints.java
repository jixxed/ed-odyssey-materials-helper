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
public class SensorBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.LONG_RANGE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("+15%", true, percentagePositive(0.0, 0.15)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.20))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.15, 0.30)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.20, 0.40))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1, Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.20)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("+45%", true, percentagePositive(0.30, 0.45)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.40, 0.60))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1, Manufactured.ELECTROCHEMICALARRAYS, 1, Encoded.DECODEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.20, 0.25)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.45, 0.60)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.60, 0.80))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1, Manufactured.POLYMERCAPACITORS, 1, Encoded.COMPACTEMISSIONSDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.25, 0.30)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("+75%", true, percentagePositive(0.60, 0.75)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.80, 1.00))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG))
            ),
            HorizonsBlueprintType.WIDE_ANGLE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.40)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.10)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("-4%", false, percentageNegative(0.0, 0.04))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.40, 0.80)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.10, 0.20)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("-8%", false, percentageNegative(0.04, 0.08))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.GERMANIUM, 1, Manufactured.MECHANICALSCRAP, 1, Encoded.SCANDATABANKS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.80, 1.20)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.20, 0.30)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("-12%", false, percentageNegative(0.08, 0.12))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1, Raw.NIOBIUM, 1, Encoded.ENCODEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+160%", true, percentagePositive(1.20, 1.60)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.30, 0.40)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("-16%", false, percentageNegative(0.12, 0.16))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1, Raw.TIN, 1, Encoded.CLASSIFIEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.60, 2.00)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.40, 0.50)),
                                    HorizonsModifier.MAX_RANGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.16, 0.20))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG))
            ),
            HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.0, 0.05)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.10)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.0, 0.20))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 1, Raw.MANGANESE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.05, 0.10)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.10, 0.20)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.20, 0.35))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 1, Raw.MANGANESE, 1, Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.10, 0.15)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.20, 0.30)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.35, 0.50))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1, Manufactured.PHASEALLOYS, 1, Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.20)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.30, 0.40)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.50, 0.65))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1, Manufactured.PROTOLIGHTALLOYS, 1, Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.20, 0.25)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.40, 0.50)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-80%", true, percentageNegative(0.65, 0.80))
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK, Engineer.BILL_TURNER, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG))
            )
    );
}
