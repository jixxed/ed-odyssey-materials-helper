package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import jfxtras.styles.jmetro.JMetroStyleClass;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.constants.AppConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PreferenceConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Commander;
import nl.jixxed.eliteodysseymaterials.enums.FontSize;
import nl.jixxed.eliteodysseymaterials.enums.GameVersion;
import nl.jixxed.eliteodysseymaterials.helper.POIHelper;
import nl.jixxed.eliteodysseymaterials.service.CAPIService;
import nl.jixxed.eliteodysseymaterials.service.EDDNService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PreferencesService;
import nl.jixxed.eliteodysseymaterials.service.event.*;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class BottomBar extends HBox {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final List<EventListener<?>> eventListeners = new ArrayList<>();

    private String system = "";
    private String body = "";
    private String station = "";

    private Label gameModeLabel;
    private Label apiLabel;
    private Label watchedFileLabel;
    private Label eddnQueueLabel;
    private Label login;
    private Label commanderLabel;
    private Label locationLabel;
    private Region region;
    private ComboBox<Commander> commanderSelect;
    private Double latitude;
    private Double longitude;
    private Separator apiLabelSeparator;
    private AtomicBoolean eddnTransmitting = new AtomicBoolean(false);
    private ScheduledExecutorService executorService;

    BottomBar() {
        executorService = new ScheduledThreadPoolExecutor(1);
        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        this.getStyleClass().add("bottombar");
        this.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        this.region = new Region();
        HBox.setHgrow(this.region, Priority.ALWAYS);
        this.locationLabel = LabelBuilder.builder().build();
        this.apiLabel = LabelBuilder.builder().build();
        this.eddnQueueLabel = LabelBuilder.builder().build();
        this.gameModeLabel = LabelBuilder.builder().build();
        this.commanderLabel = LabelBuilder.builder().withText(LocaleService.getStringBinding("tab.settings.commander")).build();
        this.login = LabelBuilder.builder().withText(LocaleService.getStringBinding("statusbar.login")).build();
        this.commanderSelect = ComboBoxBuilder.builder(Commander.class)
                .withStyleClass("bottombar-dropdown")
                .withItemsProperty(FXCollections.observableArrayList(APPLICATION_STATE.getCommanders()))
                .withValueChangeListener((obs, oldValue, newValue) -> Platform.runLater(() -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.COMMANDER, newValue.getName() + ":" + newValue.getFid() + ":" + newValue.getGameVersion().name());
                    }
                    if (oldValue != null && newValue != null) {
                        EventService.publish(new CommanderSelectedEvent(newValue));
                    }
                }))
                .build();
        this.commanderSelect.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");

        final File watchedFolder = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.DEFAULT_WATCHED_FOLDER));
        this.watchedFileLabel = LabelBuilder.builder().withText(LocaleService.getStringBinding("statusbar.watching.none", watchedFolder.getAbsolutePath())).build();
        this.apiLabelSeparator = new Separator(Orientation.VERTICAL);
        this.apiLabelSeparator.visibleProperty().bind(CAPIService.getInstance().getActive().or(ApplicationState.getInstance().getFcMaterials()));
        this.apiLabel.visibleProperty().bind(CAPIService.getInstance().getActive().or(ApplicationState.getInstance().getFcMaterials()));
        this.getChildren().addAll(this.watchedFileLabel, new Separator(Orientation.VERTICAL), this.gameModeLabel, this.apiLabelSeparator, this.apiLabel, this.login, this.eddnQueueLabel, this.region, this.locationLabel, new Separator(Orientation.VERTICAL), this.commanderLabel, this.commanderSelect);

        executorService.scheduleAtFixedRate(() -> {
            if(eddnTransmitting.get()){
                updateEDDNLabel();
            }
        },0,25, TimeUnit.MILLISECONDS);
    }

    private void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, 0, WatchedFolderChangedEvent.class, this::resetAfterWatchedFolderChanged));
        this.eventListeners.add(EventService.addListener(this, LocationChangedEvent.class, this::updateLocationLabel));
        this.eventListeners.add(EventService.addListener(this, JournalLineProcessedEvent.class, this::updateWatchedFileLabel));
        this.eventListeners.add(EventService.addListener(this, EngineerEvent.class, event -> hideLoginRequest()));
        this.eventListeners.add(EventService.addListener(this, CommanderAddedEvent.class, this::handleAddedCommander));
        this.eventListeners.add(EventService.addListener(this, 0, CommanderAllListedEvent.class, event -> afterAllCommandersListed()));
        this.eventListeners.add(EventService.addListener(this, 0, CommanderResetEvent.class, event -> this.commanderSelect.getItems().clear()));
        this.eventListeners.add(EventService.addListener(this, AfterFontSizeSetEvent.class, fontSizeEvent -> this.commanderSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px")));
        this.eventListeners.add(EventService.addListener(this, LoadGameEvent.class, this::handleLoadGame));
        this.eventListeners.add(EventService.addListener(this, JournalInitEvent.class, event -> updateApiLabel()));
        this.eventListeners.add(EventService.addListener(this, CapiFleetCarrierEvent.class, event -> updateApiLabel()));
        this.eventListeners.add(EventService.addListener(this, EDDNQueueEvent.class, event -> eddnTransmitting.set(true)));
        this.eventListeners.add(EventService.addListener(this, TerminateApplicationEvent.class, event -> executorService.shutdown()));
    }

    private void updateEDDNLabel() {
        Platform.runLater(()-> {
            final int queueSize = EDDNService.queueSize();
            if (queueSize > 0) {
                this.eddnQueueLabel.setText("EDDN Transmission queue: " + queueSize);
            } else {
                this.eddnQueueLabel.setText("");
                eddnTransmitting.set(false);
            }
        });
    }

    private void afterAllCommandersListed() {
        if (!this.commanderSelect.getItems().isEmpty() && this.commanderSelect.getSelectionModel().getSelectedIndex() == -1) {
            final Commander commander = this.commanderSelect.getItems().get(0);
            this.commanderSelect.getSelectionModel().select(commander);
            PreferencesService.setPreference(PreferenceConstants.COMMANDER, commander.getName() + ":" + commander.getFid() + ":" + commander.getGameVersion().name());
            EventService.publish(new CommanderSelectedEvent(commander));
        }
    }

    private void handleAddedCommander(final CommanderAddedEvent commanderAddedEvent) {
        this.login.setVisible(true);
        this.login.getStyleClass().remove("statusbar-login-hidden");
        this.commanderSelect.getItems().add(commanderAddedEvent.getCommander());
        final String preferredName = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
        if (preferredName.isBlank() || isPreferredCommander(commanderAddedEvent.getCommander(), preferredName)) {
            this.commanderSelect.getSelectionModel().select(commanderAddedEvent.getCommander());
            EventService.publish(new CommanderSelectedEvent(commanderAddedEvent.getCommander()));
        }
    }

    private static boolean isPreferredCommander(final Commander addedCommander, final String preferredName) {
        final String[] commanderFidVersion = preferredName.split(":");
        final String name = commanderFidVersion[0];
        final String version = (commanderFidVersion.length > 2) ? commanderFidVersion[2] : "LIVE";
        final String fid = (commanderFidVersion.length > 2) ? commanderFidVersion[1] : "0";
        return addedCommander.getName().equals(name) && addedCommander.getFid().equals(fid) && addedCommander.getGameVersion().name().equals(version);
    }

    private void handleLoadGame(final LoadGameEvent loadGameEvent) {
        this.gameModeLabel.textProperty().bind(LocaleService.getStringBinding(loadGameEvent.getExpansion().getLocalizationKey()));
    }

    private void hideLoginRequest() {
        this.login.setVisible(false);
        if (!this.login.getStyleClass().contains("statusbar-login-hidden")) {
            this.login.getStyleClass().add("statusbar-login-hidden");
        }
    }

    private void resetAfterWatchedFolderChanged(final WatchedFolderChangedEvent watchedFolderChangedEvent) {
        this.commanderSelect.getItems().clear();
        this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching.none", watchedFolderChangedEvent.getPath()));
    }

    private void updateWatchedFileLabel(final JournalLineProcessedEvent journalLineProcessedEvent) {
        if (journalLineProcessedEvent.getFile().getName().endsWith("log")) {
            Platform.runLater(() -> this.watchedFileLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.watching", journalLineProcessedEvent.getFile().getName())));
        }
    }

    private void updateApiLabel() {

        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final String pathname = OsConstants.CONFIG_DIRECTORY + OsConstants.OS_SLASH + commander.getFid().toLowerCase(Locale.ENGLISH) + (commander.getGameVersion().equals(GameVersion.LEGACY) ? ".legacy" : "");
            final File fleetCarrierFileDir = new File(pathname);
            fleetCarrierFileDir.mkdirs();
            final File fleetCarrierFile = new File(pathname + OsConstants.OS_SLASH + AppConstants.FLEETCARRIER_FILE);
            if (fleetCarrierFile.exists()) {
                if (CAPIService.getInstance().getActive().getValue().equals(false)) {
                    this.apiLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.api.stale"));

                } else {
                    final ZonedDateTime lastModified = ZonedDateTime.ofInstant(Instant.ofEpochMilli(fleetCarrierFile.lastModified()), ZoneId.systemDefault());
                    this.apiLabel.textProperty().bind(LocaleService.getStringBinding("statusbar.api.last.update", lastModified.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))));

                }
            }
        });
    }

    private void updateLocationLabel(final LocationChangedEvent locationChangedEvent) {
        this.system = locationChangedEvent.getCurrentStarSystem().getName();
        this.body = locationChangedEvent.getCurrentBody();
        this.station = locationChangedEvent.getCurrentSettlement();
        this.latitude = locationChangedEvent.getCurrentLatitude();
        this.longitude = locationChangedEvent.getCurrentLongitude();
        Platform.runLater(() -> this.locationLabel.setText(this.system +
                (this.body.isBlank() ? "" : " | " + this.body) +
                (this.station.isBlank() || this.station.equals(this.body) ? "" : " | " + POIHelper.map(this.station)) +
                (this.latitude != null && !this.latitude.equals(999.9) ? " (" + this.latitude + ", " + this.longitude + ")" : "")
        ));
    }
}
