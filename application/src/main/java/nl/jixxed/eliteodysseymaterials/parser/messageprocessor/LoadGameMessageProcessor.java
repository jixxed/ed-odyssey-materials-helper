package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.GameMode;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LoadGameEvent;

/**
 * When written: at startup, when loading from main menu into game
 * Parameters:
 * - Commander: commander name
 * - FID: player id
 * - Horizons: bool
 * - Odyssey: bool
 * - Ship: current ship type
 * - ShipID: ship id number (indicates which of your ships you are in)
 * - StartLanded: true (only present if landed)
 * - StartDead:true (only present if starting dead: see "Resurrect")
 * - GameMode: Open, Solo or Group
 * - Group: name of group (if in a group)
 * - Credits: current credit balance
 * - Loan: current loan
 * - ShipName: user-defined ship name
 * - ShipIdent: user-defined ship ID string
 * - FuelLevel: current fuel
 * - FuelCapacity: size of main tank
 * - language
 * - gameversion
 * - build
 */
public class LoadGameMessageProcessor implements MessageProcessor {

    @Override
    public void process(final JsonNode journalMessage) {
        final String gameMode = journalMessage.get("GameMode").asText();
        final Expansion expansion = journalMessage.get("Odyssey") != null && journalMessage.get("Odyssey").asBoolean() ? Expansion.ODYSSEY : Expansion.HORIZONS;
        EventService.publish(new LoadGameEvent(GameMode.valueOf(gameMode.toUpperCase()), expansion));
    }
}
