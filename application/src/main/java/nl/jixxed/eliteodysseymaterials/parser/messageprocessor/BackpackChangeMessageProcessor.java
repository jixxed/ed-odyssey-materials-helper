package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.Consumable;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Operation;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.MaterialService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackChangeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * This is written when there is any change to the contents of the suit backpack – note this can be
 * written at the same time as other events like UseConsumable
 * Parameters:
 * Either Added:[array of items] or Removed: [array of items]
 * Where each item contains:
 * - Name
 * - OwnerID
 * - MissionID (if relevant)
 * - Count
 * - Type
 */
public class BackpackChangeMessageProcessor implements MessageProcessor {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public BackpackChangeMessageProcessor() {
    }

    @Override
    public void process(final JsonNode journalMessage) {
        EventService.publish(new BackpackEvent(journalMessage.get("timestamp").asText()));
        final ArrayNode added = (ArrayNode) journalMessage.get("Added");
        final ArrayNode removed = (ArrayNode) journalMessage.get("Removed");
        final String timestamp = journalMessage.get("timestamp").asText();
        publishBackpackChangeEvents(added, Operation.ADDED, timestamp);
        publishBackpackChangeEvents(removed, Operation.REMOVED, timestamp);
        if (added != null && !added.isEmpty()) {
            StreamSupport.stream(added.spliterator(), false)
                    .map(jsonNode -> OdysseyMaterial.subtypeForName(jsonNode.get("Name").asText()))
                    .filter(material -> !(material instanceof Consumable))
                    .forEach(material -> {
                        if ((APPLICATION_STATE.getSoloMode() && OdysseyBlueprintConstants.isNotRelevantWithOverrideAndNotRequiredEngineeringIngredient(material))
                                || (!APPLICATION_STATE.getSoloMode() && !OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(material))) {
                            NotificationService.showInformation(NotificationType.IRRELEVANT_PICKUP, LocaleService.getLocalizedStringForCurrentLocale("notification.collected.irrelevant.material.title"), LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()));
                        } else if (MaterialService.isMaterialOnWishlist(material)) {
                            NotificationService.showInformation(NotificationType.WISHLIST_PICKUP, LocaleService.getLocalizedStringForCurrentLocale("notification.collected.wishlist.material.title"), LocaleService.getLocalizedStringForCurrentLocale(material.getLocalizationKey()));
                        }
                    });
        }
    }

    private void publishBackpackChangeEvents(final ArrayNode arrayNode, final Operation operation, final String timestamp) {
        StreamSupport.stream((arrayNode != null) ? arrayNode.spliterator() : Spliterators.emptySpliterator(), false)
                .forEach(jsonNode -> EventService.publish(createEvent(operation, jsonNode, timestamp)));
    }

    private BackpackChangeEvent createEvent(final Operation operation, final JsonNode jsonNode, final String timestamp) {
        final Location currentLocation = LocationService.getCurrentLocation();
        return BackpackChangeEvent.builder()
                .odysseyMaterial(OdysseyMaterial.subtypeForName(jsonNode.get("Name").asText()))
                .amount(jsonNode.get("Count").asInt())
                .operation(operation)
                .timestamp(timestamp)
                .commander(APPLICATION_STATE.getPreferredCommander().map(Commander::getName).orElse(""))
                .system(currentLocation.getStarSystem().getName())
                .primaryEconomy(currentLocation.getStarSystem().getPrimaryEconomy().name())
                .secondaryEconomy(currentLocation.getStarSystem().getSecondaryEconomy().name())
                .government(currentLocation.getStarSystem().getGovernment().name())
                .security(currentLocation.getStarSystem().getSecurity().name())
                .state(currentLocation.getStarSystem().getState())
                .body(currentLocation.getBody())
                .settlement(currentLocation.getStation())
                .x(currentLocation.getStarSystem().getX())
                .y(currentLocation.getStarSystem().getY())
                .z(currentLocation.getStarSystem().getZ())
                .latitude(currentLocation.getLatitude())
                .longitude(currentLocation.getLongitude())
                .build();
    }
}
