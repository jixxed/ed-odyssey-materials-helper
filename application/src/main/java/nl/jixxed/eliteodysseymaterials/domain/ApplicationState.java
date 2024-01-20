package nl.jixxed.eliteodysseymaterials.domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ships.Ship;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Fileheader.Fileheader;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.service.event.trade.EnlistWebSocketEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.*;

@Slf4j
public class ApplicationState {
    @SuppressWarnings("java:S1068")
    private static PreferencesService preferencesService;//defined so app folder gets created for lockfile
    @Getter
    private Expansion expansion = Expansion.HORIZONS;
    @Getter
    @Setter
    private Fileheader fileheader;
    private static FileLock fileLock;
    private static ApplicationState applicationState;
    private final Set<Commander> commanders = new HashSet<>();
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
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
    @Setter
    private static GameVersion gameVersion = GameVersion.UNKNOWN;
    private int flags = 0;
    private int flags2 = 0;

    @Setter
    @Getter
    private Ship ship;

    @Setter
    @Getter
    private Optional<ShipConfig> shipConfig = Optional.empty();
    @Getter
    @Setter
    private int systemPips = 8;
    @Getter
    @Setter
    private int enginePips = 8;
    @Getter
    @Setter
    private int weaponPips = 8;
    @Getter
    @Setter
    private boolean liveStats = false;

    @Getter
    private final BooleanProperty fcMaterials = new SimpleBooleanProperty(false);
    private ApplicationState() {

        this.eventListeners.add(EventService.addListener(this, EnlistWebSocketEvent.class, event -> getPreferredCommander().ifPresent(commander -> PreferencesService.setPreference(PreferenceConstants.MARKETPLACE_TOKEN_PREFIX + commander.getFid(), event.getEnlistMessage().getTrace().getToken()))));
        this.eventListeners.add(EventService.addListener(this, LoadGameEvent.class, event -> {
            this.gameMode = event.getGameMode();
            this.expansion = event.getExpansion();
        }));
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
            final File configDir = new File(OsConstants.CONFIG_DIRECTORY);
            configDir.mkdirs();
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
//Flags:
//Bit   Value       Hex         Meaning
//0     1           0000 0001   Docked, (on a landing pad)
//1     2           0000 0002   Landed, (on planet surface)
//2     4           0000 0004   Landing Gear Down
//3     8           0000 0008   Shields Up
//4     16          0000 0010   Supercruise
//5     32          0000 0020   FlightAssist Off
//6     64          0000 0040   Hardpoints Deployed
//7     128         0000 0080   In Wing
//8     256         0000 0100   LightsOn
//9     512         0000 0200   Cargo Scoop Deployed
//10    1024        0000 0400   Silent Running,
//11    2048        0000 0800   Scooping Fuel
//12    4096        0000 1000   Srv Handbrake
//13    8192        0000 2000   Srv using Turret view
//14    16384       0000 4000   Srv Turret retracted (close to ship)
//15    32768       0000 8000   Srv DriveAssist
//16    65536       0001 0000   Fsd MassLocked
//17    131072      0002 0000   Fsd Charging
//18    262144      0004 0000   Fsd Cooldown
//19    524288      0008 0000   Low Fuel ( < 25% )
//20    1048576     0010 0000   Over Heating ( > 100% )
//21    2097152     0020 0000   Has Lat Long
//22    4194304     0040 0000   IsInDanger
//23    8388608     0080 0000   Being Interdicted
//24    16777216    0100 0000   In MainShip
//25    33554432    0200 0000   In Fighter
//26    67108864    0400 0000   In SRV
//27    134217728   0800 0000   Hud in Analysis mode
//28    268435456   1000 0000   Night Vision
//29    536870912   2000 0000   Altitude from Average radius
//30    1073741824  4000 0000   fsdJump
//31    2147483648  8000 0000   srvHighBeam

//Flags2 bits:
//Bit   value   hex         meaning
//0     1       0001        OnFoot
//1     2       0002        InTaxi (or dropship/shuttle)
//2     4       0004        InMulticrew (ie in someone else’s ship)
//3     8       0008        OnFootInStation
//4     16      0010        OnFootOnPlanet
//5     32      0020        AimDownSight
//6     64      0040        LowOxygen
//7     128     0080        LowHealth
//8     256     0100        Cold
//9     512     0200        Hot
//10    1024    0400        VeryCold
//11    2048    0800        VeryHot
//12    4096    1000        Glide Mode
//13    8192    2000        OnFootInHangar
//14    16384   4000        OnFootSocialSpace
//15    32768   8000        OnFootExterior
//16    65536   0001 0001   BreathableAtmosphere
//17    131072  0002 0000   Telepresence Multicrew
//18    262144  0004 0000   Physical Multicrew
//19    524288  0008 0000   Fsd hyperdrive charging

    public void updateWithFlags(final int flags, final int flags2) {
        this.flags = flags;
        this.flags2 = flags2;
    }

    public boolean playerInShip(){
        return (this.flags & 16777216) > 0;
    }
    public boolean playerInSrv(){
        return (this.flags & 67108864) > 0;
    }
    public boolean playerOnFoot(){
        return (this.flags2 & 1) > 0;
    }

}
