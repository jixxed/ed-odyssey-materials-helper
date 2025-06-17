package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ConstructionProgress;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ColonisationConstructionDepot.ColonisationConstructionDepot;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ColonisationConstructionDepot.ResourcesRequired;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
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
        //validate buildcost
        int x = 0;
        for (ColonisationBuildable buildable : ColonisationBuildable.values()) {
            final Map<Commodity, ConstructionProgress> commodityIntegerMap = event.getResourcesRequired().stream()
                    .map(resourcesRequired -> Map.entry(getCommodity(resourcesRequired.getName()), getConstructionProgress(resourcesRequired)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, ConstructionProgress::sum));
            var x2 = buildable.getBlueprintCost().keySet().equals(commodityIntegerMap.keySet());
            if (x2) {
                x++;
            }
        }
        if (x < 1) {
            NotificationService.showWarning(NotificationType.ERROR, LocaleService.LocaleString.of("notification.value.text", "No match detected"), LocaleService.LocaleString.of("notification.value.text", "Please check ColonisationBuildable: " + x));
        }
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
