package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Commodity;
import nl.jixxed.eliteodysseymaterials.enums.StoragePool;
import nl.jixxed.eliteodysseymaterials.journalevents.CargoTransfer.CargoTransfer;
import nl.jixxed.eliteodysseymaterials.service.ReportService;
import nl.jixxed.eliteodysseymaterials.service.StorageService;

public class CargoTransferMessageProcessor implements MessageProcessor<CargoTransfer> {
    @Override
    @SuppressWarnings("java:S1192")
    public void process(final CargoTransfer event) {
        event.getTransfers().forEach(transfer -> {
            if ("tocarrier".equals(transfer.getDirection())) {
                final Commodity commodity = Commodity.forName(transfer.getType());
                if (commodity.isUnknown()) {
                    ReportService.reportMaterial(event);
                } else {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, transfer.getCount().intValue());
                    StorageService.addCommodity(commodity, StoragePool.FLEETCARRIER, transfer.getCount().intValue());
                }
            } else if ("tosrv".equals(transfer.getDirection())) {
                final Commodity commodity = Commodity.forName(transfer.getType());
                if (commodity.isUnknown()) {
                    ReportService.reportMaterial(event);
                } else {
                    StorageService.removeCommodity(commodity, StoragePool.SHIP, transfer.getCount().intValue());
                    StorageService.addCommodity(commodity, StoragePool.SRV, transfer.getCount().intValue());
                }
            } else if ("toship".equals(transfer.getDirection())) {
                final Commodity commodity = Commodity.forName(transfer.getType());
                if (commodity.isUnknown()) {
                    ReportService.reportMaterial(event);
                } else {
                    if (ApplicationState.getInstance().playerInSrv()) {
                        StorageService.removeCommodity(commodity, StoragePool.SRV, transfer.getCount().intValue());
                    } else {
                        StorageService.removeCommodity(commodity, StoragePool.FLEETCARRIER, transfer.getCount().intValue());
                    }
                    StorageService.addCommodity(commodity, StoragePool.SHIP, transfer.getCount().intValue());
                }
            }
        });
    }
    @Override
    public Class<CargoTransfer> getMessageClass() {
        return CargoTransfer.class;
    }

}