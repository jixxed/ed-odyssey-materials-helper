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
