package nl.jixxed.eliteodysseymaterials.service.eddn;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.schemas.eddn.scanbarycentre.Message;
import nl.jixxed.eliteodysseymaterials.schemas.journal.ScanBaryCentre.ScanBaryCentre;
import nl.jixxed.eliteodysseymaterials.service.LocationService;

public class EDDNScanBaryCentreMapper extends EDDNMapper {
    public static Message mapToEDDN(final ScanBaryCentre scanBaryCentre, final Expansion expansion) {
        return new Message.MessageBuilder()
                .withTimestamp(scanBaryCentre.getTimestamp())
                .withEvent(scanBaryCentre.getEvent())
                .withHorizons(expansion.equals(Expansion.HORIZONS) || expansion.equals(Expansion.ODYSSEY))
                .withOdyssey(expansion.equals(Expansion.ODYSSEY))
                .withStarSystem(scanBaryCentre.getStarSystem())
                .withStarPos(LocationService.getCurrentStarPos(scanBaryCentre.getSystemAddress()))
                .withSystemAddress(scanBaryCentre.getSystemAddress())
                .withBodyID(scanBaryCentre.getBodyID())
                .withSemiMajorAxis(scanBaryCentre.getSemiMajorAxis())
                .withEccentricity(scanBaryCentre.getEccentricity())
                .withOrbitalInclination(scanBaryCentre.getOrbitalInclination())
                .withPeriapsis(scanBaryCentre.getPeriapsis())
                .withOrbitalPeriod(scanBaryCentre.getOrbitalPeriod())
                .withAscendingNode(scanBaryCentre.getAscendingNode())
                .withMeanAnomaly(scanBaryCentre.getMeanAnomaly())
                .build();
    }
}
