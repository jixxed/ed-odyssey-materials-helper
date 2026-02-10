package nl.jixxed.eliteodysseymaterials.parser.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;
import nl.jixxed.eliteodysseymaterials.schemas.journal.EngineerProgress.EngineerProgress;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;

import java.math.BigInteger;
import java.util.Optional;

@Slf4j
@SuppressWarnings("java:S1479")
public class EngineerProgressMessageProcessor implements MessageProcessor<EngineerProgress> {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    @Override
    public void process(final EngineerProgress engineerProgress) {
        if (engineerProgress.getEngineers().isPresent()) {
            engineerProgress.getEngineers().get().forEach(engineer -> processEngineerProgressItem(engineer.getEngineer(), engineer.getProgress(), engineer.getRankProgress(), engineer.getRank()));
        } else if (engineerProgress.getEngineer().isPresent()) {
            processEngineerProgressItem(engineerProgress.getEngineer(), engineerProgress.getProgress(), engineerProgress.getRankProgress(), engineerProgress.getRank());
        }
        EventService.publish(new EngineerEvent());
    }

    @Override
    public Class<EngineerProgress> getMessageClass() {
        return EngineerProgress.class;
    }

    private static void processEngineerProgressItem(final Optional<String> engineer, final Optional<String> progress, final Optional<BigInteger> rankProgress, final Optional<BigInteger> engineerRank) {
        if (engineer.isPresent() && progress.isPresent()) {
            final String engineerName = engineer.get();
            final EngineerState engineerState = EngineerState.forName(progress.get());
            final Integer rank = engineerRank.map(BigInteger::intValue).orElseGet(() -> {
                if (EngineerState.INVITED.equals(engineerState)) {
                    return  -1;
                } else if (EngineerState.KNOWN.equals(engineerState)) {
                    return -2;
                } else if (EngineerState.UNKNOWN.equals(engineerState)) {
                    return -3;
                } else {
                    return 0;
                }
            });
            final Integer rankProgressValue = rankProgress.map(BigInteger::intValue).orElse(0);
            switch (engineerName) {
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
                case "Colonel Bris Dekker" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.COLONEL_BRIS_DEKKER, engineerState, rank, rankProgressValue);
                case "Marco Qwent" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.MARCO_QWENT, engineerState, rank, rankProgressValue);
                case "The Dweller" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.THE_DWELLER, engineerState, rank, rankProgressValue);
                case "Lori Jameson" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.LORI_JAMESON, engineerState, rank, rankProgressValue);
                case "The Sarge" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.THE_SARGE, engineerState, rank, rankProgressValue);
                case "Selene Jean" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.SELENE_JEAN, engineerState, rank, rankProgressValue);
                case "Liz Ryder" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.LIZ_RYDER, engineerState, rank, rankProgressValue);
                case "Bill Turner" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.BILL_TURNER, engineerState, rank, rankProgressValue);
                case "Tod 'The Blaster' McQuinn" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.TOD_THE_BLASTER_MCQUINN, engineerState, rank, rankProgressValue);
                case "Professor Palin" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.PROFESSOR_PALIN, engineerState, rank, rankProgressValue);
                case "Didi Vatermann" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.DIDI_VATERMANN, engineerState, rank, rankProgressValue);
                case "Juri Ishmaak" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.JURI_ISHMAAK, engineerState, rank, rankProgressValue);
                case "Lei Cheung" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.LEI_CHEUNG, engineerState, rank, rankProgressValue);
                case "Felicity Farseer" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.FELICITY_FARSEER, engineerState, rank, rankProgressValue);
                case "Tiana Fortune" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.TIANA_FORTUNE, engineerState, rank, rankProgressValue);
                case "Zacariah Nemo" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.ZACARIAH_NEMO, engineerState, rank, rankProgressValue);
                case "Ram Tah" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.RAM_TAH, engineerState, rank, rankProgressValue);
                case "Broo Tarquin" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.BROO_TARQUIN, engineerState, rank, rankProgressValue);
                case "Elvira Martuuk" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.ELVIRA_MARTUUK, engineerState, rank, rankProgressValue);
                case "Hera Tani" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.HERA_TANI, engineerState, rank, rankProgressValue);
                case "Chloe Sedesi" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.CHLOE_SEDESI, engineerState, rank, rankProgressValue);
                case "Marsha Hicks" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.MARSHA_HICKS, engineerState, rank, rankProgressValue);
                case "Etienne Dorn" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.ETIENNE_DORN, engineerState, rank, rankProgressValue);
                case "Mel Brandon" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.MEL_BRANDON, engineerState, rank, rankProgressValue);
                case "Petra Olmanova" ->
                        APPLICATION_STATE.setEngineerStatus(Engineer.PETRA_OLMANOVA, engineerState, rank, rankProgressValue);
                default -> log.warn("Unknown engineer: " + engineer);
            }
        }
    }

}
