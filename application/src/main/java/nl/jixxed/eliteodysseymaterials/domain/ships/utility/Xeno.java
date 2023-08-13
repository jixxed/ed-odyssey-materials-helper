package nl.jixxed.eliteodysseymaterials.domain.ships.utility;

import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleClass;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.domain.ships.Mounting;
import nl.jixxed.eliteodysseymaterials.domain.ships.UtilityModule;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintType;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.List;
import java.util.Map;

public class Xeno extends UtilityModule {
//58061 : { mtype:'uex',  cost:  63000, name:'Shutdown Field Neutraliser',class:0, rating:'F', mass:1.30, integ:35, pwrdraw:0.20, passive:1, boottime:0, barrierrng:3000, barrierdur:1, barrierpwr:0.25, barriercool:10, fdid:128771884, fdname:'Hpt_AntiUnknownShutdown_Tiny', eddbid:1622 },
//            58052 : { mtype:'uex',  cost:      0, name:'Thargoid Pulse Neutraliser',class:0, rating:'E', mass:3.00, integ:70, pwrdraw:0.40, passive:1, boottime:0, barrierrng:   0, barrierdur:2, barrierpwr:0.33, barriercool:10, fdid:129022663, fdname:'Hpt_AntiUnknownShutdown_Tiny_V2', eddbid:null }, // Rescue Ship tech broker // verify: cost // TODO: eddbid
//            58050 : { mtype:'uex',  cost: 365700, name:'Xeno Scanner',              class:0, rating:'E', mass:1.30, integ:56, pwrdraw:0.20,            boottime:2, scanrng: 500, maxangle:23.00, scantime:10, limit:'uex', fdid:128793115, fdname:'Hpt_XenoScanner_Basic_Tiny', eddbid:1616 },
//            58030 : { mtype:'uex',  cost: 745950, name:'Enhanced Xeno Scanner',     class:0, rating:'C', mass:1.30, integ:56, pwrdraw:0.80,            boottime:2, scanrng:2000, maxangle:23.00, scantime:10, limit:'uex', fdid:128808878, fdname:'Hpt_XenoScannerMk2_Basic_Tiny', eddbid:1838 },
//            58031 : { mtype:'uex',  cost: 850000, name:'Pulse Wave Xeno Scanner',   class:0, rating:'C', mass:3.00,integ:100, pwrdraw:1.00,            boottime:2, scanrng:1000, maxangle:23.00, scantime:10, limit:'uex', fdid:129022952, fdname:'Hpt_XenoScanner_Advanced_Tiny', eddbid:null }, // TODO: eddbid


    public Xeno(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, true, Mounting.NA, basePrice, internalName, attributes);
    }

    private Xeno(final Xeno xeno) {
        super(xeno);
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedBlueprints() {
        return null;
    }

    @Override
    public List<HorizonsBlueprintType> getAllowedExperimentalEffects() {
        return null;
    }

    @Override
    public Xeno Clone() {
        return new Xeno(this);
    }
}
