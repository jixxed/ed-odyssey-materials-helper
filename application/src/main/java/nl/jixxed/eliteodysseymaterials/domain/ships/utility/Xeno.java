package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.domain.ships.*;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Xeno extends UtilityModule {
    public static final Xeno SHUTDOWN_FIELD_NEUTRALISER_0_F = new Xeno("SHUTDOWN_FIELD_NEUTRALISER_0_F", HorizonsBlueprintName.SHUTDOWN_FIELD_NEUTRALISER, ModuleSize.SIZE_0, ModuleClass.F, Origin.AEGIS, false, Mounting.NA, 63000, "Hpt_AntiUnknownShutdown_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.3), Map.entry(HorizonsModifier.INTEGRITY, 35.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.2), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_RANGE, 3000.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_CHARGE_DURATION, 1.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_ACTIVE_POWER, 0.25), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_COOLDOWN, 10.0)));
    public static final Xeno THARGOID_PULSE_NEUTRALISER_0_E = new Xeno("THARGOID_PULSE_NEUTRALISER_0_E", HorizonsBlueprintName.THARGOID_PULSE_NEUTRALISER, ModuleSize.SIZE_0, ModuleClass.E, Origin.AEGIS, false, Mounting.NA, 150000, "Hpt_AntiUnknownShutdown_Tiny_V2", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 3.0), Map.entry(HorizonsModifier.INTEGRITY, 70.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.4), Map.entry(HorizonsModifier.BOOT_TIME, 0.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_RANGE, 0.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_CHARGE_DURATION, 2.0), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_ACTIVE_POWER, 0.33), Map.entry(HorizonsModifier.DISRUPTION_BARRIER_COOLDOWN, 10.0)));
    public static final Xeno XENO_SCANNER_0_E = new Xeno("XENO_SCANNER_0_E", HorizonsBlueprintName.XENO_SCANNER, ModuleSize.SIZE_0, ModuleClass.E, false, Mounting.NA, 365700, "Hpt_XenoScanner_Basic_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.3), Map.entry(HorizonsModifier.INTEGRITY, 56.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.2), Map.entry(HorizonsModifier.BOOT_TIME, 2.0), Map.entry(HorizonsModifier.SCANNER_RANGE, 500.0), Map.entry(HorizonsModifier.MAX_ANGLE, 23.0), Map.entry(HorizonsModifier.SCAN_TIME, 10.0)));
    public static final Xeno ENHANCED_XENO_SCANNER_0_C = new Xeno("ENHANCED_XENO_SCANNER_0_C", HorizonsBlueprintName.ENHANCED_XENO_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, Origin.ARQUE, false, Mounting.NA, 745950, "Hpt_XenoScannerMk2_Basic_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 1.3), Map.entry(HorizonsModifier.INTEGRITY, 56.0), Map.entry(HorizonsModifier.POWER_DRAW, 0.8), Map.entry(HorizonsModifier.BOOT_TIME, 2.0), Map.entry(HorizonsModifier.SCANNER_RANGE, 2000.0), Map.entry(HorizonsModifier.MAX_ANGLE, 23.0), Map.entry(HorizonsModifier.SCAN_TIME, 10.0)));
    public static final Xeno PULSE_WAVE_XENO_SCANNER_0_C = new Xeno("PULSE_WAVE_XENO_SCANNER_0_C", HorizonsBlueprintName.PULSE_WAVE_XENO_SCANNER, ModuleSize.SIZE_0, ModuleClass.C, Origin.AEGIS, false, Mounting.NA, 850000, "Hpt_XenoScanner_Advanced_Tiny", Map.ofEntries(Map.entry(HorizonsModifier.MASS, 3.0), Map.entry(HorizonsModifier.INTEGRITY, 100.0), Map.entry(HorizonsModifier.POWER_DRAW, 1.0), Map.entry(HorizonsModifier.BOOT_TIME, 2.0), Map.entry(HorizonsModifier.SCANNER_RANGE, 1000.0), Map.entry(HorizonsModifier.MAX_ANGLE, 23.0), Map.entry(HorizonsModifier.SCAN_TIME, 10.0)));


    public static final List<Xeno> XENOS = List.of(
            SHUTDOWN_FIELD_NEUTRALISER_0_F,
            THARGOID_PULSE_NEUTRALISER_0_E,
            XENO_SCANNER_0_E,
            ENHANCED_XENO_SCANNER_0_C,
            PULSE_WAVE_XENO_SCANNER_0_C
    );

    public Xeno(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public Xeno(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, Mounting mounting, long basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public Xeno(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
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

    @Override
    public boolean isPassivePower() {
        return SHUTDOWN_FIELD_NEUTRALISER_0_F.equals(this) || THARGOID_PULSE_NEUTRALISER_0_E.equals(this);
    }

    @Override
    public int getGrouping() {
        return switch (getId()) {
            case "XENO_SCANNER_0_E" -> 1;
            case "ENHANCED_XENO_SCANNER_0_C" -> 2;
            case "PULSE_WAVE_XENO_SCANNER_0_C" -> 3;
            case "SHUTDOWN_FIELD_NEUTRALISER_0_F" -> 4;
            case "THARGOID_PULSE_NEUTRALISER_0_E" -> 5;
            default -> 0;
        };
    }

    @Override
    public int getModuleLimit() {
        if (XENO_SCANNER_0_E.equals(this) || ENHANCED_XENO_SCANNER_0_C.equals(this) || PULSE_WAVE_XENO_SCANNER_0_C.equals(this)) {
            return 1;
        }
        return super.getModuleLimit();
    }
}
