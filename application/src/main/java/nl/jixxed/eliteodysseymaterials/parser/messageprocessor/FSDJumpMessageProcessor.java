package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.SystemEconomy;
import nl.jixxed.eliteodysseymaterials.enums.SystemGovernment;
import nl.jixxed.eliteodysseymaterials.enums.SystemSecurity;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.FSDJumpJournalEvent;

import java.util.Iterator;

public class FSDJumpMessageProcessor implements MessageProcessor {
    @Override
    public void process(final JsonNode journalMessage) {
        final String timestamp = asTextOrBlank(journalMessage, "timestamp");
        final String body = asTextOrBlank(journalMessage, "Body");
        final String starSystem = asTextOrBlank(journalMessage, "StarSystem");
        final String allegiance = asTextOrBlank(journalMessage, "SystemAllegiance");
        final String economy = asTextOrBlank(journalMessage, "SystemEconomy");
        final String secondEconomy = asTextOrBlank(journalMessage, "SystemSecondEconomy");
        final String government = asTextOrBlank(journalMessage, "SystemGovernment");
        final String security = asTextOrBlank(journalMessage, "SystemSecurity");
        final String factionState = journalMessage.get("SystemFaction") != null ? asTextOrBlank(journalMessage.get("SystemFaction"), "FactionState") : "";
        if (!starSystem.isBlank() && journalMessage.get("StarPos") != null) {
            final Iterator<JsonNode> starPos = journalMessage.get("StarPos").elements();
            final double x = starPos.next().asDouble();
            final double y = starPos.next().asDouble();
            final double z = starPos.next().asDouble();
            EventService.publish(new FSDJumpJournalEvent(timestamp, new StarSystem(starSystem, SystemEconomy.forKey(economy), SystemEconomy.forKey(secondEconomy), SystemGovernment.forKey(government), SystemSecurity.forKey(security), factionState, x, y, z), body));
        }
    }
}
