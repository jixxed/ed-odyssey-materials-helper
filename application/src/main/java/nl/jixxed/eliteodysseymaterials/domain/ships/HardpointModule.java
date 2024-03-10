package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public abstract class HardpointModule extends ExternalModule {
    public HardpointModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, mounting, multiCrew, basePrice, internalName, attributes);
    }

    public HardpointModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final Mounting mounting, final int basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, origin, multiCrew, mounting, basePrice, internalName, attributes);
    }

    public HardpointModule(final HardpointModule hardpointModule) {
        super(hardpointModule);
    }


    public Optional<ExternalModule> findHigherMounting() {
        return getModules(SlotType.HARDPOINT).stream()
                .map(ExternalModule.class::cast)
                .filter(shipModule -> shipModule instanceof ExternalModule externalModule &&
                        shipModule.getName().equals(this.getName()) &&
                        shipModule.getModuleSize().equals(this.getModuleSize()) &&
                        externalModule.getMounting().isHigher(this.getMounting())
                )
                .sorted(Comparator.comparing(ExternalModule::getMounting))
                .findFirst();
    }
    public Optional<ExternalModule> findLowerMounting() {
        return getModules(SlotType.HARDPOINT).stream()
                .map(ExternalModule.class::cast)
                .filter(shipModule -> shipModule instanceof ExternalModule externalModule &&
                        shipModule.getName().equals(this.getName()) &&
                        shipModule.getModuleSize().equals(this.getModuleSize()) &&
                        externalModule.getMounting().isLower(this.getMounting())
                )
                .sorted(Comparator.comparing(ExternalModule::getMounting).reversed())
                .findFirst();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey()) + " " + getModuleSize().intValue() + getModuleClass().name() + "-" + getMounting().getShortName();
    }
}
