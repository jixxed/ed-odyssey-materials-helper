package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;

import java.util.Map;

public abstract class ExternalModule extends ShipModule {
    @Getter
    private final Mounting mounting;
    ExternalModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Mounting mounting, final boolean multiCrew, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
        this.mounting = mounting;
    }

    ExternalModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final Mounting mounting, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, basePrice, internalName, attributes);
        this.mounting = mounting;
    }

    ExternalModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final Mounting mounting, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, multiCrew, basePrice, internalName, attributes);
        this.mounting = mounting;
    }

    ExternalModule(final ExternalModule externalModule) {
        super(externalModule);
        this.mounting = externalModule.mounting;
    }

    public String getMountingClarifier(){
        return Mounting.NA.equals(this.mounting) ? "" : " (" + this.mounting.getShortName() + ")";
    }

    @Override
    public boolean isSame(ShipModule other){
        //compare this module name, size, class, modifications, experimental effects
        return super.isSame(other) && other instanceof ExternalModule externalModule &&
                isSameMounting(externalModule);
    }

    public boolean isSameMounting(ShipModule other) {
        return other instanceof ExternalModule externalModule && this.getMounting() == externalModule.getMounting();
    }
}
