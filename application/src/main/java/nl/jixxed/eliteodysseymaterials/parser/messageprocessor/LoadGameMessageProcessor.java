package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.GameMode;
import nl.jixxed.eliteodysseymaterials.schemas.journal.LoadGame.LoadGame;
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
public class LoadGameMessageProcessor implements MessageProcessor<LoadGame> {
    @Override
    public void process(final LoadGame event) {
        final String gameMode = event.getGameMode().orElse("none");
        final Expansion horizonsExpansion = event.getHorizons() ? Expansion.HORIZONS : Expansion.NONE;
        final Expansion expansion = event.getOdyssey().orElse(false) ? Expansion.ODYSSEY : horizonsExpansion;
        EventService.publish(new LoadGameEvent(GameMode.valueOf(gameMode.toUpperCase()), expansion));
    }

    @Override
    public Class<LoadGame> getMessageClass() {
        return LoadGame.class;
    }
}
