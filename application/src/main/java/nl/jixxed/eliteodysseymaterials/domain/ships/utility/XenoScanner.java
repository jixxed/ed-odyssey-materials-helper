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

public class XenoScanner extends UtilityModule {
//            58050 : { mtype:'uex',  cost: 365700, name:'Xeno Scanner',              class:0, rating:'E', mass:1.30, integ:56, pwrdraw:0.20,            boottime:2, scanrng: 500, maxangle:23.00, scantime:10, limit:'uex', fdid:128793115, fdname:'Hpt_XenoScanner_Basic_Tiny', eddbid:1616 },
//            58030 : { mtype:'uex',  cost: 745950, name:'Enhanced Xeno Scanner',     class:0, rating:'C', mass:1.30, integ:56, pwrdraw:0.80,            boottime:2, scanrng:2000, maxangle:23.00, scantime:10, limit:'uex', fdid:128808878, fdname:'Hpt_XenoScannerMk2_Basic_Tiny', eddbid:null }, // TODO: eddbid


    public XenoScanner(final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(name, moduleSize, moduleClass, true, Mounting.NA, basePrice, internalName, attributes);
    }

    private XenoScanner(final XenoScanner xenoScanner) {
        super(xenoScanner);
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
    public XenoScanner Clone() {
        return new XenoScanner(this);
    }
}
