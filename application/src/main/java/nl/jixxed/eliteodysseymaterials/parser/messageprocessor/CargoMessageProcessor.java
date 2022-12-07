package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.journalevents.Cargo.Cargo;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.CargoEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

import java.time.format.DateTimeFormatter;

public class CargoMessageProcessor implements MessageProcessor<Cargo> {

    @Override
    @SuppressWarnings("java:S1192")
    public void process(final Cargo event) {
        if (event.getInventory().isEmpty()) {
            final String timestamp = event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            EventService.publish(new CargoEvent(timestamp));
            return;
        }
        if ("Ship".equals(event.getVessel())) {
            processCargo(event, StoragePool.SHIP, StorageService::resetHorizonsCommodityCounts);
        } else if ("Srv".equals(event.getVessel())) {
            processCargo(event, StoragePool.SRV, StorageService::resetSrvCounts);
        }

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

    @Override
    public Class<Cargo> getMessageClass() {
        return Cargo.class;
    }
}