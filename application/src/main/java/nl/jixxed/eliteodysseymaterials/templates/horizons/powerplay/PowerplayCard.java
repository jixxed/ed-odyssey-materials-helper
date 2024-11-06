package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PowerplayCard extends VBox {
    @Getter
    private final Power power;


    protected DestroyableResizableImageView image;

    protected Label name;
    protected Label rankLabel;
    protected Label meritsLabel;
    private Label engineerLocation;
    private Label engineerDistance;
    private DestroyableResizableImageView copyIcon;
    protected FlowPane location;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private Label unlockablesTitle;
    private List<PowerplayUnlockableModule> unlockablesLabels;
    private Separator unlockablesSeparator;
    private Long merits;
    private Long rank;
    private HBox rankAndMeritsBox;

    public PowerplayCard(Power power) {
        this.power = power;
        initComponents();
        initEventHandling(power);
    }

    private void initEventHandling(final Power engineer) {
        this.eventListeners.add(EventService.addListener(true, this, LocationChangedEvent.class, locationChangedEvent -> {
            if (!Power.ALL.equals(this.power)) {
                this.engineerDistance.setText("(" + Formatters.NUMBER_FORMAT_2.format(
                        engineer.getDistance(
                                locationChangedEvent.getCurrentStarSystem().getX(),
                                locationChangedEvent.getCurrentStarSystem().getY(),
                                locationChangedEvent.getCurrentStarSystem().getZ()
                        )
                ) + "Ly)");
            }
        }));
        this.eventListeners.add(EventService.addListener(true, this, PowerplayEvent.class, powerplayEvent ->
                this.update(powerplayEvent.getPower(), powerplayEvent.getMerits(), powerplayEvent.getRank())));
    }

    private void update(Power power, Long merits, Long rank) {
        this.merits = merits;
        this.rank = rank;

        this.getChildren().remove(rankAndMeritsBox);
        if (this.power.equals(power)) {
            rankLabel.textProperty().bind(LocaleService.getStringBinding("tab.powerplay.rank", this.rank));
            meritsLabel.textProperty().bind(LocaleService.getStringBinding("tab.powerplay.merits", this.merits, Power.getMeritsRequiredForRank(this.rank + 1)));
            this.getChildren().add(3, rankAndMeritsBox);
        }
    }

    private void initComponents() {
        this.image = getEngineerImageView();
        this.name = getEngineerName();
        rankLabel = LabelBuilder.builder()
                .withStyleClass("power-current-rank")
                .withText(LocaleService.getStringBinding("tab.powerplay.rank", ApplicationState.getInstance().getPowerRank()))
                .build();
        meritsLabel = LabelBuilder.builder()
                .withStyleClass("power-current-merits")
                .withText(LocaleService.getStringBinding("tab.powerplay.merits", ApplicationState.getInstance().getPowerMerits(), Power.getMeritsRequiredForRank(ApplicationState.getInstance().getPowerRank() + 1)))
                .build();
        rankAndMeritsBox = BoxBuilder.builder().withNodes(rankLabel, new GrowingRegion(), meritsLabel).buildHBox();
        this.getChildren().addAll(
                this.image,
                this.name
        );
        if (!Power.ALL.equals(this.power)) {
            this.location = getEngineerLocation();
            this.getChildren().add(this.location);
        }
        final List<PowerplayPerk> powerplayPerks = this.power.getPerks().entrySet().stream()
                .map(entry -> new PowerplayPerk(this.power, entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(powerplayPerk -> LocaleService.getLocalizedStringForCurrentLocale(powerplayPerk.getPerk().getLocalizationTitleKey())))
                .toList();
        this.getChildren().addAll(powerplayPerks);
        if (!Power.ALL.equals(this.power)) {
            this.unlockablesTitle = getUnlockablesTitle();
            this.unlockablesLabels = getUnlockables();
            this.unlockablesSeparator = new Separator(Orientation.HORIZONTAL);
            this.getChildren().addAll(
                    this.unlockablesSeparator,
                    new GrowingRegion(),
                    this.unlockablesTitle
            );
            this.getChildren().addAll(this.unlockablesLabels);
        }
        this.getStyleClass().add("power-card");
        this.update(ApplicationState.getInstance().getPower(), ApplicationState.getInstance().getPowerMerits(), ApplicationState.getInstance().getPowerRank());
    }


    protected List<PowerplayUnlockableModule> getUnlockables() {
        return this.power.getUnlockables().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .filter(Predicate.not(entry ->
                        entry.getKey().getName().equals(HorizonsBlueprintName.PRISMATIC_SHIELD_GENERATOR)
                                && entry.getKey().getModuleSize().isLower(ModuleSize.SIZE_8)))
                .map(unlockable -> new PowerplayUnlockableModule(this.power, unlockable.getValue(), unlockable.getKey()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Label getUnlockablesTitle() {
        return LabelBuilder.builder()
                .withStyleClass("power-category")
                .withText(LocaleService.getStringBinding("tab.powerplay.unlockables"))
                .build();
    }


    private FlowPane getEngineerLocation() {
        this.engineerLocation = LabelBuilder.builder()
                .withStyleClass("power-location")
                .withNonLocalizedText(this.power.getStarSystem().getName())
                .build();
        String distance = "(" + Formatters.NUMBER_FORMAT_2.format(
                power.getDistance(
                        LocationService.getCurrentStarSystem().getX(),
                        LocationService.getCurrentStarSystem().getY(),
                        LocationService.getCurrentStarSystem().getZ()
                )
        ) + "Ly)";
        this.engineerDistance = LabelBuilder.builder()
                .withStyleClass("power-distance")
                .withNonLocalizedText(distance)
                .build();

        this.copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("power-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        return FlowPaneBuilder.builder().withStyleClass("power-location-line")
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard();
                    NotificationService.showInformation(NotificationType.COPY, "Clipboard", "System name copied.");
                })
                .withNodes(this.engineerLocation, new StackPane(this.copyIcon), this.engineerDistance)
                .build();

    }

    private void copyLocationToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(this.power.getStarSystem().getName());
        clipboard.setContent(content);
    }


    private Label getEngineerName() {
        return LabelBuilder.builder()
                .withStyleClass("power-name")
                .withText(LocaleService.getStringBinding(this.power.getLocalizationKey()))
                .build();
    }

    private DestroyableResizableImageView getEngineerImageView() {
        return ResizableImageViewBuilder.builder()
                .withStyleClass("power-image")
                .withPreserveRatio(true)
                .withImage(ImageService.getImage("/images/power/" + power.name().toLowerCase() + ".png"))
                .build();

    }
}
