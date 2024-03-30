package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsNumberModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

import static nl.jixxed.eliteodysseymaterials.helper.ModifierFunctionHelper.percentagePositive;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailedSurfaceScannerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsModuleBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DSS_PATCH_RADIUS, new HorizonsNumberModifierValue("+10%", true, percentagePositive(0.0, 0.1))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.BILL_TURNER,
                                    Engineer.JURI_ISHMAAK,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.LORI_JAMESON,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.FELICITY_FARSEER,
                                    Engineer.HERA_TANI
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsModuleBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DSS_PATCH_RADIUS, new HorizonsNumberModifierValue("+20%", true, percentagePositive(0.1, 0.2))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.BILL_TURNER,
                                    Engineer.JURI_ISHMAAK,
                                    Engineer.HERA_TANI,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.LORI_JAMESON,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.FELICITY_FARSEER
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsModuleBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Raw.GERMANIUM, 1,
                                    Manufactured.MECHANICALSCRAP, 1,
                                    Manufactured.PHASEALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DSS_PATCH_RADIUS, new HorizonsNumberModifierValue("+30%", true, percentagePositive(0.2, 0.3))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.BILL_TURNER,
                                    Engineer.JURI_ISHMAAK,
                                    Engineer.LORI_JAMESON,
                                    Engineer.TIANA_FORTUNE,
                                    Engineer.HERA_TANI,
                                    Engineer.LEI_CHEUNG,
                                    Engineer.FELICITY_FARSEER
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsModuleBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Raw.NIOBIUM, 1,
                                    Manufactured.MECHANICALEQUIPMENT, 1,
                                    Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DSS_PATCH_RADIUS, new HorizonsNumberModifierValue("+40%", true, percentagePositive(0.3, 0.4))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.BILL_TURNER,
                                    Engineer.JURI_ISHMAAK,
                                    Engineer.LORI_JAMESON,
                                    Engineer.HERA_TANI,
                                    Engineer.LEI_CHEUNG
                            )
                    ),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsModuleBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Raw.TIN, 1,
                                    Manufactured.MECHANICALCOMPONENTS, 1,
                                    Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.DSS_PATCH_RADIUS, new HorizonsNumberModifierValue("+50%", true, percentagePositive(0.4, 0.5))
                            ),
                            List.of(
                                    Engineer.ETIENNE_DORN,
                                    Engineer.BILL_TURNER,
                                    Engineer.JURI_ISHMAAK,
                                    Engineer.LORI_JAMESON,
                                    Engineer.HERA_TANI,
                                    Engineer.LEI_CHEUNG
                            )
                    )
            )
    );

}
