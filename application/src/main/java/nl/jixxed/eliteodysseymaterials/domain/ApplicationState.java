package nl.jixxed.eliteodysseymaterials.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.CommanderAddedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EngineerEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LoadGameEvent;
import nl.jixxed.eliteodysseymaterials.service.event.trade.EnlistWebSocketEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.*;

@Slf4j
public class ApplicationState {
    @SuppressWarnings("java:S1068")
    private static PreferencesService preferencesService;//defined so app folder gets created for lockfile

    private static FileLock fileLock;
    private static ApplicationState applicationState;
    private final List<OdysseyMaterial> favourites = new ArrayList<>();
    private final Set<Commander> commanders = new HashSet<>();
    private final Map<Engineer, EngineerStatus> engineerStates = new EnumMap<>(Map.ofEntries(
            Map.entry(Engineer.DOMINO_GREEN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.HERO_FERRARI, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.JUDE_NAVARRO, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.KIT_FOWLER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ODEN_GEIGER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.TERRA_VELASQUEZ, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.UMA_LASZLO, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.WELLINGTON_BECK, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.YARDEN_BOND, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.BALTANOS, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ELEANOR_BRESA, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ROSA_DAYETTE, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.YI_SHEN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.COLONEL_BRIS_DEKKER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.MARCO_QWENT, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.THE_DWELLER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.LORI_JAMESON, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.THE_SARGE, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.SELENE_JEAN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.LIZ_RYDER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.BILL_TURNER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.TOD_THE_BLASTER_MCQUINN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.PROFESSOR_PALIN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.DIDI_VATERMANN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.JURI_ISHMAAK, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.LEI_CHEUNG, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.FELICITY_FARSEER, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.TIANA_FORTUNE, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ZACARIAH_NEMO, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.RAM_TAH, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.BROO_TARQUIN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ELVIRA_MARTUUK, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.HERA_TANI, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.CHLOE_SEDESI, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.MARSHA_HICKS, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.ETIENNE_DORN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.MEL_BRANDON, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.PETRA_OLMANOVA, new EngineerStatus(EngineerState.UNKNOWN, 0, 0)),
            Map.entry(Engineer.REMOTE_WORKSHOP, new EngineerStatus(EngineerState.UNLOCKED, 5, 0)),
            Map.entry(Engineer.UNKNOWN, new EngineerStatus(EngineerState.UNKNOWN, 0, 0))
    ));
    private GameMode gameMode = GameMode.NONE;
    @Getter
    private final GameVersion gameVersion = GameVersion.UNKNOWN;

    private ApplicationState() {
        final String fav = PreferencesService.getPreference("material.favourites", "");
        Arrays.stream(fav.split(","))
                .filter(material -> !material.isBlank())
                .map(OdysseyMaterial::subtypeForName)
                .forEach(this.favourites::add);


        EventService.addListener(this, EnlistWebSocketEvent.class, event -> getPreferredCommander().ifPresent(commander -> PreferencesService.setPreference(PreferenceConstants.MARKETPLACE_TOKEN_PREFIX + commander.getFid(), event.getEnlistMessage().getTrace().getToken())));
        EventService.addListener(this, LoadGameEvent.class, event -> this.gameMode = event.getGameMode());
    }

    public static ApplicationState getInstance() {
        if (applicationState == null) {
            applicationState = new ApplicationState();
        }
        return applicationState;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public boolean getSoloMode() {
        return PreferencesService.getPreference(PreferenceConstants.SOLO_MODE, Boolean.FALSE);
    }

    public boolean isEngineerKnown(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.KNOWN.equals(engineerState) || isEngineerUnlocked(engineer);

    }

    public boolean isEngineerUnlocked(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState);
    }

    public boolean isEngineerInvited(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.INVITED.equals(engineerState);
    }

    public boolean isEngineerUnlockedExact(final Engineer engineer) {
        final EngineerState engineerState = this.engineerStates.get(engineer).getEngineerState();
        return EngineerState.UNLOCKED.equals(engineerState);
    }

    public void setEngineerStatus(final Engineer engineer, final EngineerState engineerState, final Integer rank, final Integer progress) {
        final EngineerStatus engineerStatus = this.engineerStates.get(engineer);
        engineerStatus.setEngineerState(engineerState);
        engineerStatus.setRank(rank);
        engineerStatus.setProgress(progress);
    }

    public void setEngineerState(final Engineer engineer, final EngineerState engineerState) {
        this.engineerStates.get(engineer).setEngineerState(engineerState);
    }

    public void setEngineerRank(final Engineer engineer, final Integer rank) {
        this.engineerStates.get(engineer).setRank(rank);
    }

