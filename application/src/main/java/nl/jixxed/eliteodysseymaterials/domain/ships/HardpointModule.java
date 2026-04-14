/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.domain.ships;

import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsModifier;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public abstract class HardpointModule extends ExternalModule {
    public HardpointModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final boolean multiCrew, final Mounting mounting, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
        super(id, name, moduleSize, moduleClass, mounting, multiCrew, basePrice, internalName, attributes);
    }

    public HardpointModule(final String id, final HorizonsBlueprintName name, final ModuleSize moduleSize, final ModuleClass moduleClass, final Origin origin, final boolean multiCrew, final Mounting mounting, final long basePrice, final String internalName, final Map<HorizonsModifier, Object> attributes) {
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
        String name = LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey()) + " " + getModuleSize().intValue() + getModuleClass().name() + "-" + getMounting().getShortName();        //add engineered / experimental
        if (!getModifications().isEmpty()) {
            name += " [" + shortenName(getModifications().getFirst().getModification().name()) + " G" +getModifications().getFirst().getGrade().getGrade() + " @ " + Formatters.NUMBER_FORMAT_0.format(getModifications().getFirst().getModificationCompleteness().map(BigDecimal::doubleValue).orElse(0D) * 100)+ "%";
            if (!getExperimentalEffects().isEmpty()) {
                name += " + " + shortenName(getExperimentalEffects().getFirst().name());
            }
            name +=  "]";
        }
        return name;
    }
}
