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
public class ManifestScannerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.FAST_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-20%", true, percentageNegative(0.05, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("-5%", false, percentageNegative(0.0, 0.05))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.PHOSPHORUS, 1,
                                    Manufactured.UNCUTFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-35%", true, percentageNegative(0.2, 0.35)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.05, 0.1))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.PHOSPHORUS, 1,
                                    Encoded.SYMMETRICKEYS, 1,
                                    Manufactured.UNCUTFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-50%", true, percentageNegative(0.35, 0.5)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.1, 0.15))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Encoded.ENCRYPTIONARCHIVES, 1,
                                    Manufactured.FOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.5, 0.65)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.2))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.FAST_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.ARSENIC, 1,
                                    Encoded.ADAPTIVEENCRYPTORS, 1,
                                    Manufactured.REFINEDFOCUSCRYSTALS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("-80%", true, percentageNegative(0.65, 0.8)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.2, 0.25))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    )
            ),
            HorizonsBlueprintType.LIGHTWEIGHT,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.PHOSPHORUS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.0, 0.1)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-45%", true, percentageNegative(0.35, 0.45))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.1, 0.2)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-55%", true, percentageNegative(0.45, 0.55))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.MANGANESE, 1,
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.SALVAGEDALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.2, 0.3)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-65%", true, percentageNegative(0.55, 0.65))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.CONDUCTIVECOMPONENTS, 1,
                                    Manufactured.PHASEALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-40%", false, percentageNegative(0.3, 0.4)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-75%", true, percentageNegative(0.65, 0.75))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LIGHTWEIGHT, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.CONDUCTIVECERAMICS, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("-50%", false, percentageNegative(0.4, 0.5)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("-85%", true, percentageNegative(0.75, 0.85))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    )
            ),
            HorizonsBlueprintType.LONG_RANGE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.IRON, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("-10%", false, percentageNegative(0.05, 0.1)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("+24%", true, percentagePositive(0.0, 0.24)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.IRON, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("-15%", false, percentageNegative(0.1, 0.15)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("+48%", true, percentagePositive(0.24, 0.48)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.IRON, 1,
                                    Encoded.EMISSIONDATA, 1,
                                    Manufactured.HYBRIDCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("-20%", false, percentageNegative(0.15, 0.2)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("+72%", true, percentagePositive(0.48, 0.72)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.DECODEDEMISSIONDATA, 1,
                                    Manufactured.ELECTROCHEMICALARRAYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("-25%", false, percentageNegative(0.2, 0.25)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("+96%", true, percentagePositive(0.72, 0.96)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.LONG_RANGE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.COMPACTEMISSIONSDATA, 1,
                                    Manufactured.POLYMERCAPACITORS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("-30%", false, percentageNegative(0.25, 0.3)),
                                    HorizonsModifier.SCANNER_RANGE, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.96, 1.2)),
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.4, 0.5))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    )
            ),
            HorizonsBlueprintType.REINFORCED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Raw.NICKEL, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.0, 0.6)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.0, 0.3))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.6, 1.2)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.3, 0.6))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.NICKEL, 1,
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(1.2, 1.8)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+90%", false, percentagePositive(0.6, 0.9))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.MOLYBDENUM, 1,
                                    Raw.ZINC, 1,
                                    Raw.TUNGSTEN, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%", true, percentagePositive(1.8, 2.4)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+120%", false, percentagePositive(0.9, 1.2))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.REINFORCED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TECHNETIUM, 1,
                                    Raw.MOLYBDENUM, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.4, 3.0)),
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+150%", false, percentagePositive(1.2, 1.5))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    )
            ),
            HorizonsBlueprintType.SHIELDED,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.WORNSHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+60%", true, percentagePositive(0.0, 0.6))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.2, 0.4)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.6, 1.2))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.CARBON, 1,
                                    Manufactured.SHIELDEMITTERS, 1,
                                    Manufactured.HIGHDENSITYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+180%", true, percentagePositive(1.2, 1.8))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.VANADIUM, 1,
                                    Manufactured.SHIELDINGSENSORS, 1,
                                    Manufactured.FEDPROPRIETARYCOMPOSITES, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+240%", true, percentagePositive(1.8, 2.4))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.SHIELDED, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TUNGSTEN, 1,
                                    Manufactured.FEDCORECOMPOSITES, 1,
                                    Manufactured.COMPOUNDSHIELDING, 1
                            ),
                            Map.of(
                                    HorizonsModifier.POWER_DRAW, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.INTEGRITY, new HorizonsNumberModifierValue("+300%", true, percentagePositive(2.4, 3.0))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    )
            ),
            HorizonsBlueprintType.WIDE_ANGLE_SCANNER,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.0, 0.2)),
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.0, 0.4)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+10%", false, percentagePositive(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.2, 0.4)),
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("+80%", true, percentagePositive(0.4, 0.8)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+20%", false, percentagePositive(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Encoded.SCANDATABANKS, 1,
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+60%", false, percentagePositive(0.4, 0.6)),
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("+120%", true, percentagePositive(0.8, 1.2)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+30%", false, percentagePositive(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.BILL_TURNER,
                                    Engineer.LORI_JAMESON,
                                    Engineer.JURI_ISHMAAK
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Encoded.ENCODEDSCANDATA, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+80%", false, percentagePositive(0.6, 0.8)),
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("+160%", true, percentagePositive(1.2, 1.6)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+40%", false, percentagePositive(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.MANIFEST_SCANNER, HorizonsBlueprintType.WIDE_ANGLE_SCANNER, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TIN, 1,
                                    Encoded.CLASSIFIEDSCANDATA, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.MASS, new HorizonsNumberModifierValue("+100%", false, percentagePositive(0.8, 1.0)),
                                    HorizonsModifier.MAX_ANGLE, new HorizonsNumberModifierValue("+200%", true, percentagePositive(1.6, 2.0)),
                                    HorizonsModifier.SCAN_TIME, new HorizonsNumberModifierValue("+50%", false, percentagePositive(0.4, 0.5))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.TIANA_FORTUNE
                            )
                    )
            )
    );

}
