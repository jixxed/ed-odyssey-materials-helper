package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.Mounting;
import nl.jixxed.eliteodysseymaterials.domain.ships.UtilityModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Xeno extends UtilityModule {
//            58061 : { mtype:'uex',  cost:  63000, name:'Shutdown Field Neutraliser',class:0, rating:'F', mass:1.30, integ:35, pwrdraw:0.20, passive:1, boottime:0, barrierrng:3000, barrierdur:1, barrierpwr:0.25, barriercool:10, fdid:128771884, fdname:'Hpt_AntiUnknownShutdown_Tiny', eddbid:1622 },
//            58052 : { mtype:'uex',  cost:      0, name:'Thargoid Pulse Neutraliser',class:0, rating:'E', mass:3.00, integ:70, pwrdraw:0.40, passive:1, boottime:0, barrierrng:   0, barrierdur:2, barrierpwr:0.33, barriercool:10, fdid:129022663, fdname:'Hpt_AntiUnknownShutdown_Tiny_V2', eddbid:null }, // Rescue Ship tech broker // verify: cost // TODO: eddbid
//            58050 : { mtype:'uex',  cost: 365700, name:'Xeno Scanner',              class:0, rating:'E', mass:1.30, integ:56, pwrdraw:0.20,            boottime:2, scanrng: 500, maxangle:23.00, scantime:10, limit:'uex', fdid:128793115, fdname:'Hpt_XenoScanner_Basic_Tiny', eddbid:1616 },
//            58030 : { mtype:'uex',  cost: 745950, name:'Enhanced Xeno Scanner',     class:0, rating:'C', mass:1.30, integ:56, pwrdraw:0.80,            boottime:2, scanrng:2000, maxangle:23.00, scantime:10, limit:'uex', fdid:128808878, fdname:'Hpt_XenoScannerMk2_Basic_Tiny', eddbid:1838 },
//            58031 : { mtype:'uex',  cost: 850000, name:'Pulse Wave Xeno Scanner',   class:0, rating:'C', mass:3.00,integ:100, pwrdraw:1.00,            boottime:2, scanrng:1000, maxangle:23.00, scantime:10, limit:'uex', fdid:129022952, fdname:'Hpt_XenoScanner_Advanced_Tiny', eddbid:null }, // TODO: eddbid
    public static final Xeno SHUTDOWN_FIELD_NEUTRALISER_0_F = new Xeno("SHUTDOWN_FIELD_NEUTRALISER_0_F", HorizonsBlueprintName.SHUTDOWN_FIELD_NEUTRALISER, ModuleSize.SIZE_0, ModuleClass.F, false, Mounting.NA, 63000, "Hpt_AntiUnknownShutdown_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  35.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_RANGE,  3000.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_CHARGE_DURATION,  1.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_ACTIVE_POWER,  0.25), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_COOLDOWN,  10.0)));
    public static final Xeno THARGOID_PULSE_NEUTRALISER_0_E = new Xeno("THARGOID_PULSE_NEUTRALISER_0_E", HorizonsBlueprintName.THARGOID_PULSE_NEUTRALISER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 0, "Hpt_AntiUnknownShutdown_Tiny_V2", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  3.0), Map.entry(HorizonsModifier.INTEGRITY,  70.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.4), Map.entry(HorizonsModifier.BOOT_TIME,  0.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_RANGE,  0.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_CHARGE_DURATION,  2.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_ACTIVE_POWER,  0.33), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_COOLDOWN,  10.0)));
    public static final Xeno XENO_SCANNER_0_E = new Xeno("XENO_SCANNER_0_E", HorizonsBlueprintName.XENO_SCANNER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 365700, "Hpt_XenoScanner_Basic_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  500.0), Map.entry(HorizonsModifier.MAX_ANGLE,  23.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final Xeno ENHANCED_XENO_SCANNER_0_C = new Xeno("ENHANCED_XENO_SCANNER_0_C", HorizonsBlueprintName.ENHANCED_XENO_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, false, Mounting.NA, 745950, "Hpt_XenoScannerMk2_Basic_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  56.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.8), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  2000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  23.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));
    public static final Xeno PULSE_WAVE_XENO_SCANNER_0_C = new Xeno("PULSE_WAVE_XENO_SCANNER_0_C", HorizonsBlueprintName.PULSE_WAVE_XENO_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, false, Mounting.NA, 850000, "Hpt_XenoScanner_Advanced_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  3.0), Map.entry(HorizonsModifier.INTEGRITY,  100.0), Map.entry(HorizonsModifier.POWER_DRAW,  1.0), Map.entry(HorizonsModifier.BOOT_TIME,  2.0), Map.entry(HorizonsModifier.SCANNER_RANGE,  1000.0), Map.entry(HorizonsModifier.MAX_ANGLE,  23.0), Map.entry(HorizonsModifier.SCAN_TIME,  10.0)));


    public static final List<Xeno> XENOS = List.of(
            SHUTDOWN_FIELD_NEUTRALISER_0_F,
            THARGOID_PULSE_NEUTRALISER_0_E,
            XENO_SCANNER_0_E,
            ENHANCED_XENO_SCANNER_0_C,
            PULSE_WAVE_XENO_SCANNER_0_C
    );

    public Xeno(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public Xeno(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, true, Mounting.NA, basePrice, internalName, attributes);
    }

    private Xeno(final Xeno xeno) {
        super(xeno);
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
    public Xeno Clone() {
        return new Xeno(this);
    }
    @Override
    public String getClarifier() {
        return " " + LocaleService.getLocalizedStringForCurrentLocale(getName().getLocalizationKey());
    }
}
