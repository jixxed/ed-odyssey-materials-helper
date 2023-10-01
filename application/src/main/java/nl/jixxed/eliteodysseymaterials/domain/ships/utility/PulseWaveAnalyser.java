package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PulseWaveAnalyser extends UtilityModule {
//    		59050 : { mtype:'upwa', cost:  13550, name:'Pulse Wave Analyser',       class:0, rating:'E', mass:1.30, integ:32, pwrdraw:0.20,            boottime:3, scanrng:12000,maxangle:15.00, scantime: 3, limit:'upwa', fdid:128915718, fdname:'Hpt_MRAScanner_Size0_Class1', eddbid:1793 },
//            59040 : { mtype:'upwa', cost:  40630, name:'Pulse Wave Analyser',       class:0, rating:'D', mass:1.30, integ:24, pwrdraw:0.40,            boottime:3, scanrng:15000,maxangle:15.00, scantime: 3, limit:'upwa', fdid:128915719, fdname:'Hpt_MRAScanner_Size0_Class2', eddbid:1794 },
//            59030 : { mtype:'upwa', cost: 121900, name:'Pulse Wave Analyser',       class:0, rating:'C', mass:1.30, integ:40, pwrdraw:0.80,            boottime:3, scanrng:18000,maxangle:15.00, scantime: 3, limit:'upwa', fdid:128915720, fdname:'Hpt_MRAScanner_Size0_Class3', eddbid:1795 },
//            59020 : { mtype:'upwa', cost: 365700, name:'Pulse Wave Analyser',       class:0, rating:'B', mass:1.30, integ:56, pwrdraw:1.60,            boottime:3, scanrng:21000,maxangle:15.00, scantime: 3, limit:'upwa', fdid:128915721, fdname:'Hpt_MRAScanner_Size0_Class4', eddbid:1796 },
//            59010 : { mtype:'upwa', cost:1097100, name:'Pulse Wave Analyser',       class:0, rating:'A', mass:1.30, integ:48, pwrdraw:3.20,            boottime:3, scanrng:24000,maxangle:15.00, scantime: 3, limit:'upwa', fdid:128915722, fdname:'Hpt_MRAScanner_Size0_Class5', eddbid:1797 },
    public static final PulseWaveAnalyser PULSE_WAVE_ANALYSER_0_E = new PulseWaveAnalyser("PULSE_WAVE_ANALYSER_0_E", HorizonsBlueprintName.PULSE_WAVE_ANALYSER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 13550, "Hpt_MRAScanner_Size0_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  32.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  12000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  3.0)));
    public static final PulseWaveAnalyser PULSE_WAVE_ANALYSER_0_D = new PulseWaveAnalyser("PULSE_WAVE_ANALYSER_0_D", HorizonsBlueprintName.PULSE_WAVE_ANALYSER, ModuleSize.SIZE_0, ModuleClass.D, false, Mounting.NA, 40630, "Hpt_MRAScanner_Size0_Class2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  15000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  3.0)));
    public static final PulseWaveAnalyser PULSE_WAVE_ANALYSER_0_C = new PulseWaveAnalyser("PULSE_WAVE_ANALYSER_0_C", HorizonsBlueprintName.PULSE_WAVE_ANALYSER, ModuleSize.SIZE_0, ModuleClass.C, false, Mounting.NA, 121900, "Hpt_MRAScanner_Size0_Class3", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  40.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  18000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  3.0)));
    public static final PulseWaveAnalyser PULSE_WAVE_ANALYSER_0_B = new PulseWaveAnalyser("PULSE_WAVE_ANALYSER_0_B", HorizonsBlueprintName.PULSE_WAVE_ANALYSER, ModuleSize.SIZE_0, ModuleClass.B, false, Mounting.NA, 365700, "Hpt_MRAScanner_Size0_Class4", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.6), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  21000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  3.0)));
    public static final PulseWaveAnalyser PULSE_WAVE_ANALYSER_0_A = new PulseWaveAnalyser("PULSE_WAVE_ANALYSER_0_A", HorizonsBlueprintName.PULSE_WAVE_ANALYSER, ModuleSize.SIZE_0, ModuleClass.A, false, Mounting.NA, 1097100, "Hpt_MRAScanner_Size0_Class5", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  48.0), Map.entry(HorizonsModifier.POWER_DRAW,  3.2), Map.entry(HorizonsModifier.BOOT_TIME,  3.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  24000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  15.0), Map.entry(HorizonsModifier.SCAN_TIME,  3.0)));

    public static final List<PulseWaveAnalyser> PULSE_WAVE_ANALYSERS = List.of(
            PULSE_WAVE_ANALYSER_0_E,
            PULSE_WAVE_ANALYSER_0_D,
            PULSE_WAVE_ANALYSER_0_C,
            PULSE_WAVE_ANALYSER_0_B,
            PULSE_WAVE_ANALYSER_0_A
    );
    public PulseWaveAnalyser(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PulseWaveAnalyser(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public PulseWaveAnalyser(PulseWaveAnalyser pulseWaveAnalyser) {
        super(pulseWaveAnalyser);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return Collections.emptyList();
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return Collections.emptyList();
    }

    @Override
    public PulseWaveAnalyser Clone() {
        return new PulseWaveAnalyser(this);
    }
}