    public Integer getEngineerRank(final Engineer engineer) {
        return this.engineerStates.get(engineer).getRank();
    }

    public void setEngineerProgress(final Engineer engineer, final Integer progress) {
        this.engineerStates.get(engineer).setProgress(progress);
    }

    public Integer getEngineerProgress(final Engineer engineer) {
        return this.engineerStates.get(engineer).getProgress();
    }


    public void resetEngineerStates() {
        this.engineerStates.forEach((engineer, engineerState) -> {
            this.engineerStates.get(engineer).setEngineerState(EngineerState.UNKNOWN);
            this.engineerStates.get(engineer).setProgress(0);
            this.engineerStates.get(engineer).setRank(0);
        });

        final EngineerStatus engineerStatus = this.engineerStates.get(Engineer.REMOTE_WORKSHOP);
        engineerStatus.setEngineerState(EngineerState.UNLOCKED);
        engineerStatus.setRank(5);
        EventService.publish(new EngineerEvent());
    }

    public <T extends OdysseyMaterial> boolean toggleFavourite(final T material) {
        final boolean newState;
        if (this.favourites.contains(material)) {
            this.favourites.remove(material);
            newState = false;
        } else {
            this.favourites.add(material);
            newState = true;
        }
        PreferencesService.setPreference("material.favourites", this.favourites, OdysseyMaterial::name);
        return newState;
    }

    public boolean isFavourite(final OdysseyMaterial odysseyMaterial) {
        return this.favourites.contains(odysseyMaterial);
    }

    public Set<Commander> getCommanders() {
        return this.commanders;
    }

    public Optional<Commander> getPreferredCommander() {
        final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
        if (!preferredCommander.isBlank()) {
            final String[] commanderFidVersion = preferredCommander.split(":");
            final String name = commanderFidVersion[0];
            final String version = (commanderFidVersion.length > 2) ? commanderFidVersion[2] : "LIVE";
            final String fid = (commanderFidVersion.length > 2) ? commanderFidVersion[1] : "0";
            if (this.commanders.stream().anyMatch(commander -> commander.getName().equals(name) && commander.getFid().equals(fid) && commander.getGameVersion().name().equals(version))) {
                return this.commanders.stream().filter(commander -> commander.getName().equals(name) && commander.getFid().equals(fid) && commander.getGameVersion().name().equals(version)).findFirst();
            }
        }
        final Iterator<Commander> commanderIterator = this.commanders.iterator();
        if (commanderIterator.hasNext()) {
            final Commander commander = commanderIterator.next();
            PreferencesService.setPreference(PreferenceConstants.COMMANDER, commander.getName() + ":" + commander.getFid() + ":" + commander.getGameVersion().name());
            return Optional.of(commander);
        }
        return Optional.empty();
    }

    public void addCommander(final String name, final String fid, final GameVersion gameVersion) {
        if (this.commanders.stream().noneMatch(commander -> commander.getName().equals(name) && commander.getFid().equals(fid) && commander.getGameVersion().equals(gameVersion))) {
            final Commander commander = new Commander(name, fid, gameVersion);
            final boolean existingName = this.commanders.stream().anyMatch(commander1 -> commander1.getName().equals(name) && commander1.getGameVersion().equals(gameVersion));
            this.commanders.add(commander);
            if (existingName) {
                this.commanders.stream()
                        .filter(commander1 -> commander1.getName().equals(name) && commander1.getGameVersion().equals(gameVersion))
                        .forEach(commander1 -> commander1.setDuplicateName(true));
            }
            final String preferredCommander = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
            if (preferredCommander.isBlank()) {
                PreferencesService.setPreference(PreferenceConstants.COMMANDER, name + ":" + fid + ":" + gameVersion.name());
            }
            EventService.publish(new CommanderAddedEvent(commander));
        }
    }

    public void resetCommanders() {
        this.commanders.clear();
    }

    public String getMarketPlaceToken() {
        return getPreferredCommander().map(commander -> PreferencesService.getPreference(PreferenceConstants.MARKETPLACE_TOKEN_PREFIX + commander.getFid(), "")).orElse("");
    }

    @SuppressWarnings("java:S2095")
    public boolean isLocked() {
        try {
            fileLock = new FileOutputStream(OsConstants.LOCK).getChannel().tryLock();
        } catch (final IOException exception) {
            log.error("error acquiring lock", exception);
            return true;
        }
        // null if the lock could not be acquired because another program holds an overlapping lock
        return (fileLock == null);
    }

    public void releaseLock() {
        try {
            fileLock.release();
        } catch (final IOException e) {
            log.error("error releasing lock", e);
        }
    }

}
