package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.schemas.journal.CargoTransfer.CargoTransfer;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.StorageEvent;

public class CargoTransferMessageProcessor implements MessageProcessor<CargoTransfer> {
    @Override
    @SuppressWarnings("java:S1192")
    public void process(final CargoTransfer event) {
        event.getTransfers().forEach(transfer -> {
            final Commodity commodity = Commodity.forName(transfer.getType());
            if (commodity.isUnknown()) {
                ReportService.reportMaterial(event);
            } else {
                if ("tocarrier".equals(transfer.getDirection())) {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, transfer.getCount().intValue());
                    StorageService.addCommodity(commodity, StoragePool.FLEETCARRIER, transfer.getCount().intValue());
                    EventService.publish(new StorageEvent(StoragePool.SHIP));
                    EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));
                } else if ("tosrv".equals(transfer.getDirection())) {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, transfer.getCount().intValue());
                    StorageService.addCommodity(commodity, StoragePool.SRV, transfer.getCount().intValue());
                    EventService.publish(new StorageEvent(StoragePool.SHIP));
                    EventService.publish(new StorageEvent(StoragePool.SRV));
                } else if ("toship".equals(transfer.getDirection())) {
                    if (ApplicationState.getInstance().playerInSrv()) {
                        StorageService.removeCommodity(commodity, StoragePool.SRV, transfer.getCount().intValue());
                        EventService.publish(new StorageEvent(StoragePool.SRV));
                    } else {
                        StorageService.removeCommodity(commodity, StoragePool.FLEETCARRIER, transfer.getCount().intValue());
                        EventService.publish(new StorageEvent(StoragePool.FLEETCARRIER));
                    }
                    StorageService.addCommodity(commodity, StoragePool.SHIP, transfer.getCount().intValue());
                    EventService.publish(new StorageEvent(StoragePool.SHIP));
                }
            }
        });
    }

    @Override
    public Class<CargoTransfer> getMessageClass() {
        return CargoTransfer.class;
    }

}