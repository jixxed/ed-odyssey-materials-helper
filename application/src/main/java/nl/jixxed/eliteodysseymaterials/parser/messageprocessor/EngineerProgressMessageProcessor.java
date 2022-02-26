package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

@Slf4j
public class EngineerProgressMessageProcessor implements MessageProcessor {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String ENGINEER = "Engineer";
    private static final String PROGRESS = "Progress";
    private static final String RANK = "Rank";
    private static final String RANK_PROGRESS = "RankProgress";
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
            final Integer rank = item.get(RANK) != null ? item.get(RANK).asInt() : EngineerState.INVITED.equals(engineerState) ? -1 : EngineerState.KNOWN.equals(engineerState) ? -2 : EngineerState.UNKNOWN.equals(engineerState) ? -3 : 0;
            final Integer rankProgress = item.get(RANK_PROGRESS) != null ? item.get(RANK_PROGRESS).asInt() : 0;
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
                case "Colonel Bris Dekker" -> APPLICATION_STATE.setEngineerStatus(Engineer.COLONEL_BRIS_DEKKER, engineerState, rank, rankProgress);
                case "Marco Qwent" -> APPLICATION_STATE.setEngineerStatus(Engineer.MARCO_QWENT, engineerState, rank, rankProgress);
                case "The Dweller" -> APPLICATION_STATE.setEngineerStatus(Engineer.THE_DWELLER, engineerState, rank, rankProgress);
                case "Lori Jameson" -> APPLICATION_STATE.setEngineerStatus(Engineer.LORI_JAMESON, engineerState, rank, rankProgress);
                case "The Sarge" -> APPLICATION_STATE.setEngineerStatus(Engineer.THE_SARGE, engineerState, rank, rankProgress);
                case "Selene Jean" -> APPLICATION_STATE.setEngineerStatus(Engineer.SELENE_JEAN, engineerState, rank, rankProgress);
                case "Liz Ryder" -> APPLICATION_STATE.setEngineerStatus(Engineer.LIZ_RYDER, engineerState, rank, rankProgress);
                case "Bill Turner" -> APPLICATION_STATE.setEngineerStatus(Engineer.BILL_TURNER, engineerState, rank, rankProgress);
                case "Tod 'The Blaster' McQuinn" -> APPLICATION_STATE.setEngineerStatus(Engineer.TOD_THE_BLASTER_MCQUINN, engineerState, rank, rankProgress);
                case "Professor Palin" -> APPLICATION_STATE.setEngineerStatus(Engineer.PROFESSOR_PALIN, engineerState, rank, rankProgress);
                case "Didi Vatermann" -> APPLICATION_STATE.setEngineerStatus(Engineer.DIDI_VATERMANN, engineerState, rank, rankProgress);
                case "Juri Ishmaak" -> APPLICATION_STATE.setEngineerStatus(Engineer.JURI_ISHMAAK, engineerState, rank, rankProgress);
                case "Lei Cheung" -> APPLICATION_STATE.setEngineerStatus(Engineer.LEI_CHEUNG, engineerState, rank, rankProgress);
                case "Felicity Farseer" -> APPLICATION_STATE.setEngineerStatus(Engineer.FELICITY_FARSEER, engineerState, rank, rankProgress);
                case "Tiana Fortune" -> APPLICATION_STATE.setEngineerStatus(Engineer.TIANA_FORTUNE, engineerState, rank, rankProgress);
                case "Zacariah Nemo" -> APPLICATION_STATE.setEngineerStatus(Engineer.ZACARIAH_NEMO, engineerState, rank, rankProgress);
                case "Ram Tah" -> APPLICATION_STATE.setEngineerStatus(Engineer.RAM_TAH, engineerState, rank, rankProgress);
                case "Broo Tarquin" -> APPLICATION_STATE.setEngineerStatus(Engineer.BROO_TARQUIN, engineerState, rank, rankProgress);
                case "Elvira Martuuk" -> APPLICATION_STATE.setEngineerStatus(Engineer.ELVIRA_MARTUUK, engineerState, rank, rankProgress);
                case "Hera Tani" -> APPLICATION_STATE.setEngineerStatus(Engineer.HERA_TANI, engineerState, rank, rankProgress);
                case "Chloe Sedesi" -> APPLICATION_STATE.setEngineerStatus(Engineer.CHLOE_SEDESI, engineerState, rank, rankProgress);
                case "Marsha Hicks" -> APPLICATION_STATE.setEngineerStatus(Engineer.MARSHA_HICKS, engineerState, rank, rankProgress);
                case "Etienne Dorn" -> APPLICATION_STATE.setEngineerStatus(Engineer.ETIENNE_DORN, engineerState, rank, rankProgress);
                case "Mel Brandon" -> APPLICATION_STATE.setEngineerStatus(Engineer.MEL_BRANDON, engineerState, rank, rankProgress);
                case "Petra Olmanova" -> APPLICATION_STATE.setEngineerStatus(Engineer.PETRA_OLMANOVA, engineerState, rank, rankProgress);
                default -> log.warn("Unknown engineer: " + engineer);
            }
            EventService.publish(new EngineerEvent());
        }
    }

}
