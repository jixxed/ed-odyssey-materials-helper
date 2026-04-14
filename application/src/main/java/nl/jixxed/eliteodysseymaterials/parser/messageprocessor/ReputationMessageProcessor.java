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

import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.CommanderReputation;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Reputation.Reputation;

import java.math.BigDecimal;

public class ReputationMessageProcessor implements MessageProcessor<Reputation> {
    @Override
    public void process(Reputation reputation) {
        ApplicationState.getInstance().setCommanderReputation(CommanderReputation.FEDERATION, reputation.getFederation().map(BigDecimal::doubleValue).orElse(0D));
        ApplicationState.getInstance().setCommanderReputation(CommanderReputation.EMPIRE, reputation.getEmpire().map(BigDecimal::doubleValue).orElse(0D));
        ApplicationState.getInstance().setCommanderReputation(CommanderReputation.ALLIANCE, reputation.getAlliance().map(BigDecimal::doubleValue).orElse(0D));
        ApplicationState.getInstance().setCommanderReputation(CommanderReputation.INDEPENDENT, reputation.getIndependent().map(BigDecimal::doubleValue).orElse(0D));
    }

    @Override
    public Class<Reputation> getMessageClass() {
        return Reputation.class;
    }
}
