package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Manufactured;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.MaterialTrade;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.Paid;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.Received;

import java.util.Map;

@Slf4j
public class ManufacturedTradeParser implements HorizonsParser<MaterialTrade> {
    @Override
    public void parse(final MaterialTrade event, final Map<HorizonsMaterial, Integer> storage) {
        final Paid paid = event.getPaid();
        final String paidName = paid.getMaterial();
        final int paidAmount = paid.getQuantity().intValue();
        final Received received = event.getReceived();
        final String receivedName = received.getMaterial();
        final int receivedAmount = received.getQuantity().intValue();
        final Manufactured paidMaterial = Manufactured.forName(paidName);
        if (Manufactured.UNKNOWN.equals(paidMaterial)) {
            log.warn("Unknown Paid Manufactured data detected: " + event);
        } else {
            storage.put(paidMaterial, Math.max(0, storage.get(paidMaterial) - paidAmount));
        }
        final Manufactured receivedMaterial = Manufactured.forName(receivedName);
        if (Manufactured.UNKNOWN.equals(receivedMaterial)) {
            log.warn("Unknown Received Manufactured data detected: " + event);
        } else {
            storage.put(receivedMaterial, Math.max(0, storage.get(receivedMaterial) + receivedAmount));
        }
    }
}
