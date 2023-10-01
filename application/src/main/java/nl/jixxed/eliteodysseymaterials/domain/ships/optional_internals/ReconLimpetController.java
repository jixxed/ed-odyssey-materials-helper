package nl.jixxed.eliteodysseymaterials.domain.ships.optional_internals;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.OptionalModule;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReconLimpetController extends OptionalModule {

//		21150 : { mtype:'inlc', cost:  2600, name:'Recon Limpet Controller', class:1, rating:'E', mass:  1.30, integ: 24, pwrdraw:0.18, boottime:10, maxlimpet: 1, lpactrng:1200, maxspd:100, hacktime:22, fdid:128837858, fdname:'Int_DroneControl_Recon_Size1_Class1', eddbid:1636 },
//            21350 : { mtype:'inlc', cost:  8200, name:'Recon Limpet Controller', class:3, rating:'E', mass:  2.00, integ: 51, pwrdraw:0.20, boottime:10, maxlimpet: 1, lpactrng:1400, maxspd:100, hacktime:17, fdid:128841592, fdname:'Int_DroneControl_Recon_Size3_Class1', eddbid:1637 },
//            21550 : { mtype:'inlc', cost: 75800, name:'Recon Limpet Controller', class:5, rating:'E', mass: 20.00, integ: 96, pwrdraw:0.50,boottime:9.85,maxlimpet: 1, lpactrng:1700, maxspd:100, hacktime:13, fdid:128841593, fdname:'Int_DroneControl_Recon_Size5_Class1', eddbid:1638 },
//            21750 : { mtype:'inlc', cost:612200, name:'Recon Limpet Controller', class:7, rating:'E', mass:128.00, integ:157, pwrdraw:0.97, boottime:10, maxlimpet: 1, lpactrng:2000, maxspd:100, hacktime:10, fdid:128841594, fdname:'Int_DroneControl_Recon_Size7_Class1', eddbid:1639 },
    public static final ReconLimpetController RECON_LIMPET_CONTROLLER_1_E = new ReconLimpetController("RECON_LIMPET_CONTROLLER_1_E", HorizonsBlueprintName.RECON_LIMPET_CONTROLLER, ModuleSize.SIZE_1, ModuleClass.E, true, 2600, "Int_DroneControl_Recon_Size1_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  1.3), Map.entry(HorizonsModifier.INTEGRITY,  24.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.18), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1200.0), Map.entry(HorizonsModifier.DRONE_SPEED,  100.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  22.0)));
    public static final ReconLimpetController RECON_LIMPET_CONTROLLER_3_E = new ReconLimpetController("RECON_LIMPET_CONTROLLER_3_E", HorizonsBlueprintName.RECON_LIMPET_CONTROLLER, ModuleSize.SIZE_3, ModuleClass.E, true, 8200, "Int_DroneControl_Recon_Size3_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  2.0), Map.entry(HorizonsModifier.INTEGRITY,  51.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.2), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1400.0), Map.entry(HorizonsModifier.DRONE_SPEED,  100.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  17.0)));
    public static final ReconLimpetController RECON_LIMPET_CONTROLLER_5_E = new ReconLimpetController("RECON_LIMPET_CONTROLLER_5_E", HorizonsBlueprintName.RECON_LIMPET_CONTROLLER, ModuleSize.SIZE_5, ModuleClass.E, true, 75800, "Int_DroneControl_Recon_Size5_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  20.0), Map.entry(HorizonsModifier.INTEGRITY,  96.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.5), Map.entry(HorizonsModifier.BOOT_TIME,  9.85), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  1700.0), Map.entry(HorizonsModifier.DRONE_SPEED,  100.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  13.0)));
    public static final ReconLimpetController RECON_LIMPET_CONTROLLER_7_E = new ReconLimpetController("RECON_LIMPET_CONTROLLER_7_E", HorizonsBlueprintName.RECON_LIMPET_CONTROLLER, ModuleSize.SIZE_7, ModuleClass.E, true, 612200, "Int_DroneControl_Recon_Size7_Class1", Map.ofEntries(Map.entry(HorizonsModifier.MASS,  128.0), Map.entry(HorizonsModifier.INTEGRITY,  157.0), Map.entry(HorizonsModifier.POWER_DRAW,  0.97), Map.entry(HorizonsModifier.BOOT_TIME,  10.0), Map.entry(HorizonsModifier.MAX_ACTIVE_LIMPETS,  1.0), Map.entry(HorizonsModifier.DRONE_ACTIVE_RANGE,  2000.0), Map.entry(HorizonsModifier.DRONE_SPEED,  100.0), Map.entry(HorizonsModifier.DRONE_HACKING_TIME,  10.0)));

    public static final List<ReconLimpetController> RECON_LIMPET_CONTROLLERS = List.of(
            RECON_LIMPET_CONTROLLER_1_E,
            RECON_LIMPET_CONTROLLER_3_E,
            RECON_LIMPET_CONTROLLER_5_E,
            RECON_LIMPET_CONTROLLER_7_E
    );
    public ReconLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
    }

    public ReconLimpetController(String id, HorizonsBlueprintName name, ModuleSize moduleSize, ModuleClass moduleClass, Origin origin, boolean multiCrew, int basePrice, String internalName, Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
    }

    public ReconLimpetController(ReconLimpetController reconLimpetController) {
        super(reconLimpetController);
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
    public ReconLimpetController Clone() {
        return new ReconLimpetController(this);
    }
}
