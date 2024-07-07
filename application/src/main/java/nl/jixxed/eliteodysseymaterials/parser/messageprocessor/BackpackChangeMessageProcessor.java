package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import javafx.util.Pair;
import nl.jixxed.eliteodysseymaterials.constants.OdysseyBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.domain.Location;
import nl.jixxed.eliteodysseymaterials.enums.Consumable;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Operation;
import nl.jixxed.eliteodysseymaterials.schemas.journal.BackpackChange.BackpackChange;
import nl.jixxed.eliteodysseymaterials.schemas.journal.BackpackChange.ChangeEntry;
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackChangeEvent;
import nl.jixxed.eliteodysseymaterials.service.event.BackpackEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
public class BackpackChangeMessageProcessor implements MessageProcessor<BackpackChange> {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public BackpackChangeMessageProcessor() {
    }

    @Override
    public Class<BackpackChange> getMessageClass() {
        return BackpackChange.class;
    }

    @Override
    public void process(final BackpackChange event) {
        final String timestamp = event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        EventService.publish(new BackpackEvent(timestamp));
        event.getAdded().ifPresent(added -> publishBackpackChangeEvents(added, Operation.ADDED, timestamp));
        event.getRemoved().ifPresent(removed -> publishBackpackChangeEvents(removed, Operation.REMOVED, timestamp));
        event.getAdded().ifPresent(added -> {
            if (!added.isEmpty()) {
                added.stream()
                        .filter(changeEntry -> changeEntry.getMissionID().isEmpty())
                        .map(changeEntry -> {
                            try {
                                final OdysseyMaterial odysseyMaterial = OdysseyMaterial.subtypeForName(changeEntry.getName());
                                return new Pair<OdysseyMaterial,Long>(odysseyMaterial,changeEntry.getCount().longValue());
                            } catch (final IllegalArgumentException e) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .filter(pair -> !(pair.getKey() instanceof Consumable))
                        .forEach(pair -> {
                            if ((APPLICATION_STATE.getSoloMode() && OdysseyBlueprintConstants.isNotRelevantWithOverrideAndNotRequiredEngineeringIngredient(pair.getKey()))
                                    || (!APPLICATION_STATE.getSoloMode() && !OdysseyBlueprintConstants.isEngineeringOrBlueprintIngredientWithOverride(pair.getKey()))) {
                                NotificationService.showInformation(NotificationType.IRRELEVANT_PICKUP, LocaleService.getLocalizedStringForCurrentLocale("notification.collected.irrelevant.material.title"), LocaleService.getLocalizedStringForCurrentLocale(pair.getKey().getLocalizationKey()));
                            } else if (WishlistService.isMaterialOnWishlist(pair.getKey())) {
                                NotificationService.showInformation(NotificationType.WISHLIST_PICKUP, LocaleService.getLocalizedStringForCurrentLocale("notification.collected.wishlist.material.title"),
                                        LocaleService.getLocalizedStringForCurrentLocale("notification.collected.wishlist.material.notification",
                                                LocaleService.LocalizationKey.of(pair.getKey().getLocalizationKey()),
                                                StorageService.getMaterialStorage(pair.getKey()).getTotalValue() + pair.getValue(),
                                                WishlistService.getAllWishlistsCount(pair.getKey()))
                                );
                            }
                        });
            }
        });

    }

    private void publishBackpackChangeEvents(final List<ChangeEntry> changeEntries, final Operation operation, final String timestamp) {
        StreamSupport.stream((changeEntries != null) ? changeEntries.spliterator() : Spliterators.emptySpliterator(), false)
                .forEach(changeEntry -> {
                    try {
                        EventService.publish(createEvent(operation, changeEntry, timestamp));
                    } catch (final IllegalArgumentException e) {
                        NotificationService.showWarning(NotificationType.ERROR, "Unknown Material Detected", changeEntry.getName() + "\nPlease report!");
                    }
                });
    }

    private BackpackChangeEvent createEvent(final Operation operation, final ChangeEntry changeEntry, final String timestamp) {
        final Location currentLocation = LocationService.getCurrentLocation();
        return BackpackChangeEvent.builder()
                .odysseyMaterial(OdysseyMaterial.subtypeForName(changeEntry.getName()))
                .amount(changeEntry.getCount().intValue())
                .operation(operation)
                .timestamp(timestamp)
                .commander(APPLICATION_STATE.getPreferredCommander().map(Commander::getName).orElse(""))
                .system(currentLocation.getStarSystem().getName())
                .primaryEconomy(currentLocation.getStarSystem().getPrimaryEconomy().name())
                .secondaryEconomy(currentLocation.getStarSystem().getSecondaryEconomy().name())
                .government(currentLocation.getStarSystem().getGovernment().name())
                .security(currentLocation.getStarSystem().getSecurity().name())
                .state(currentLocation.getStarSystem().getState().name())
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
