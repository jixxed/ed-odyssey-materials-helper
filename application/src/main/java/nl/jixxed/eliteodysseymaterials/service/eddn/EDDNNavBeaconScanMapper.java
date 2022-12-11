package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.navbeaconscan.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.NavBeaconScan.NavBeaconScan;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

public class EDDNNavBeaconScanMapper extends EDDNMapper {
    public static Message mapToEDDN(final NavBeaconScan navBeaconScan, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(navBeaconScan.getTimestamp())
                .withEvent(navBeaconScan.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withStarPos(LocationService.getCurrentStarPos(navBeaconScan.getSystemAddress()))
                .withStarSystem(LocationService.getCurrentStarSystemName(navBeaconScan.getSystemAddress()))
                .withSystemAddress(navBeaconScan.getSystemAddress())
                .withNumBodies(navBeaconScan.getNumBodies())
                .build();
    }
}
