package nl.jixxed.eliteodysseymaterials.constants.horizons.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModifierValue;
import nl.jixxed.eliteodysseymaterials.enums.*;

import java.util.List;
import java.util.Map;

public class DetailedSurfaceScannerBlueprints {
    public static final Map<HorizonsBlueprintType, Map<HorizonsBlueprintGrade, HorizonsBlueprint>> BLUEPRINTS = Map.of(
            HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS,
            Map.of(
                    HorizonsBlueprintGrade.GRADE_1, new HorizonsBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_1,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1
                            ),
                            Map.of(
                                    HorizonsModifier.PROBE_RADIUS, new HorizonsModifierValue("+10%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LEI_CHEUNG, Engineer.LORI_JAMESON, Engineer.TIANA_FORTUNE, Engineer.FELICITY_FARSEER, Engineer.HERA_TANI)),
                    HorizonsBlueprintGrade.GRADE_2, new HorizonsBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_2,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1
                            ),
                            Map.of(
                                    HorizonsModifier.PROBE_RADIUS, new HorizonsModifierValue("+20%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.LORI_JAMESON, Engineer.TIANA_FORTUNE, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_3, new HorizonsBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_3,
                            Map.of(
                                    Manufactured.MECHANICALSCRAP, 1, Raw.GERMANIUM, 1, Manufactured.PHASEALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.PROBE_RADIUS, new HorizonsModifierValue("+30%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LORI_JAMESON, Engineer.TIANA_FORTUNE, Engineer.HERA_TANI, Engineer.LEI_CHEUNG, Engineer.FELICITY_FARSEER)),
                    HorizonsBlueprintGrade.GRADE_4, new HorizonsBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_4,
                            Map.of(
                                    Manufactured.MECHANICALEQUIPMENT, 1, Raw.NIOBIUM, 1, Manufactured.PROTOLIGHTALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.PROBE_RADIUS, new HorizonsModifierValue("+40%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LORI_JAMESON, Engineer.HERA_TANI, Engineer.LEI_CHEUNG)),
                    HorizonsBlueprintGrade.GRADE_5, new HorizonsBlueprint(HorizonsBlueprintName.DETAILED_SURFACE_SCANNER, HorizonsBlueprintType.EXPANDED_PROBE_SCANNING_RADIUS, HorizonsBlueprintGrade.GRADE_5,
                            Map.of(
                                    Manufactured.MECHANICALCOMPONENTS, 1, Raw.TIN, 1, Manufactured.PROTORADIOLICALLOYS, 1
                            ),
                            Map.of(
                                    HorizonsModifier.PROBE_RADIUS, new HorizonsModifierValue("+50%", true)
                            ),
                            List.of(Engineer.ETIENNE_DORN, Engineer.BILL_TURNER, Engineer.JURI_ISHMAAK, Engineer.LORI_JAMESON, Engineer.HERA_TANI, Engineer.LEI_CHEUNG))));
}
