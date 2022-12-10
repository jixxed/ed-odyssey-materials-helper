package nl.jixxed.eliteodysseymaterials.parser;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.Encoded;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterial;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.MaterialTrade;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.Paid;
import nl.jixxed.eliteodysseymaterials.schemas.journal.MaterialTrade.Received;

import java.util.Map;

@Slf4j
public class EncodedTradeParser implements HorizonsParser {

    @Override
    public void parse(final MaterialTrade event, final Map<HorizonsMaterial, Integer> storage) {
        final Paid paid = event.getPaid();
        final String paidName =paid.getMaterial();
        final int paidAmount = paid.getQuantity().intValue();
        final Received received = event.getReceived();
        final String receivedName = received.getMaterial();
        final int receivedAmount = received.getQuantity().intValue();
        final Encoded paidMaterial = Encoded.forName(paidName);
        if (Encoded.UNKNOWN.equals(paidMaterial)) {
            log.warn("Unknown Paid Encoded data detected: " + event);
        } else {
            storage.put(paidMaterial, storage.get(paidMaterial) - paidAmount);
        }
        final Encoded receivedMaterial = Encoded.forName(receivedName);
        if (Encoded.UNKNOWN.equals(receivedMaterial)) {
            log.warn("Unknown Received Encoded data detected: " + event);
        } else {
            storage.put(receivedMaterial, storage.get(receivedMaterial) + receivedAmount);
        }
    }
}
