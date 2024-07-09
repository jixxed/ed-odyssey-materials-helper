package nl.jixxed.eliteodysseymaterials.templates.horizons.hgefinder;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.HgeStarSystem;
import nl.jixxed.eliteodysseymaterials.domain.StarSystem;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.Formatters;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.NotificationService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class CopyableSystemPopoverLabel extends FlowPane implements Template {

    private Label systemName;
    private Label distance;
    private Label percentage;
    private HgeStarSystem starSystem;
    private DestroyableResizableImageView copyIcon;
    private final List<EventListener<?>> eventListeners = new ArrayList<>();
    private HorizonsMaterialType materialType;

    public CopyableSystemPopoverLabel() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("copyable-system");
        this.setPrefWrapLength(500);
        this.systemName = LabelBuilder.builder()
                .withStyleClass("copyable-system-popover-location")
                .withNonLocalizedText("")
                .build();

        this.distance = LabelBuilder.builder()
                .withStyleClass("copyable-system-popover-distance")
                .withNonLocalizedText("")
                .build();
        this.percentage = LabelBuilder.builder()
                .withStyleClass("copyable-system-popover-percentage")
                .withNonLocalizedText("")
                .build();

        this.copyIcon = ResizableImageViewBuilder.builder()
                .withStyleClass("copyable-system-popover-copy-icon")
                .withImage("/images/other/copy.png")
                .build();

        this.setOnMouseClicked(event -> {
            copyLocationToClipboard();
            NotificationService.showInformation(NotificationType.COPY, "Clipboard", "System name copied.");
        });
        this.getChildren().addAll(this.systemName, new StackPane(this.copyIcon), this.distance, this.percentage);
        this.setVisible(false);
        addInfoPopOver(this);
    }

    @Override
    public void initEventHandling() {
        this.eventListeners.add(EventService.addListener(this, LocationChangedEvent.class, locationEvent -> {
            update();
        }));
    }

    public void setStarSystem(HgeStarSystem starSystem, HorizonsMaterialType materialType) {
        this.setVisible(true);
        this.starSystem = starSystem;
        this.materialType = materialType;
        this.systemName.setText(starSystem.starSystem().getName());
        final Double dropChance = calculatePercentage(materialType);
        if (dropChance.equals(maxPercentage(materialType))) {
            if (!this.systemName.getStyleClass().contains("copyable-system-popover-location-max")) {
                this.systemName.getStyleClass().add("copyable-system-popover-location-max");
            }
        } else {
            this.systemName.getStyleClass().remove("copyable-system-popover-location-max");
        }
        this.percentage.setText(Formatters.NUMBER_FORMAT_0.format(dropChance) + "%");
        update();
    }

    private Double maxPercentage(HorizonsMaterialType materialType) {
        if (
                HorizonsMaterialType.THERMIC.equals(materialType)
                        || HorizonsMaterialType.HEAT.equals(materialType)
                        || HorizonsMaterialType.CAPACITORS.equals(materialType)
                        || HorizonsMaterialType.ALLOYS.equals(materialType)
        ) {
            return 50D;
        }
        return 100D;
    }

    private Double calculatePercentage(HorizonsMaterialType materialType) {
        AtomicReference<Double> count = new AtomicReference<>(0D);
        AtomicReference<Double> total = new AtomicReference<>(0D);
        final Allegiance allegiance = this.starSystem.starSystem().getAllegiance();
        if (Allegiance.FEDERATION.equals(allegiance)) {
            total.updateAndGet(v -> v + 1);
            if (materialType.equals(HorizonsMaterialType.COMPOSITE)) {
                count.updateAndGet(v -> v + 1);
            }
        } else if (Allegiance.EMPIRE.equals(allegiance)) {
            total.updateAndGet(v -> v + 1);
            if (materialType.equals(HorizonsMaterialType.SHIELDING)) {
                count.updateAndGet(v -> v + 1);
            }
        }
        this.starSystem.faction().forEach(faction -> {
            faction.getActiveStates().forEach(state -> {
                switch (state) {
                    case OUTBREAK:
                        total.updateAndGet(v -> v + 1);
                        if (materialType.equals(HorizonsMaterialType.CHEMICAL)) {
                            count.updateAndGet(v -> v + 1);
                        }
                        break;
                    case WAR, CIVILWAR:
                        total.updateAndGet(v -> v + 1);
                        if (materialType.equals(HorizonsMaterialType.CAPACITORS) || materialType.equals(HorizonsMaterialType.THERMIC)) {
                            count.updateAndGet(v -> v + 0.5);
                        }
                        break;
                    case BOOM, EXPANSION:
                        total.updateAndGet(v -> v + 1);
                        if (materialType.equals(HorizonsMaterialType.HEAT) || materialType.equals(HorizonsMaterialType.ALLOYS)) {
                            count.updateAndGet(v -> v + 0.5);
                        }
                        break;
                    case CIVILUNREST:
                        total.updateAndGet(v -> v + 1);
                        if (materialType.equals(HorizonsMaterialType.MECHANICAL_COMPONENTS)) {
                            count.updateAndGet(v -> v + 1);
                        }
                        break;
                    default:
                        break;
                }
            });
        });
        if (total.get().equals(0D)) {
            return 0D;
        }
        final double dropChance = count.get() / total.get();
        return dropChance * 100;
    }

    private void update() {
        try {
            if (this.starSystem != null) {
                this.distance.setText("(" + Formatters.NUMBER_FORMAT_2.format(getDistance()) + "Ly)");
            }
        } catch (final IllegalArgumentException ex) {
            log.error("Error while updating distance", ex);
        }
    }

    public Double getDistance() {
        final StarSystem currentLocation = LocationService.getCurrentLocation().getStarSystem();
        return Math.sqrt(Math.pow(currentLocation.getX() - starSystem.starSystem().getX(), 2) + Math.pow(currentLocation.getY() - starSystem.starSystem().getY(), 2) + Math.pow(currentLocation.getZ() - starSystem.starSystem().getZ(), 2));
    }

    private void copyLocationToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(this.starSystem.starSystem().getName());
        clipboard.setContent(content);
    }

    public void addInfoPopOver(final Node hoverableNode) {
        hoverableNode.setOnMouseEntered(mouseEvent -> {
            final Node contentNode = createSystemPopOverContent();
            contentNode.getStyleClass().add("material-popover");
            final PopOver popOver = new PopOver(contentNode);
            popOver.setDetachable(false);
            popOver.setHeaderAlwaysVisible(false);
            popOver.arrowSizeProperty().set(0);
            popOver.arrowIndentProperty().set(0);
            popOver.cornerRadiusProperty().set(0);
            final Rectangle2D currentScreen = Screen.getScreensForRectangle(mouseEvent.getScreenX(), mouseEvent.getScreenY(), 1, 1).get(0).getBounds();
            final double mouseXOnScreen = mouseEvent.getScreenX() - currentScreen.getMinX();
            final double mouseYOnScreen = mouseEvent.getScreenY() - currentScreen.getMinY();
            if (mouseXOnScreen < currentScreen.getWidth() / 2 && mouseYOnScreen < currentScreen.getHeight() / 2) {
                popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
            } else if (mouseXOnScreen < currentScreen.getWidth() / 2 && mouseYOnScreen > currentScreen.getHeight() / 2) {
                popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_BOTTOM);
            } else if (mouseXOnScreen > currentScreen.getWidth() / 2 && mouseYOnScreen < currentScreen.getHeight() / 2) {
                popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
            } else {
                popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_BOTTOM);
            }
            final Timeline timelineShow = new Timeline();
            timelineShow.getKeyFrames().add(new KeyFrame(Duration.millis(500)));
            timelineShow.setOnFinished(finishEvent -> {
                if (hoverableNode.isHover() || (contentNode.isHover())) {
                    if (popOver.getContentNode() != null) {
                        popOver.show(hoverableNode);
                    }
                }
            });
            timelineShow.play();
            final Timeline timelineHide = new Timeline();
            timelineHide.getKeyFrames().add(new KeyFrame(Duration.millis(100)));
            timelineHide.setOnFinished(finishEvent -> {
                if (hoverableNode.isHover() || contentNode.isHover()) {
                    timelineHide.play();
                } else {
                    popOver.hide(Duration.ZERO);
                    if (popOver.getContentNode() != null) {
                        popOver.setContentNode(null);
                    }
                    timelineHide.stop();
                }
            });
            hoverableNode.setOnMouseExited(mouseEvent2 -> timelineHide.play());
        });
    }

    private Node createSystemPopOverContent() {
        Label systemLabel = LabelBuilder.builder().withStyleClasses("copyable-system-popover-label").withText(LocaleService.getStringBinding("hge.card.system")).build();
        Label allegianceLabel = LabelBuilder.builder().withStyleClasses("copyable-system-popover-label").withText(LocaleService.getStringBinding("hge.card.allegiance")).build();
        Label populationLabel = LabelBuilder.builder().withStyleClasses("copyable-system-popover-label").withText(LocaleService.getStringBinding("hge.card.population")).build();
        Label economyLabel = LabelBuilder.builder().withStyleClasses("copyable-system-popover-label").withText(LocaleService.getStringBinding("hge.card.economy")).build();
        Label statesLabel = LabelBuilder.builder().withStyleClasses("copyable-system-popover-label").withText(LocaleService.getStringBinding("hge.card.states")).build();
        final DestroyableLabel populationLabel1 = LabelBuilder.builder().withStyleClasses("copyable-system-popover-value", "copyable-system-popover-allegiance").withNonLocalizedText(formatPopulation(starSystem.starSystem().getPopulation().doubleValue())).build();
        final String primaryEconomy = LocaleService.getLocalizedStringForCurrentLocale(starSystem.starSystem().getPrimaryEconomy().getLocalizationKey());
        final String secondaryEconomy = Economy.UNKNOWN.equals(starSystem.starSystem().getSecondaryEconomy()) || Economy.NONE.equals(starSystem.starSystem().getSecondaryEconomy()) ? null : LocaleService.getLocalizedStringForCurrentLocale(starSystem.starSystem().getSecondaryEconomy().getLocalizationKey());
        final DestroyableLabel economyLabel1 = LabelBuilder.builder().withStyleClasses("copyable-system-popover-value", "copyable-system-popover-allegiance").withNonLocalizedText(primaryEconomy + (secondaryEconomy != null ? ", " + secondaryEconomy : "")).build();
        HBox populationLine = BoxBuilder.builder().withNodes(populationLabel, populationLabel1).buildHBox();
        HBox economyLine = BoxBuilder.builder().withNodes(economyLabel, economyLabel1).buildHBox();
        final DestroyableResizableImageView allegianceImage = ResizableImageViewBuilder.builder().withStyleClass("copyable-system-popover-allegiance-image").withImage("/images/allegiance/" + starSystem.starSystem().getAllegiance().name().toLowerCase() + ".png").build();
        CopyableSystemLabel systemLabel1 = new CopyableSystemLabel();
        systemLabel1.getStyleClass().add("copyable-system-popover-system");
        systemLabel1.setStarSystem(starSystem.starSystem());
        HBox systemLine = BoxBuilder.builder().withNodes(systemLabel, systemLabel1).buildHBox();
        final DestroyableLabel allegianceLabel1 = LabelBuilder.builder().withStyleClasses("copyable-system-popover-value", "copyable-system-popover-allegiance").withText(LocaleService.getStringBinding(starSystem.starSystem().getAllegiance().getLocalizationKey())).build();
        if ((Allegiance.FEDERATION.equals(starSystem.starSystem().getAllegiance()) && HorizonsMaterialType.COMPOSITE.equals(materialType))
                || (Allegiance.EMPIRE.equals(starSystem.starSystem().getAllegiance()) && HorizonsMaterialType.SHIELDING.equals(materialType))) {
            allegianceLabel1.getStyleClass().add("copyable-system-popover-allegiance-highlight");
        }
        HBox allegianceLine = BoxBuilder.builder().withNodes(allegianceLabel, allegianceLabel1).buildHBox();
        VBox states = BoxBuilder.builder().withNodes(statesLabel).buildVBox();
        HBox categoryLine1 = BoxBuilder.builder().buildHBox();
        HBox categoryLine2 = BoxBuilder.builder().buildHBox();
        VBox categories = BoxBuilder.builder().withNodes(categoryLine1, categoryLine2).buildVBox();
        HBox statesCategories = BoxBuilder.builder().withNodes(states, new GrowingRegion(), categories).buildHBox();
        final VBox systemAllegiance = BoxBuilder.builder().withNodes(BoxBuilder.builder().withNodes(systemLine, allegianceLine, populationLine, economyLine).buildVBox()).buildVBox();
        final HBox systemAllegianceImage = BoxBuilder.builder().withNodes(systemAllegiance, new GrowingRegion(), allegianceImage).buildHBox();
        final VBox vBox = BoxBuilder.builder().withNodes(systemAllegianceImage, statesCategories).withStyleClass("material-popover-content").buildVBox();

        Map<State, Integer> stateCounts = new HashMap<>();
        this.starSystem.faction().forEach(faction -> {
            faction.getActiveStates().forEach(state -> {
                if (List.of(State.OUTBREAK, State.WAR, State.CIVILWAR, State.BOOM, State.EXPANSION, State.CIVILUNREST).contains(state)) {
                    stateCounts.put(state, stateCounts.getOrDefault(state, 0) + 1);
                }
            });
        });
        stateCounts.forEach((state, count) -> {
            final DestroyableLabel stateLabel = LabelBuilder.builder().withNonLocalizedText(LocaleService.getLocalizedStringForCurrentLocale(state.getLocalizationKey()) + ((count > 1) ? " " + count + "x" : "")).build();
            if (state == State.OUTBREAK && materialType.equals(HorizonsMaterialType.CHEMICAL)
                    || (state == State.WAR || state == State.CIVILWAR) && (materialType.equals(HorizonsMaterialType.CAPACITORS) || materialType.equals(HorizonsMaterialType.THERMIC))
                    || (state == State.BOOM || state == State.EXPANSION) && (materialType.equals(HorizonsMaterialType.HEAT) || materialType.equals(HorizonsMaterialType.ALLOYS))
                    || state == State.CIVILUNREST && materialType.equals(HorizonsMaterialType.MECHANICAL_COMPONENTS)) {
                stateLabel.getStyleClass().add("copyable-system-popover-state-highlight");

            }
            states.getChildren().add(stateLabel);
        });
        List
                .of(HorizonsMaterialType.CHEMICAL, HorizonsMaterialType.THERMIC, HorizonsMaterialType.HEAT, HorizonsMaterialType.MECHANICAL_COMPONENTS)
                .forEach(materialType -> {
                    categoryLine1.getChildren().add(createMaterialTypePercentage(materialType));
                });
        List
                .of(HorizonsMaterialType.CAPACITORS, HorizonsMaterialType.SHIELDING, HorizonsMaterialType.COMPOSITE, HorizonsMaterialType.ALLOYS)
                .forEach(materialType -> {
                    categoryLine2.getChildren().add(createMaterialTypePercentage(materialType));
                });

        return vBox;
    }

    private VBox createMaterialTypePercentage(HorizonsMaterialType materialType) {
        final Double percentage1 = calculatePercentage(materialType);
        final DestroyableResizableImageView categoryImage = ResizableImageViewBuilder.builder().withStyleClass("copyable-system-popover-group-image").withImage("/images/material/categories/" + materialType.name().toLowerCase() + ".png").build();
        Label materialTypeLabel = LabelBuilder.builder().withStyleClass("copyable-system-popover-group-texts").withNonLocalizedText(LocaleService.getLocalizedStringForCurrentLocale(materialType.getLocalizationKey())).build();
        Label percentageLabel = LabelBuilder.builder().withStyleClass("copyable-system-popover-group-texts").withNonLocalizedText(Formatters.NUMBER_FORMAT_0.format(percentage1) + "%").build();
        if (percentage1.equals(0D)) {
            categoryImage.getStyleClass().add("copyable-system-popover-group-image-zero");
            materialTypeLabel.getStyleClass().add("copyable-system-popover-group-texts-zero");
            percentageLabel.getStyleClass().add("copyable-system-popover-group-texts-zero");
        }

        return BoxBuilder.builder().withStyleClass("copyable-system-popover-group-box").withNodes(BoxBuilder.builder().withNodes(new GrowingRegion(), categoryImage, new GrowingRegion()).buildHBox(), materialTypeLabel, percentageLabel).buildVBox();

    }

    static String formatPopulation(double size) {
        if (size < 1_000_000) {
            return Formatters.NUMBER_FORMAT_0.format(size);
        }
        String finalQ = "";
        for (String q : new String[]{"K", "population.million", "population.billion", "population.trillion"}) {
            if (size < 1000) break;
            finalQ = q;
            size /= 1000;
        }
        return LocaleService.getLocalizedStringForCurrentLocale(finalQ, Formatters.NUMBER_FORMAT_1.format(size));
    }
}
