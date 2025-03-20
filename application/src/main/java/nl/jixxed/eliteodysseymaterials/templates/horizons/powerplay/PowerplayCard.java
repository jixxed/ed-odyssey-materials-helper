package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import javafx.geometry.Orientation;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ModuleSize;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsBlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.NotificationType;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PowerplayCard extends DestroyableVBox implements DestroyableEventTemplate {
    @Getter
    private final Power power;


    protected DestroyableResizableImageView image;

    protected DestroyableLabel name;
    protected DestroyableLabel rankLabel;
    protected DestroyableLabel meritsLabel;
    private DestroyableLabel powerLocation;
    private DestroyableLabel powerDistance;
    protected DestroyableFlowPane location;

    private DestroyableLabel unlockablesTitle;
    private List<PowerplayUnlockableModule> unlockablesLabels;
    private DestroyableSeparator unlockablesSeparator;
    private Long merits;
    private Long rank;
    private DestroyableHBox rankAndMeritsBox;

    public PowerplayCard(Power power) {
        this.power = power;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.image = getEngineerImageView();
        this.name = getEngineerName();
        rankLabel = LabelBuilder.builder()
                .withStyleClass("power-current-rank")
                .withText("tab.powerplay.rank", ApplicationState.getInstance().getPowerRank())
                .build();
        meritsLabel = LabelBuilder.builder()
                .withStyleClass("power-current-merits")
                .withText("tab.powerplay.merits", ApplicationState.getInstance().getPowerMerits(), Power.getMeritsRequiredForRank(ApplicationState.getInstance().getPowerRank() + 1))
                .build();
        rankAndMeritsBox = BoxBuilder.builder()
                .withNodes(rankLabel, new GrowingRegion(), meritsLabel)
                .buildHBox();
        this.getNodes().addAll(
                this.image,
                this.name
        );
        if (!Power.ALL.equals(this.power)) {
            this.location = getPowerLocation();
            this.getNodes().add(this.location);
        }
        final List<PowerplayPerk> powerplayPerks = this.power.getPerks().entrySet().stream()
                .map(entry -> new PowerplayPerk(this.power, entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(powerplayPerk -> LocaleService.getLocalizedStringForCurrentLocale(powerplayPerk.getPerk().getLocalizationTitleKey())))
                .toList();
        this.getNodes().addAll(powerplayPerks);
        if (!Power.ALL.equals(this.power)) {
            this.unlockablesTitle = getUnlockablesTitle();
            this.unlockablesLabels = getUnlockables();
            this.unlockablesSeparator = new DestroyableSeparator(Orientation.HORIZONTAL);
            this.getNodes().addAll(
                    this.unlockablesSeparator,
                    new GrowingRegion(),
                    this.unlockablesTitle
            );
            this.getNodes().addAll(this.unlockablesLabels);
        }
        this.getStyleClass().add("power-card");
        this.update(ApplicationState.getInstance().getPower(), Optional.of(ApplicationState.getInstance().getPowerMerits()), Optional.of(ApplicationState.getInstance().getPowerRank()));
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, LocationChangedEvent.class, locationChangedEvent -> {
            if (!Power.ALL.equals(this.power)) {
                final Double powerDistanceValue = this.power.getDistance(
                        locationChangedEvent.getCurrentStarSystem().getX(),
                        locationChangedEvent.getCurrentStarSystem().getY(),
                        locationChangedEvent.getCurrentStarSystem().getZ()
                );
                this.powerDistance.addBinding(this.powerDistance.textProperty(), LocaleService.getStringBinding("tab.powerplay.distance", Formatters.NUMBER_FORMAT_2.format(powerDistanceValue)));
            }
        }));
        register(EventService.addListener(true, this, PowerplayEvent.class, powerplayEvent ->
                this.update(powerplayEvent.getPower(), powerplayEvent.getMerits(), powerplayEvent.getRank())));

    }

    private void update(Power power, Optional<Long> meritsOpt, Optional<Long> rankOpt) {
        meritsOpt.ifPresent(merits -> this.merits = merits);
        rankOpt.ifPresent(rank -> this.rank = rank);

        this.getNodes().remove(rankAndMeritsBox);
        if (this.power.equals(power)) {
            rankLabel.addBinding(rankLabel.textProperty(), LocaleService.getStringBinding("tab.powerplay.rank", this.rank));
            meritsLabel.addBinding(meritsLabel.textProperty(), LocaleService.getStringBinding("tab.powerplay.merits", this.merits, Power.getMeritsRequiredForRank(this.rank + 1)));
            this.getNodes().add(3, rankAndMeritsBox);
        }
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

    private DestroyableLabel getUnlockablesTitle() {
        return LabelBuilder.builder()
                .withStyleClass("power-category")
                .withText("tab.powerplay.unlockables")
                .build();
    }


    private DestroyableFlowPane getPowerLocation() {
        this.powerLocation = LabelBuilder.builder()
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
        this.powerDistance = LabelBuilder.builder()
                .withStyleClass("power-distance")
                .withText("tab.powerplay.distance", distance)
                .build();

        final DestroyableResizableImageView copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("power-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        final DestroyableStackPane copyIconStackPane = StackPaneBuilder.builder()
                .withNodes(copyIcon)
                .build();

        return FlowPaneBuilder.builder()
                .withStyleClass("power-location-line")
                .withOnMouseClicked(event -> {
                    copyLocationToClipboard();
                    NotificationService.showInformation(NotificationType.COPY, LocaleService.LocaleString.of("notification.clipboard.title"), LocaleService.LocaleString.of("notification.clipboard.system.copied.text"));
                })
                .withNodes(this.powerLocation, copyIconStackPane, this.powerDistance)
                .build();

    }

    private void copyLocationToClipboard() {
        ClipboardHelper.copyToClipboard(this.power.getStarSystem().getName());
    }


    private DestroyableLabel getEngineerName() {
        return LabelBuilder.builder()
                .withStyleClass("power-name")
                .withText(this.power.getLocalizationKey())
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
