/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import nl.jixxed.eliteodysseymaterials.enums.Expansion;
import nl.jixxed.eliteodysseymaterials.enums.GameMode;
import nl.jixxed.eliteodysseymaterials.schemas.journal.LoadGame.LoadGame;
import nl.jixxed.eliteodysseymaterials.service.LedgerService;
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
        LedgerService.setCredits(event.getCredits().longValue());
        EventService.publish(new LoadGameEvent(GameMode.valueOf(gameMode.toUpperCase()), expansion));
    }

    @Override
    public Class<LoadGame> getMessageClass() {
        return LoadGame.class;
    }
}
