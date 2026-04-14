/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ConstructionProgress;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ColonisationConstructionDepot.ColonisationConstructionDepot;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ColonisationConstructionDepot.ResourcesRequired;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationConstructionDepotEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ColonisationConstructionDepotMessageProcessor implements MessageProcessor<ColonisationConstructionDepot> {
    private ColonisationConstructionDepot previousEvent = null;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    public ColonisationConstructionDepotMessageProcessor() {
        eventListeners.add(EventService.addListener(this, JournalInitEvent.class, event -> {
            if (!event.isInitialised()) {
                this.previousEvent = null;
            }
        }));
    }

    @Override
    public void process(final ColonisationConstructionDepot event) {
//        //validate buildcost
//        int x = 0;
//        for (ColonisationBuildable buildable : ColonisationBuildable.values()) {
//            final Map<Commodity, ConstructionProgress> commodityIntegerMap = event.getResourcesRequired().stream()
//                    .map(resourcesRequired -> Map.entry(getCommodity(resourcesRequired.getName()), getConstructionProgress(resourcesRequired)))
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, ConstructionProgress::sum));
//            var x2 = buildable.getBlueprintCost().keySet().equals(commodityIntegerMap.keySet());
//            if (x2) {
//                x++;
//            }
//        }
//        if (x < 1) {
//            NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.value.text", "No match detected"), LocaleService.LocaleString.of("notification.value.text", "Please check ColonisationBuildable: " + x));
//        }
        //don't process if the event is the same as the previous one
        if (Objects.equals(previousEvent, event)) {
            return;
        }
        previousEvent = event;
        final Map<Commodity, ConstructionProgress> commodityIntegerMap = event.getResourcesRequired().stream()
                .map(resourcesRequired -> Map.entry(getCommodity(resourcesRequired.getName()), getConstructionProgress(resourcesRequired)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, ConstructionProgress::sum));
        EventService.publish(new ColonisationConstructionDepotEvent(event.getMarketID(), event.getConstructionProgress(), event.getConstructionComplete(), event.getConstructionFailed(), commodityIntegerMap));
    }

    private static ConstructionProgress getConstructionProgress(ResourcesRequired resourcesRequired) {
        return new ConstructionProgress(resourcesRequired.getProvidedAmount().intValue(), resourcesRequired.getRequiredAmount().intValue());
    }

    private static Commodity getCommodity(final String name) {
        // strip $ and _name;
        var strippedName = name.startsWith("$") ? name.substring(1, name.length() - 6) : name;
        return Commodity.forName(strippedName);
    }

    @Override
    public Class<ColonisationConstructionDepot> getMessageClass() {
        return ColonisationConstructionDepot.class;
    }
}
