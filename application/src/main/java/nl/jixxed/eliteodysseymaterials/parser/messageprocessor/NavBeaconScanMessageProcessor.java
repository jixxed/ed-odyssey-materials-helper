package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.schemas.journal.NavBeaconScan.NavBeaconScan;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;

public class NavBeaconScanMessageProcessor implements MessageProcessor<NavBeaconScan> {
    @Override
    public void process(final NavBeaconScan event) {
        EDDNService.navbeaconscan(event);
    }

    @Override
    public Class<NavBeaconScan> getMessageClass() {
        return NavBeaconScan.class;
    }
}
