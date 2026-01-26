package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.domain.ships.Slot;
import nl.jixxed.eliteodysseymaterials.domain.ships.core_internals.Armour;
import nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.HullReinforcementPackage;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.math.BigDecimal.ONE;
import static nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals.military.HullReinforcementPackage.*;
import static nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintGrade.*;
import static nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(JfxToolkitExtension.class)
class ArmourStatsTest {

    private ArmourStats armourStats;
    private static HullReinforcementPackage MOD_1D = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, null, null, 0.0, null);
    private static HullReinforcementPackage MOD_1D_BR_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_1D_TR_G1 = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_1, 1.0, null);
    private static HullReinforcementPackage MOD_1D_TR_G1_DP = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_1, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_1D_TR_G2 = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_2, 1.0, null);
    private static HullReinforcementPackage MOD_1D_TR_G2_DP = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_2, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_1D_TR_G2_RP = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_2, 1.0, REFLECTIVE_PLATING);
    private static HullReinforcementPackage MOD_1D_TR_G4 = createModule(HULL_REINFORCEMENT_PACKAGE_1_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, null);

    private static HullReinforcementPackage MOD_2D = createModule(HULL_REINFORCEMENT_PACKAGE_2_D, null, null, 0.0, null);
    private static HullReinforcementPackage MOD_2D_BR_G4_LP = createModule(HULL_REINFORCEMENT_PACKAGE_2_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, LAYERED_PLATING);
    private static HullReinforcementPackage MOD_2D_BR_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_2_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_2D_HD_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_2_D, HEAVY_DUTY_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_2D_KR_G1 = createModule(HULL_REINFORCEMENT_PACKAGE_2_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_1, 1.0, null);
    private static HullReinforcementPackage MOD_2D_TR_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_2_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);

    private static HullReinforcementPackage MOD_3D = createModule(HULL_REINFORCEMENT_PACKAGE_3_D, null, null, 0.0, null);
    private static HullReinforcementPackage MOD_3D_HD_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_3_D, HEAVY_DUTY_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);

    private static HullReinforcementPackage MOD_4D = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, null, null, 0.0, null);
    private static HullReinforcementPackage MOD_4D_BR_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_4D_HD_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, HEAVY_DUTY_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_4D_KR_G1 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_1, 1.0, null);
    private static HullReinforcementPackage MOD_4D_KR_G2 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_2, 1.0, null);
    private static HullReinforcementPackage MOD_4D_KR_G3 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_3, 1.0, null);
    private static HullReinforcementPackage MOD_4D_KR_G4 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, null);
    private static HullReinforcementPackage MOD_4D_KR_G4_25 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 0.25, null);
    private static HullReinforcementPackage MOD_4D_KR_G4_50 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 0.50, null);
    private static HullReinforcementPackage MOD_4D_KR_G4_75 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 0.75, null);
    private static HullReinforcementPackage MOD_4D_KR_G4_AP = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, ANGLED_PLATING);
    private static HullReinforcementPackage MOD_4D_KR_G4_RP = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, REFLECTIVE_PLATING);
    private static HullReinforcementPackage MOD_4D_KR_G5 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, null);
    private static HullReinforcementPackage MOD_4D_KR_G5_20 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 0.20, null);
    private static HullReinforcementPackage MOD_4D_KR_G5_40 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 0.40, null);
    private static HullReinforcementPackage MOD_4D_KR_G5_60 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 0.60, null);
    private static HullReinforcementPackage MOD_4D_KR_G5_80 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 0.80, null);
    private static HullReinforcementPackage MOD_4D_KR_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_4D_TR_G1 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_1, 1.0, null);
    private static HullReinforcementPackage MOD_4D_TR_G2 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_2, 1.0, null);
    private static HullReinforcementPackage MOD_4D_TR_G3 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_3, 1.0, null);
    private static HullReinforcementPackage MOD_4D_TR_G4 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, null);
    private static HullReinforcementPackage MOD_4D_TR_G5 = createModule(HULL_REINFORCEMENT_PACKAGE_4_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, null);

    private static HullReinforcementPackage MOD_5D = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, null, null, 0.0, null);
    private static HullReinforcementPackage MOD_5D_BR_G4_DP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_5D_BR_G4_LP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, LAYERED_PLATING);
    private static HullReinforcementPackage MOD_5D_BR_G5 = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, null);
    private static HullReinforcementPackage MOD_5D_BR_G5_AP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, ANGLED_PLATING);
    private static HullReinforcementPackage MOD_5D_BR_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_5D_BR_G5_LP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, BLAST_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, LAYERED_PLATING);
    private static HullReinforcementPackage MOD_5D_HD_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, HEAVY_DUTY_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_5D_HD_G5_LP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, HEAVY_DUTY_HULL_REINFORCEMENT, GRADE_5, 1.0, LAYERED_PLATING);
    private static HullReinforcementPackage MOD_5D_KR_G4_DP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_4, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_5D_KR_G5_DP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, KINETIC_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, DEEP_PLATING);
    private static HullReinforcementPackage MOD_5D_TR_G5 = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, null);
    private static HullReinforcementPackage MOD_5D_TR_G5_RP = createModule(HULL_REINFORCEMENT_PACKAGE_5_D, THERMAL_RESISTANT_HULL_REINFORCEMENT, GRADE_5, 1.0, REFLECTIVE_PLATING);

    private static Armour ANACONDA_ARMOUR_MIRRORED_TR_G5_RP = Armour.ANACONDA_ARMOUR_MIRRORED.Clone();
    private static Armour ANACONDA_ARMOUR_MIRRORED = Armour.ANACONDA_ARMOUR_MIRRORED.Clone();
    private static Armour ANACONDA_ARMOUR_REACTIVE = Armour.ANACONDA_ARMOUR_REACTIVE.Clone();
    private static Armour ANACONDA_ARMOUR_LIGHTWEIGHT = Armour.ANACONDA_ARMOUR_GRADE_1.Clone();

    @BeforeEach
    void setUp() {
        ScalingHelper.init();
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        armourStats = new ArmourStats();

        ANACONDA_ARMOUR_MIRRORED_TR_G5_RP.applyModification(THERMAL_RESISTANT, GRADE_5, ONE);
        ANACONDA_ARMOUR_MIRRORED_TR_G5_RP.applyExperimentalEffect(REFLECTIVE_PLATING);
    }

    private static HullReinforcementPackage createModule(HullReinforcementPackage baseModule, HorizonsBlueprintType blueprint, HorizonsBlueprintGrade grade, double completion, HorizonsBlueprintType effect) {
        HullReinforcementPackage hrp = baseModule.Clone();
        if (blueprint != null)
            hrp.applyModification(blueprint, grade, BigDecimal.valueOf(completion));
        if (effect != null)
            hrp.applyExperimentalEffect(effect);
        return hrp;
    }

    private static Stream<Arguments> testcases() {
        return Stream.of(
                Arguments.of(-96.0, 72.4, -68.0, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of()),

                Arguments.of(3.3, 73.1, -83.8, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP)),
                Arguments.of(41.1, 73.9, -101.1, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP)),
                Arguments.of(53.2, 74.7, -119.9, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP)),
                Arguments.of(59.2, 75.0, -140.6, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP)),
                Arguments.of(62.1, 75.0, -163.2, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP)),
                Arguments.of(63.6, 75.0, -187.9, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP)),
                Arguments.of(63.8, 75.0, -143.4, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP)),
                Arguments.of(63.7, 75.0, -20.1, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP)),
                Arguments.of(63.6, 75.0, 35.0, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP)),
                Arguments.of(63.8, 75.0, 39.5, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP)),
                Arguments.of(64.0, 74.3, 43.3, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP)),
                Arguments.of(64.1, 73.0, 46.4, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP)),
                Arguments.of(64.0, 73.9, 55.4, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(63.0, 73.2, 56.2, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(62.8, 74.1, 60.5, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(60.5, 73.3, 60.9, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(55.8, 72.6, 61.2, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(57.2, 71.4, 61.8, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(56.3, 68.3, 61.4, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_2D_TR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(57.2, 67.9, 58.1, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_2D_TR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP)),
                Arguments.of(49.2, 67.7, 58.7, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_2D_TR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP)),
                Arguments.of(51.6, 67.3, 59.7, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_2D_TR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP)),
                Arguments.of(52.9, 69.5, 60.2, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP)),
                Arguments.of(34.7, 67.3, 62.5, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_2D_TR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_1D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP)),
                Arguments.of(41.8, 68.9, 63.1, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_1D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP)),
                Arguments.of(53.6, 69.2, 62.9, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(61.1, 72.0, 61.5, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_2D_BR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_5D_KR_G5_DP, MOD_4D_HD_G5_DP, MOD_5D_HD_G5_DP, MOD_5D_BR_G5_DP, MOD_4D_BR_G5_DP, MOD_4D_HD_G5_DP, MOD_4D_HD_G5_DP, MOD_2D_HD_G5_DP, MOD_1D_BR_G5_DP)),
                Arguments.of(-87.7, 57.9, -60.9, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5)),
                Arguments.of(-101.3, 61.7, -72.5, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5)),
                Arguments.of(-115.9, 63.4, -85.0, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_5D_TR_G5)),
                Arguments.of(-87.7, 48.9, 29.5, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_BR_G5)),
                Arguments.of(-101.3, 47.7, 48.4, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_BR_G5, MOD_5D_BR_G5)),
                Arguments.of(-115.9, 46.5, 57.2, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_BR_G5, MOD_5D_BR_G5, MOD_5D_BR_G5)),
                Arguments.of(-71.5, 50.3, -47.0, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D)),
                Arguments.of(-74.9, 52.1, -49.9, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_TR_G1)),
                Arguments.of(-78.4, 53.1, -52.9, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_TR_G2)),
                Arguments.of(-81.8, 54.1, -55.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_TR_G3)),
                Arguments.of(-85.2, 55.8, -58.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_TR_G4)),
                Arguments.of(-88.7, 57.9, -61.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_TR_G5)),
                Arguments.of(-50.9, 50.0, -49.9, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G1)),
                Arguments.of(-38.9, 49.7, -52.9, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G2)),
                Arguments.of(-26.9, 49.4, -55.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G3)),
                Arguments.of(-23.9, 49.1, -58.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G4_25)),
                Arguments.of(-19.3, 49.1, -58.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G4_50)),
                Arguments.of(-13.3, 49.1, -58.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G4_75)),
                Arguments.of(-7.3, 49.1, -58.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G4)),
                Arguments.of(-2.5, 48.8, -61.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G5_20)),
                Arguments.of(2.3, 48.8, -61.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G5_40)),
                Arguments.of(7.1, 48.8, -61.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G5_60)),
                Arguments.of(11.9, 48.8, -61.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G5_80)),
                Arguments.of(16.7, 48.8, -61.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G5)),
                Arguments.of(4.2, 56.8, 18.5, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G5, MOD_5D_BR_G5, MOD_5D_TR_G5)),
                Arguments.of(-131.9, 63.1, 12.9, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_BR_G5, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_1D_TR_G1, MOD_1D)),
                Arguments.of(-131.9, 64.2, -98.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_1D_TR_G1, MOD_1D)),
                Arguments.of(-126.2, 64.0, -93.9, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D)),
                Arguments.of(-138.9, 64.3, -104.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_1D_TR_G1_DP, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-137.7, 64.3, -103.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-136.5, 64.3, -102.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-128.5, 64.2, -95.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_1D, MOD_1D, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-127.3, 64.2, -94.8, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_1D, MOD_1D, MOD_1D, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(2.6, 61.1, -3.9, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_1D, MOD_1D, MOD_1D, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-1.4, 61.5, -8.1, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-0.3, 61.6, -7.0, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_4D_TR_G4, MOD_2D, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(11.7, 61.5, -9.2, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-106.0, 64.3, -104.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-41.3, 62.5, -91.1, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-19.4, 62.9, -61.5, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_5D_HD_G5_DP, MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-28.1, 62.7, 24.1, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_5D_HD_G5_DP, MOD_5D_BR_G5, MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-37.4, 62.6, 47.2, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_5D_HD_G5_DP, MOD_5D_BR_G5, MOD_5D_BR_G5, MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(19.9, 61.8, 43.3, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP, MOD_5D_BR_G5, MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),
                Arguments.of(-86.8, 64.3, 18.7, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_5D_HD_G5_DP, MOD_5D_BR_G5, MOD_2D_KR_G1, MOD_5D_TR_G5, MOD_5D_TR_G5, MOD_1D, MOD_1D, MOD_1D_TR_G1_DP, MOD_4D_TR_G4, MOD_4D_TR_G5, MOD_2D, MOD_1D_TR_G1)),

                Arguments.of(-16.7, 68.1, 0.0, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D, MOD_5D, MOD_5D, MOD_5D, MOD_5D, MOD_4D, MOD_4D, MOD_4D, MOD_2D, MOD_1D)),
                Arguments.of(-26.0, 68.4, 34.7, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D, MOD_5D, MOD_5D, MOD_5D, MOD_5D, MOD_4D, MOD_4D, MOD_4D, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(24.5, 68.6, 32.3, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D, MOD_5D, MOD_5D, MOD_5D, MOD_5D, MOD_4D, MOD_4D, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(32.9, 68.1, 37.7, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_HD_G5_LP, MOD_5D, MOD_5D, MOD_5D, MOD_5D, MOD_4D, MOD_4D, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(30.4, 68.4, 51.9, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_BR_G5_AP, MOD_5D_HD_G5_LP, MOD_5D, MOD_5D, MOD_5D, MOD_4D, MOD_4D, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(23.9, 68.7, 59.0, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_BR_G5_AP, MOD_5D_HD_G5_LP, MOD_5D_BR_G5_LP, MOD_5D, MOD_5D, MOD_4D, MOD_4D, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(16.3, 66.7, 58.4, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_BR_G5_AP, MOD_5D_HD_G5_LP, MOD_5D_BR_G5_LP, MOD_5D_TR_G5_RP, MOD_5D, MOD_4D, MOD_4D, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(43.7, 66.9, 57.6, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_BR_G5_AP, MOD_5D_HD_G5_LP, MOD_5D_BR_G5_LP, MOD_5D_TR_G5_RP, MOD_5D, MOD_4D, MOD_4D_KR_G5_DP, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(42.0, 67.1, 60.6, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_BR_G5_AP, MOD_5D_HD_G5_LP, MOD_5D_BR_G5_LP, MOD_5D_TR_G5_RP, MOD_5D_BR_G4_LP, MOD_4D, MOD_4D_KR_G5_DP, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D)),
                Arguments.of(40.1, 66.3, 60.2, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_BR_G5_AP, MOD_5D_HD_G5_LP, MOD_5D_BR_G5_LP, MOD_5D_TR_G5_RP, MOD_5D_BR_G4_LP, MOD_4D, MOD_4D_KR_G5_DP, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D_TR_G4)),
                Arguments.of(49.4, 66.4, 59.8, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G4_DP, MOD_5D_TR_G5_RP, MOD_5D_KR_G4_DP, MOD_5D_BR_G5_AP, MOD_5D_HD_G5_LP, MOD_5D_BR_G5_LP, MOD_5D_TR_G5_RP, MOD_5D_BR_G4_LP, MOD_4D_KR_G4_RP, MOD_4D_KR_G5_DP, MOD_4D_KR_G4_AP, MOD_2D_BR_G4_LP, MOD_1D_TR_G4)),

                Arguments.of(-8.3, 63.1, 32.9, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_5D_HD_G5_DP,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_1D,MOD_1D,MOD_1D_TR_G1_DP,MOD_4D_TR_G4,MOD_4D_TR_G5,MOD_2D,MOD_1D_TR_G1)),
                Arguments.of(-16.7, 62.9, 49.8, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_5D_HD_G5_DP,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_1D,MOD_1D_TR_G1_DP,MOD_4D_TR_G4,MOD_4D_TR_G5,MOD_2D,MOD_1D_TR_G1)),
                Arguments.of(-25.8, 62.8, 57.8, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_5D_HD_G5_DP,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_1D_TR_G1_DP,MOD_4D_TR_G4,MOD_4D_TR_G5,MOD_2D,MOD_1D_TR_G1)),
                Arguments.of(29.2, 62.2, 55.9, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_1D_TR_G1_DP,MOD_4D_TR_G4,MOD_4D_TR_G5,MOD_2D,MOD_1D_TR_G1)),
                Arguments.of(10.1, 63.4, 53.4, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_1D_TR_G1_DP,MOD_4D_TR_G4,MOD_4D_TR_G5,MOD_2D,MOD_1D_TR_G1)),
                Arguments.of(11.4, 63.2, 53.6, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_1D_TR_G1_DP,MOD_4D_TR_G4,MOD_4D_TR_G5,MOD_2D)),
                Arguments.of(10.5, 63.2, 53.5, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_1D_TR_G1_DP,MOD_4D_TR_G4,MOD_4D_TR_G5)),
                Arguments.of(17.0, 61.2, 54.3, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_1D_TR_G1_DP,MOD_4D_TR_G4)),
                Arguments.of(21.6, 58.8, 54.9, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_1D_TR_G1_DP)),
                Arguments.of(24.3, 58.0, 55.2, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5,MOD_5D_BR_G5)),
                Arguments.of(29.4, 58.5, 44.2, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_5D_BR_G5)),
                Arguments.of(32.1, 58.9, 11.6, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5)),
                Arguments.of(41.2, 60.6, 33.1, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP)),
                Arguments.of(41.6, 60.7, 33.6, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D)),
                Arguments.of(39.8, 62.9, 31.1, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_TR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_5D_TR_G5,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D,MOD_4D_TR_G5)),
                Arguments.of(43.1, 55.7, 35.6, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_2D_KR_G1,MOD_5D_TR_G5,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D,MOD_4D_TR_G5)),
                Arguments.of(44.5, 45.2, 37.5, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_2D_KR_G1,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D,MOD_4D_TR_G5)),
                Arguments.of(47.7, 48.3, 41.8, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D,MOD_4D_TR_G5)),
                Arguments.of(49.0, 29.8, 43.5, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D)),
                Arguments.of(49.2, 30.4, 43.8, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D,MOD_3D)),
                Arguments.of(48.4, 33.7, 42.7, ANACONDA_ARMOUR_LIGHTWEIGHT, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D,MOD_1D_TR_G1_DP)),
                Arguments.of(54.6, 12.3, 52.3, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_3D_HD_G5_DP,MOD_5D_HD_G5_DP,MOD_3D,MOD_1D_TR_G1_DP)),
                Arguments.of(52.7, -3.8, 49.9, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_3D_HD_G5_DP,MOD_3D,MOD_1D_TR_G1_DP)),
                Arguments.of(52.0, -10.2, 58.0, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_5D_HD_G5_DP,MOD_5D_BR_G5,MOD_3D,MOD_1D_TR_G1_DP)),
                Arguments.of(49.6, -30.4, 56.7, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_5D_BR_G5,MOD_3D,MOD_1D_TR_G1_DP)),
                Arguments.of(48.5, -39.8, 61.1, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_KR_G5,MOD_5D_BR_G5,MOD_5D_HD_G5_DP,MOD_2D_KR_G1,MOD_5D_BR_G5,MOD_5D_BR_G5,MOD_3D,MOD_1D_TR_G1_DP)),
                Arguments.of(22.6, 16.3, 17.4, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_TR_G4,MOD_5D)),
                Arguments.of(24.5, 18.4, 19.5, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_TR_G4,MOD_5D,MOD_5D)),
                Arguments.of(26.4, 20.4, 21.5, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_TR_G4,MOD_5D,MOD_5D,MOD_5D)),
                Arguments.of(34.7, 32.2, 32.7, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_TR_G4,MOD_5D,MOD_5D,MOD_5D,MOD_5D,MOD_5D_HD_G5_DP)),
                Arguments.of(26.1, 30.5, 21.2, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_TR_G4,MOD_5D,MOD_5D,MOD_5D,MOD_5D,MOD_1D_TR_G1_DP,MOD_1D)),
                Arguments.of(23.5, 38.4, 18.4, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_TR_G4,MOD_5D,MOD_5D,MOD_5D,MOD_5D,MOD_1D_TR_G1_DP,MOD_1D_TR_G1,MOD_1D_TR_G1)),
                Arguments.of(21.5, 37.8, 16.3, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_4D_TR_G4,MOD_5D,MOD_5D,MOD_5D,MOD_1D_TR_G1_DP,MOD_1D_TR_G1,MOD_1D_TR_G1)),
                Arguments.of(25.9, 11.1, 20.9, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D,MOD_5D,MOD_5D,MOD_1D_TR_G1_DP,MOD_1D_TR_G1,MOD_1D_TR_G1)),
                Arguments.of(33.7, 24.9, 31.6, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_5D,MOD_5D,MOD_1D_TR_G1_DP,MOD_1D_TR_G1,MOD_1D_TR_G1)),
                Arguments.of(38.2, 32.9, 36.5, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_5D,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G1_DP,MOD_1D_TR_G1,MOD_1D_TR_G1)),
                Arguments.of(37.7, 35.5, 35.9, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_5D,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G1_DP,MOD_1D_TR_G2,MOD_1D_TR_G1)),
                Arguments.of(37.2, 37.8, 35.3, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_5D,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_DP,MOD_1D_TR_G2,MOD_1D_TR_G1)),
                Arguments.of(38.1, 31.3, 36.3, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_5D,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_DP,MOD_1D_TR_G1)),
                Arguments.of(37.4, 30.4, 35.6, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_DP,MOD_1D_TR_G1)),
                Arguments.of(36.7, 29.1, 34.8, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_DP,MOD_1D_TR_G1)),
                Arguments.of(35.7, 36.4, 33.8, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_DP,MOD_1D_TR_G2,MOD_1D_TR_G1)),
                Arguments.of(35.9, 36.6, 33.9, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_DP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D)),
                Arguments.of(36.0, 36.7, 34.1, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_DP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),

                Arguments.of(36.6, 37.8, 34.7, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D_HD_G5_DP,MOD_5D,MOD_3D_HD_G5_DP,MOD_1D_TR_G2_RP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),
                Arguments.of(30.5, 32.0, 26.5, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_3D_HD_G5_DP,MOD_1D_TR_G2_RP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),
                Arguments.of(19.3, 22.7, 13.9, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_1D_TR_G2_RP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),
                Arguments.of(21.3, 24.7, 16.1, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D,MOD_1D_TR_G2_RP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),
                Arguments.of(23.3, 26.6, 18.2, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D,MOD_5D,MOD_1D_TR_G2_RP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),
                Arguments.of(25.2, 28.4, 20.2, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D,MOD_5D,MOD_5D,MOD_1D_TR_G2_RP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),
                Arguments.of(27.1, 30.1, 22.2, ANACONDA_ARMOUR_REACTIVE, List.of(MOD_5D,MOD_5D,MOD_5D,MOD_5D,MOD_1D_TR_G2_RP,MOD_1D_TR_G2,MOD_1D_TR_G1,MOD_1D,MOD_1D)),
                Arguments.of(-110.2, 68.5, -80.2, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_TR_G5)),
                Arguments.of(-110.2, 72.9, 21.0, ANACONDA_ARMOUR_MIRRORED_TR_G5_RP, List.of(MOD_5D_BR_G5)),


                Arguments.of(4.2, 56.8, 18.5, ANACONDA_ARMOUR_MIRRORED, List.of(MOD_4D_KR_G5, MOD_5D_BR_G5, MOD_5D_TR_G5))
        );
    }

    @ParameterizedTest
    @MethodSource("testcases")
    void calculateResistance(Double expectedKinetic, Double expectedThermic, Double expectedExplosive, Armour armour, List<HullReinforcementPackage> modules) {
        Ship ship = new Ship(Ship.ANACONDA);
        ship.getCoreSlots().getFirst().setShipModule(armour);
        for (int i = 0; i < 13; i++) {
            if (modules.size() > i && modules.get(i) != null && !modules.get(i).getCustomName().equals("DUMMY"))
                ship.getOptionalSlots().get(i).setShipModule(modules.get(i));
        }
        ApplicationState.getInstance().setShip(ship);
        double delta = 0.051;
        Assertions.assertAll(
                () -> assertEquals(expectedKinetic, armourStats.calculateResistance(HorizonsModifier.KINETIC_RESISTANCE), delta),
                () -> assertEquals(expectedThermic, armourStats.calculateResistance(HorizonsModifier.THERMAL_RESISTANCE), delta),
                () -> assertEquals(expectedExplosive, armourStats.calculateResistance(HorizonsModifier.EXPLOSIVE_RESISTANCE), delta)
        );
    }

    /**
     * We would like to get this down to 1 failure, which would indicate the valid sort, but it is impossible to collect test data for it
     * Tests that start with EXP or MOD_REV are still failing
     * @param sorts
     */
    @Disabled
    @ParameterizedTest
    @MethodSource("sorts")
    void testResistanceSort(List<Comp> sorts) {
//        String name = sorts.getFirst().toString();
//        if(!(name.equals("EXP") || name.equals("MOD_REV"))){
//            log.debug("Skipping test for sort starting with {}", name);
//            return;
//        }
        double delta = 0.051;
        List<Executable> executions = testcases()
                .map(arg -> (Executable) () -> {
                    Armour armour = (Armour) arg.get()[3];
                    List<HullReinforcementPackage> modules = (List<HullReinforcementPackage>) arg.get()[4];
                    Ship ship = new Ship(Ship.ANACONDA);
                    ship.getCoreSlots().getFirst().setShipModule(armour);
                    for (int i = 0; i < 13; i++) {
                        if (modules.size() > i && modules.get(i) != null && !modules.get(i).getCustomName().equals("DUMMY"))
                            ship.getOptionalSlots().get(i).setShipModule(modules.get(i));
                    }
                    ApplicationState.getInstance().setShip(ship);
                    //chain sorts
                    Comparator<Slot> kineticSortChain = sorts.stream()
                            .filter(Objects::nonNull)
                            .map(c -> c.getComparator(HorizonsModifier.KINETIC_RESISTANCE))
                            .reduce(Comparator::thenComparing)
                            .orElse((a, b) -> 0);
                    Comparator<Slot> thermalSortChain = sorts.stream()
                            .filter(Objects::nonNull)
                            .map(c -> c.getComparator(HorizonsModifier.THERMAL_RESISTANCE))
                            .reduce(Comparator::thenComparing)
                            .orElse((a, b) -> 0);
                    Comparator<Slot> explosiveSortChain = sorts.stream()
                            .filter(Objects::nonNull)
                            .map(c -> c.getComparator(HorizonsModifier.EXPLOSIVE_RESISTANCE))
                            .reduce(Comparator::thenComparing)
                            .orElse((a, b) -> 0);

                    assertEquals((Double) arg.get()[0], armourStats.calculateResistance(HorizonsModifier.KINETIC_RESISTANCE, kineticSortChain), delta);
                    assertEquals((Double) arg.get()[1], armourStats.calculateResistance(HorizonsModifier.THERMAL_RESISTANCE, thermalSortChain), delta);
                    assertEquals((Double) arg.get()[2], armourStats.calculateResistance(HorizonsModifier.EXPLOSIVE_RESISTANCE, explosiveSortChain), delta);
                })
                .toList();

        int failures = 0;

        for (Executable exec : executions) {
            try {
                exec.execute();
            } catch (Throwable e) {
                failures++;
            }
        }
        log.debug("Failures: {} out of {}", failures, executions.size());
        //we skip a sort that always works, VALUE, so we expect at least one failure for other sorts
        if(!sorts.getFirst().name.equals("VALUE")) {
            assertTrue(failures > 0, "Expected at least one failure");
            assertTrue(failures < executions.size(), "Expected not all to fail");
        }
    }

    @AllArgsConstructor
    private static class Comp {
        String name;
        Function<HorizonsModifier, Comparator<Slot>> comparator;
        boolean reversed;

        Comparator<Slot> getComparator(HorizonsModifier mod) {
            return (reversed) ? comparator.apply(mod).reversed() : comparator.apply(mod);
        }

        @Override
        public String toString() {
            return name + ((reversed) ? "_REV" : "");
        }
    }

    private static Stream<Arguments> sorts() {
        Function<HorizonsModifier, Comparator<Slot>> MOD = _ -> Comparator.comparing((Slot slot) -> !slot.getShipModule().getModifications().isEmpty());
        Function<HorizonsModifier, Comparator<Slot>> EXP = _ -> Comparator.comparing((Slot slot) -> !slot.getShipModule().getExperimentalEffects().isEmpty());
        Function<HorizonsModifier, Comparator<Slot>> SIZE = _ -> Comparator.comparing((Slot slot) -> slot.getShipModule().getModuleSize().intValue());
        Function<HorizonsModifier, Comparator<Slot>> VALUE = (mod) -> Comparator.comparing((Slot slot) -> (double) slot.getShipModule().getAttributeValue(mod, true));

        List<String> keys = List.of("MOD", "EXP", "SIZE", "VALUE");
        Map<String, Function<HorizonsModifier, Comparator<Slot>>> normalMap = Map.of(
                "MOD", MOD,
                "EXP", EXP,
                "SIZE", SIZE,
                "VALUE", VALUE
        );

        List<Arguments> args = new ArrayList<>();
        for (int len = 1; len <= keys.size(); len++) {
            permute(keys, normalMap, len, new boolean[keys.size()], new ArrayList<>(), args);
        }

        return args.stream().sorted((o1, o2) -> {
            String comps1 = ((List<Comp>) o1.get()[0]).stream().map(comp -> comp.toString()).collect(Collectors.joining(","));
            String comps2 = ((List<Comp>) o2.get()[0]).stream().map(comp -> comp.toString()).collect(Collectors.joining(","));
            return comps1.compareTo(comps2);
        });
    }

    private static void permute(List<String> keys, Map<String, Function<HorizonsModifier, Comparator<Slot>>> normalMap, int targetLen,
                                boolean[] used, List<String> path, List<Arguments> out) {
        if (path.size() == targetLen) {
            int orientations = 1 << targetLen; // each bit chooses normal (0) or reversed (1)
            for (int mask = 0; mask < orientations; mask++) {
                List<Comp> comps = new ArrayList<>(targetLen);
                for (int i = 0; i < targetLen; i++) {
                    String key = path.get(i);
                    Function<HorizonsModifier, Comparator<Slot>> chosen = normalMap.get(key);
                    comps.add(new Comp(key, chosen, ((mask >> i) & 1) == 0));
                }
                out.add(Arguments.of(comps));
            }
            return;
        }

        for (int i = 0; i < keys.size(); i++) {
            if (used[i]) continue;
            used[i] = true;
            path.add(keys.get(i));
            permute(keys, normalMap, targetLen, used, path, out);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }


    public Optional<Ship> getShip() {
        return Optional.ofNullable(ApplicationState.getInstance().getShip());
    }

}