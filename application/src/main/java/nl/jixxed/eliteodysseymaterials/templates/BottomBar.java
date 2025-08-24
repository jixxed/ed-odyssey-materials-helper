package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
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
import nl.jixxed.eliteodysseymaterials.service.*;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class BottomBar extends DestroyableHBox implements DestroyableEventTemplate {

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private String system = "";
    private String body = "";
    private String station = "";

    private DestroyableLabel gameModeLabel;
    private DestroyableLabel apiLabel;
    private DestroyableLabel watchedFileLabel;
    private DestroyableLabel eddnQueueLabel;
    private DestroyableLabel login;
    private DestroyableLabel commanderLabel;
    private DestroyableLabel locationLabel;
    private DestroyableComboBox<Commander> commanderSelect;
    private Double latitude;
    private Double longitude;
    private DestroyableSeparator apiLabelSeparator;
    private AtomicBoolean eddnTransmitting = new AtomicBoolean(false);
    private ScheduledExecutorService executorService;

    BottomBar() {
        executorService = new ScheduledThreadPoolExecutor(1);
        initComponents();
        initEventHandling();
    }

    public void initComponents() {
        this.getStyleClass().add("bottombar");
        this.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        this.locationLabel = LabelBuilder.builder()
                .build();
        this.apiLabel = LabelBuilder.builder()
                .build();
        updateApiLabel();
        this.eddnQueueLabel = LabelBuilder.builder()
                .build();
        this.gameModeLabel = LabelBuilder.builder()
                .build();
        this.commanderLabel = LabelBuilder.builder()
                .withText(LocaleService.getStringBinding("tab.settings.commander"))
                .build();
        this.login = LabelBuilder.builder()
                .withText(LocaleService.getStringBinding("statusbar.login"))
                .build();
        if (ApplicationState.getInstance().isEngineerProcessed()) {
            hideLoginRequest();
        }
        this.commanderSelect = ComboBoxBuilder.builder(Commander.class)
                .withStyleClass("bottombar-dropdown")
                .withItemsProperty(FXCollections.observableArrayList(APPLICATION_STATE.getCommanders().stream()
                        .filter(commander -> !PreferencesService.getPreference(PreferenceConstants.HIDE_LEGACY, Boolean.TRUE) || GameVersion.LIVE.equals(commander.getGameVersion()))
                        .sorted(Comparator.comparing(commander -> commander.getName() + commander.getGameVersion()))
                        .toList()))
                .withValueChangeListener((obs, oldValue, newValue) -> Platform.runLater(() -> {
                    if (newValue != null) {
                        PreferencesService.setPreference(PreferenceConstants.COMMANDER, newValue.getName() + ":" + newValue.getFid() + ":" + newValue.getGameVersion().name());
                    }
                    if (oldValue != null && newValue != null) {
                        EventService.publish(new CommanderSelectedEvent(newValue));
                    }
                }))
                .build();
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> this.commanderSelect.getSelectionModel().select(commander));
        this.commanderSelect.setFocusTraversable(false);
        this.commanderSelect.styleProperty().set("-fx-font-size: " + FontSize.valueOf(PreferencesService.getPreference(PreferenceConstants.TEXTSIZE, "NORMAL")).getSize() + "px");
        final File watchedFolder = new File(PreferencesService.getPreference(PreferenceConstants.JOURNAL_FOLDER, OsConstants.getDefaultWatchedFolder()));
        final String watchedFile = ApplicationState.getInstance().getWatchedFile();
        if (!watchedFile.isBlank()) {
            this.watchedFileLabel = LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("statusbar.watching", watchedFile))
                    .build();
        } else {
            this.watchedFileLabel = LabelBuilder.builder()
                    .withText(LocaleService.getStringBinding("statusbar.watching.none", watchedFolder.getAbsolutePath()))
                    .build();
        }
        this.apiLabelSeparator = new DestroyableSeparator(Orientation.VERTICAL);
        this.apiLabelSeparator.addBinding(this.apiLabelSeparator.visibleProperty(), CAPIService.getInstance().getActive().or(ApplicationState.getInstance().getFcMaterials()));
        this.apiLabel.addBinding(this.apiLabel.visibleProperty(), CAPIService.getInstance().getActive().or(ApplicationState.getInstance().getFcMaterials()));
        this.getNodes().addAll(this.watchedFileLabel, new DestroyableSeparator(Orientation.VERTICAL), this.gameModeLabel, this.apiLabelSeparator, this.apiLabel, this.login, this.eddnQueueLabel, new GrowingRegion(), this.locationLabel, new DestroyableSeparator(Orientation.VERTICAL), this.commanderLabel, this.commanderSelect);

        executorService.scheduleAtFixedRate(() -> {
            if (eddnTransmitting.get()) {
                updateEDDNLabel();
            }
        }, 0, 25, TimeUnit.MILLISECONDS);
        Platform.runLater(() -> {
            this.system = LocationService.getCurrentStarSystemName();
            this.body = LocationService.getCurrentLocation().getBody();
            this.station = LocationService.getCurrentLocation().getStation();
            this.latitude = LocationService.getCurrentLocation().getLatitude();
            this.longitude = LocationService.getCurrentLocation().getLongitude();
            this.locationLabel.setText(this.system +
                    (this.body.isBlank() ? "" : " | " + this.body) +
                    (this.station.isBlank() || this.station.equals(this.body) ? "" : " | " + POIHelper.map(this.station)) +
                    (this.latitude != null && !this.latitude.equals(999.9) ? " (" + this.latitude + ", " + this.longitude + ")" : ""));
        });
    }

    public void initEventHandling() {
        register(EventService.addListener(true, this, 0, WatchedFolderChangedEvent.class, this::resetAfterWatchedFolderChanged));
        register(EventService.addListener(true, this, LocationChangedEvent.class, this::updateLocationLabel));
        register(EventService.addListener(true, this, JournalLineProcessedEvent.class, this::updateWatchedFileLabel));
        register(EventService.addListener(true, this, EngineerEvent.class, event -> hideLoginRequest()));
        register(EventService.addListener(true, this, CommanderAddedEvent.class, this::handleAddedCommander));
        register(EventService.addListener(true, this, CommanderListResetEvent.class, _ -> updateCommanderList()));
        register(EventService.addListener(true, this, 0, CommanderAllListedEvent.class, event -> afterAllCommandersListed()));
        register(EventService.addListener(true, this, 0, CommanderResetEvent.class, event -> this.commanderSelect.getItems().clear()));
        register(EventService.addListener(true, this, AfterFontSizeSetEvent.class, fontSizeEvent -> this.commanderSelect.styleProperty().set("-fx-font-size: " + fontSizeEvent.getFontSize() + "px")));
        register(EventService.addListener(true, this, LoadGameEvent.class, this::handleLoadGame));
        register(EventService.addListener(true, this, JournalInitEvent.class, event -> {
            updateApiLabel();
            this.locationLabel.setText("");
        }));
        register(EventService.addListener(true, this, CapiFleetCarrierEvent.class, event -> updateApiLabel()));
        register(EventService.addListener(true, this, CapiSquadronEvent.class, event -> updateApiLabel()));
        register(EventService.addListener(true, this, EDDNQueueEvent.class, event -> eddnTransmitting.set(true)));
        register(EventService.addListener(true, this, TerminateApplicationEvent.class, event -> executorService.shutdown()));
    }

    private void updateCommanderList() {
        Commander selectedItem = this.commanderSelect.getSelectionModel().getSelectedItem();
        this.commanderSelect.getItems().clear();
        ApplicationState.getInstance().getCommanders().stream()
                .sorted(Comparator.comparing(commander -> commander.getName() + commander.getGameVersion()))
                .forEach(commander -> {
                    if (!PreferencesService.getPreference(PreferenceConstants.HIDE_LEGACY, Boolean.TRUE) || GameVersion.LIVE.equals(commander.getGameVersion())) {
                        this.commanderSelect.getItems().add(commander);
                    }
                });

        if (this.commanderSelect.getItems().contains(selectedItem)) {
            this.commanderSelect.getSelectionModel().select(selectedItem);
        } else if (!this.commanderSelect.getItems().isEmpty()) {
            this.commanderSelect.getSelectionModel().selectFirst();
            EventService.publish(new CommanderSelectedEvent(this.commanderSelect.getSelectionModel().getSelectedItem()));
        }
    }

    private void updateEDDNLabel() {
        Platform.runLater(() -> {
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
        if (!PreferencesService.getPreference(PreferenceConstants.HIDE_LEGACY, Boolean.TRUE) || GameVersion.LIVE.equals(commanderAddedEvent.getCommander().getGameVersion())) {
            this.login.setVisible(true);
            this.login.getStyleClass().remove("statusbar-login-hidden");
            this.commanderSelect.getItems().add(commanderAddedEvent.getCommander());
            final String preferredName = PreferencesService.getPreference(PreferenceConstants.COMMANDER, "");
            if (preferredName.isBlank() || isPreferredCommander(commanderAddedEvent.getCommander(), preferredName)) {
                this.commanderSelect.getSelectionModel().select(commanderAddedEvent.getCommander());
                EventService.publish(new CommanderSelectedEvent(commanderAddedEvent.getCommander()));
            }
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
        this.gameModeLabel.addBinding(this.gameModeLabel.textProperty(), LocaleService.getStringBinding(loadGameEvent.getExpansion().getLocalizationKey()));
    }

    private void hideLoginRequest() {
        this.login.setVisible(false);
        if (!this.login.getStyleClass().contains("statusbar-login-hidden")) {
            this.login.getStyleClass().add("statusbar-login-hidden");
        }
    }

    private void resetAfterWatchedFolderChanged(final WatchedFolderChangedEvent watchedFolderChangedEvent) {
        this.commanderSelect.getItems().clear();
        this.watchedFileLabel.addBinding(this.watchedFileLabel.textProperty(), LocaleService.getStringBinding("statusbar.watching.none", watchedFolderChangedEvent.getPath()));
    }

    private void updateWatchedFileLabel(final JournalLineProcessedEvent journalLineProcessedEvent) {
        if (journalLineProcessedEvent.getFile().getName().endsWith("log")) {
            Platform.runLater(() -> this.watchedFileLabel.addBinding(this.watchedFileLabel.textProperty(), LocaleService.getStringBinding("statusbar.watching", journalLineProcessedEvent.getFile().getName())));
        }
    }

    private void updateApiLabel() {

        APPLICATION_STATE.getPreferredCommander().ifPresent(commander -> {
            final String pathname = commander.getCommanderFolder();
            final File fleetCarrierFileDir = new File(pathname);
            fleetCarrierFileDir.mkdirs();
            final File fleetCarrierFile = new File(pathname + OsConstants.getOsSlash() + AppConstants.FLEETCARRIER_FILE);
            if (fleetCarrierFile.exists()) {
                if (CAPIService.getInstance().getActive().getValue().equals(false)) {
                    this.apiLabel.addBinding(this.apiLabel.textProperty(), LocaleService.getStringBinding("statusbar.api.stale"));

                } else {
                    final ZonedDateTime lastModified = ZonedDateTime.ofInstant(Instant.ofEpochMilli(fleetCarrierFile.lastModified()), ZoneId.systemDefault());
                    this.apiLabel.addBinding(this.apiLabel.textProperty(), LocaleService.getStringBinding("statusbar.api.last.update", lastModified.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))));

                }
            } else {
                this.apiLabel.addBinding(this.apiLabel.textProperty(), LocaleService.getStringBinding("blank"));
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

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        executorService.shutdownNow();
    }
}
