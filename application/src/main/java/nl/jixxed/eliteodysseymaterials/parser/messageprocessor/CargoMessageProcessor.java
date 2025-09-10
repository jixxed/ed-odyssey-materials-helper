package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Cargo.Cargo;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Cargo.Inventory;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.CargoEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CargoMessageProcessor implements MessageProcessor<Cargo> {
    private Cargo previousShipEvent;

    //    private Cargo previousSrvEvent;
    @Override
    @SuppressWarnings("java:S1192")
    public void process(final Cargo event) {
        if (event.getInventory().isEmpty()) {
            final String timestamp = event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            EventService.publish(new CargoEvent(timestamp));
            return;
        }
        if (onSquadronCarrier() && isBankTransfer(event.getTimestamp())) {
            List<Inventory> changes = diff(previousShipEvent, event);
            processCargo(changes, StoragePool.SQUADRONCARRIER);
        }
        if ("Ship".equals(event.getVessel())) {
            processCargo(event, StoragePool.SHIP, StorageService::resetHorizonsCommodityCounts);
            previousShipEvent = event;
        } else if ("Srv".equals(event.getVessel())) {
            processCargo(event, StoragePool.SRV, StorageService::resetSrvCounts);
//            previousSrvEvent = event;
        }
    }

    private List<Inventory> diff(Cargo previousShipEvent, Cargo event) {
        List<Inventory> currentInventory = event.getInventory().orElse(List.of());
        List<Inventory> previousInventory = previousShipEvent != null ? previousShipEvent.getInventory().orElse(List.of()) : List.of();

        // Items that are new or changed
        List<Inventory> changedOrNew = currentInventory.stream()
                .filter(currentItem -> {
                    Inventory previousItem = previousInventory.stream()
                            .filter(item -> item.getName().equals(currentItem.getName()))
                            .findFirst().orElse(null);
                    return previousItem == null || !previousItem.getCount().equals(currentItem.getCount());
                })
                .map(currentItem -> {
                    Inventory previousItem = previousInventory.stream()
                            .filter(item -> item.getName().equals(currentItem.getName()))
                            .findFirst().orElse(null);

                    Inventory diffItem = new Inventory();
                    diffItem.setName(currentItem.getName());
                    diffItem.setMissionID(currentItem.getMissionID().orElse(null));
                    diffItem.setStolen(currentItem.getStolen());
                    diffItem.setName_Localised(currentItem.getName_Localised().orElse(null));
                    diffItem.setCount(previousItem == null ? currentItem.getCount().negate() : currentItem.getCount().subtract(previousItem.getCount()).negate());
                    return diffItem;
                })
                .toList();

        // Items that were removed
        List<Inventory> removed = previousInventory.stream()
                .filter(previousItem -> currentInventory.stream()
                        .noneMatch(item -> item.getName().equals(previousItem.getName())))
                .map(removedItem -> {
                    Inventory diffItem = new Inventory();
                    diffItem.setName(removedItem.getName());
                    diffItem.setMissionID(removedItem.getMissionID().orElse(null));
                    diffItem.setStolen(removedItem.getStolen());
                    diffItem.setName_Localised(removedItem.getName_Localised().orElse(null));
                    diffItem.setCount(removedItem.getCount());
                    return diffItem;
                })
                .toList();

        // Combine both lists
        List<Inventory> result = new java.util.ArrayList<>(changedOrNew);
        result.addAll(removed);
        return result;
    }

    public static boolean isBankTransfer(LocalDateTime timestamp) {
        //if in the previous 5 seconds there was a cargo transfer or eject cargo, it's not a bank transfer
        return (CargoTransferMessageProcessor.lastCargoTransfer == null || timestamp.minusSeconds(5).isAfter(CargoTransferMessageProcessor.lastCargoTransfer))
                && (EjectCargoMessageProcessor.lastEjectCargo == null || timestamp.minusSeconds(5).isAfter(EjectCargoMessageProcessor.lastEjectCargo));
    }

    private boolean onSquadronCarrier() {
        return LocationService.isAtSquadronCarrier();
    }

    private void processCargo(final Cargo event, final StoragePool storagePool, final Runnable storageResetRunnable) {
        storageResetRunnable.run();
        event.getInventory().ifPresent(inventory -> inventory.forEach(inventoryItem -> {
            final Commodity commodity = Commodity.forName(inventoryItem.getName());
            if (commodity.isUnknown()) {
                ReportService.reportMaterial(inventoryItem);
            } else {
                StorageService.addCommodity(commodity, storagePool, inventoryItem.getCount().intValue());
            }
        }));
        EventService.publish(new StorageEvent(storagePool));
    }

    private void processCargo(final List<Inventory> changes, final StoragePool storagePool) {
        changes.forEach(inventoryItem -> {
            final Commodity commodity = Commodity.forName(inventoryItem.getName());
            if (commodity.isUnknown()) {
                ReportService.reportMaterial(inventoryItem);
            } else {
                StorageService.addCommodity(commodity, storagePool, inventoryItem.getCount().intValue());
            }
        });
        EventService.publish(new StorageEvent(storagePool));
    }

    @Override
    public Class<Cargo> getMessageClass() {
        return Cargo.class;
    }
}