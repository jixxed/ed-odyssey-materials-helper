package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

public class EngineerProgressMessageProcessor implements MessageProcessor {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String ENGINEER = "Engineer";
    private static final String PROGRESS = "Progress";
    private static final String ENGINEERS = "Engineers";

    @Override
    public void process(final JsonNode journalMessage) {
        if (journalMessage.get(ENGINEERS) != null) {
            journalMessage.get(ENGINEERS).elements().forEachRemaining(EngineerProgressMessageProcessor::processEngineerProgressItem);
        } else if (journalMessage.get(ENGINEER) != null) {
            processEngineerProgressItem(journalMessage);
        }

    }

    private static void processEngineerProgressItem(final JsonNode item) {
        if (item.get(ENGINEER) != null && item.get(PROGRESS) != null) {
            final String engineer = item.get(ENGINEER).asText();
            final EngineerState engineerState = EngineerState.forName(item.get(PROGRESS).asText());
            switch (engineer) {
                case "Domino Green" -> APPLICATION_STATE.setEngineerState(Engineer.DOMINO_GREEN, engineerState);
                case "Hero Ferrari" -> APPLICATION_STATE.setEngineerState(Engineer.HERO_FERRARI, engineerState);
                case "Jude Navarro" -> APPLICATION_STATE.setEngineerState(Engineer.JUDE_NAVARRO, engineerState);
                case "Kit Fowler" -> APPLICATION_STATE.setEngineerState(Engineer.KIT_FOWLER, engineerState);
                case "Oden Geiger" -> APPLICATION_STATE.setEngineerState(Engineer.ODEN_GEIGER, engineerState);
                case "Terra Velasquez" -> APPLICATION_STATE.setEngineerState(Engineer.TERRA_VELASQUEZ, engineerState);
                case "Uma Laszlo" -> APPLICATION_STATE.setEngineerState(Engineer.UMA_LASZLO, engineerState);
                case "Wellington Beck" -> APPLICATION_STATE.setEngineerState(Engineer.WELLINGTON_BECK, engineerState);
                case "Yarden Bond" -> APPLICATION_STATE.setEngineerState(Engineer.YARDEN_BOND, engineerState);
                case "Baltanos" -> APPLICATION_STATE.setEngineerState(Engineer.BALTANOS, engineerState);
                case "Rosa Dayette" -> APPLICATION_STATE.setEngineerState(Engineer.ROSA_DAYETTE, engineerState);
                case "Eleanor Bresa" -> APPLICATION_STATE.setEngineerState(Engineer.ELEANOR_BRESA, engineerState);
                case "Yi Shen" -> APPLICATION_STATE.setEngineerState(Engineer.YI_SHEN, engineerState);
                default -> {
                }
            }
            EventService.publish(new EngineerEvent());
        }
    }

}
