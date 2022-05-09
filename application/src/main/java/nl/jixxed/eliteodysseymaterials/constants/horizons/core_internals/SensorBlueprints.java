package nl.jixxed.eliteodysseymaterials.constants.horizons.core_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

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
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+15%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+30%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+40%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.IRON, 1, Manufactured.HYBRIDCAPACITORS, 1, Encoded.EMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+45%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+60%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1, Manufactured.ELECTROCHEMICALARRAYS, 1, Encoded.DECODEDEMISSIONDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-25%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+60%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+80%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1, Manufactured.POLYMERCAPACITORS, 1, Encoded.COMPACTEMISSIONSDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("+75%", true),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("+100%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG))),
            HorizonsBlueprintType.WIDE_ANGLE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+40%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+10%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-4%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+80%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+20%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-8%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.GERMANIUM, 1, Manufactured.MECHANICALSCRAP, 1, Encoded.SCANDATABANKS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+120%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+30%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-12%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1, Raw.NIOBIUM, 1, Encoded.ENCODEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+160%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+40%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-16%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1, Raw.TIN, 1, Encoded.CLASSIFIEDSCANDATA, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("+200%", true),
                                    HorizonsModifier.POWER_DRAW, new HorizonsModifierValue("+50%", false),
                                    HorizonsModifier.RANGE, new HorizonsModifierValue("-20%", false)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG))),
            HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-5%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-20%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 1, Raw.MANGANESE, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-10%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-35%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.SALVAGEDALLOYS, 1, Raw.MANGANESE, 1, Manufactured.CONDUCTIVECERAMICS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-15%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-30%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-50%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.CONDUCTIVECOMPONENTS, 1, Manufactured.PHASEALLOYS, 1, Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-20%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-40%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-65%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.SENSORS, HorizonsBlueprintType.LIGHT_WEIGHT_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1, Manufactured.PROTOLIGHTALLOYS, 1, Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_ANGLE, new HorizonsModifierValue("-25%", false),
                                    HorizonsModifier.INTEGRITY, new HorizonsModifierValue("-50%", false),
                                    HorizonsModifier.MASS, new HorizonsModifierValue("-80%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.LORI_JAMESON, Engineer.JURI_ISHMAAK, Engineer.BILL_TURNER, Engineer.TIANA_FORTUNE, Engineer.LEI_CHEUNG))));
}
