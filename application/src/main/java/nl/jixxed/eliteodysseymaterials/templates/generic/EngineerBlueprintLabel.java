package nl.jixxed.eliteodysseymaterials.templates.generic;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.builder.TooltipBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.PinnedBlueprintService;
import nl.jixxed.eliteodysseymaterials.service.event.*;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EngineerBlueprintLabel extends HBox implements DestroyableComponent {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private final Engineer engineer;
    private final int rank;
    private final boolean exact;
    private final HorizonsBlueprint horizonsBlueprint;
    @Getter
    private Label label;
    private DestroyableResizableImageView image;
    private DestroyableResizableImageView pinned;
    private Integer currentEngineerRank = 0;
    @Setter
    private Consumer<JournalLineProcessedEvent> journalProcessedEventConsumer;
    private boolean dragFlag = false;

    public EngineerBlueprintLabel(final Engineer engineer) {
        this(engineer, false, 0);
    }

    public EngineerBlueprintLabel(final Engineer engineer, final boolean exact, final int rank) {
        this(engineer, null, exact, rank);
    }

    public EngineerBlueprintLabel(final Engineer engineer, final HorizonsBlueprint horizonsBlueprint, final boolean exact, final int rank) {
        this.horizonsBlueprint = horizonsBlueprint;
        this.engineer = engineer;
        this.rank = rank;
        this.exact = exact;
        this.journalProcessedEventConsumer = event -> {
            if (exact) {
                this.updateStyle(APPLICATION_STATE.isEngineerUnlockedExact(this.engineer), APPLICATION_STATE.getEngineerRank(this.engineer));
            } else {
                this.updateStyle(APPLICATION_STATE.isEngineerUnlocked(this.engineer), APPLICATION_STATE.getEngineerRank(this.engineer));
            }

        };
        initComponents();
        initEventHandling();

    }


    private void initEventHandling() {
        EventService.addListener(this, EngineerEvent.class, event -> {
            update();
        });
        EventService.addListener(this, EngineerPinEvent.class, event -> {
            if (this.engineer.equals(event.getEngineer()) && !this.horizonsBlueprint.equals(event.getHorizonsBlueprint())) {
                update();
            }
        });
        EventService.addListener(this, CommanderSelectedEvent.class, event -> {
            update();
        });
        EventService.addStaticListener(0, JournalInitEvent.class, event -> {
            update();
        });
    }

    private void update() {
        if (this.exact) {
            this.updateStyle(APPLICATION_STATE.isEngineerUnlockedExact(this.engineer), APPLICATION_STATE.getEngineerRank(this.engineer));
        } else {
            this.updateStyle(APPLICATION_STATE.isEngineerUnlocked(this.engineer), APPLICATION_STATE.getEngineerRank(this.engineer));
        }
        if (this.engineer.isHorizons()) {
            final Integer engineerRank = APPLICATION_STATE.getEngineerRank(this.engineer);
            if (!this.currentEngineerRank.equals(engineerRank)) {//only update if image has changed
                final String imageString = getEngineerRankImage(engineerRank);
                this.image.setImage(ImageService.getImage("/images/ships/engineers/ranks/" + imageString + ".png"));
                this.currentEngineerRank = engineerRank;
            }
            if (PinnedBlueprintService.isPinned(this.engineer, this.horizonsBlueprint)) {
                if (!this.getChildren().contains(this.pinned)) {
                    this.pinned.setImage(ImageService.getImage("/images/ships/engineers/pinned.png"));
                    this.getChildren().add(this.pinned);
                }
            } else {
                this.pinned.setImage(null);
                this.getChildren().remove(this.pinned);
            }
        }
    }


    private String getEngineerRankImage(final Integer engineerRank) {
        final String imageString;
        if ((engineerRank > 0)) {
            imageString = "rank_" + engineerRank;
        } else {
            if (APPLICATION_STATE.isEngineerInvited(this.engineer)) {
                imageString = "invited";
            } else {
                imageString = "lock";
            }
        }
        return imageString;
    }

    private void initComponents() {
        this.label = LabelBuilder.builder().withText(LocaleService.getStringBinding(this.engineer.getLocalizationKey())).build();
        this.getStyleClass().add("engineer-label");
        if (this.engineer.isHorizons()) {
            this.image = ResizableImageViewBuilder.builder().withStyleClasses("engineer-grade-image").build();
            this.getChildren().add(this.image);
            this.pinned = ResizableImageViewBuilder.builder().withStyleClasses("engineer-grade-image").build();
            this.getStyleClass().add("engineer-label-big");
        }
        this.getChildren().add(this.label);
        if (this.engineer.isHorizons() && this.horizonsBlueprint != null) {
            final int engineerMaxGrade = HorizonsBlueprintConstants.getEngineerMaxGrade(this.horizonsBlueprint, this.engineer);
            if (engineerMaxGrade > 0) {
                this.getChildren().add(LabelBuilder.builder().withNonLocalizedText("\u2191" + engineerMaxGrade).build());
            }
        }
        update();

        if (this.horizonsBlueprint instanceof HorizonsModuleBlueprint) {
            final EventHandler<MouseEvent> imageDragMouseEventHandler = event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    this.dragFlag = true;
                }
            };

            final EventHandler<MouseEvent> imageClickMouseEventHandler = event -> {

                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    return;
                }
                if (!this.dragFlag) {
                    if (event.getClickCount() == 1) {
                    } else if (event.getClickCount() > 1) {
                        processMultiClick();
                    }
                }
                this.dragFlag = false;

            };

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, imageClickMouseEventHandler);
            this.addEventHandler(MouseEvent.MOUSE_DRAGGED, imageDragMouseEventHandler);

        }
    }

    private void processMultiClick() {
        if (PinnedBlueprintService.isPinned(this.engineer, this.horizonsBlueprint)) {
            PinnedBlueprintService.unpinBlueprint(this.engineer);
//            PreferencesService.setPreference("blueprint.pinned." + this.engineer.name(), "");
            EventService.publish(new EngineerPinEvent(this.engineer, this.horizonsBlueprint, false));
        } else if (this.currentEngineerRank >= this.horizonsBlueprint.getHorizonsBlueprintGrade().getGrade()) {
            PinnedBlueprintService.pinBlueprint(this.engineer, this.horizonsBlueprint);
//            PreferencesService.setPreference("blueprint.pinned." + this.engineer.name(), this.horizonsBlueprint.getHorizonsBlueprintName().name() + ":" + this.horizonsBlueprint.getHorizonsBlueprintType().name() + ":" + this.horizonsBlueprint.getHorizonsBlueprintGrade().getGrade());
            EventService.publish(new EngineerPinEvent(this.engineer, this.horizonsBlueprint, true));
        }
        update();
    }


    private void updateStyle(final boolean unlocked, final Integer currentEngineerRank) {
        this.label.getStyleClass().removeAll("engineer-unlocked", "engineer-locked");
        final String styleClass;
        if (unlocked && currentEngineerRank >= this.rank) {
            styleClass = "engineer-unlocked";
            if(this.horizonsBlueprint instanceof HorizonsModuleBlueprint){
                final Tooltip tooltip = TooltipBuilder.builder().withText(LocaleService.getStringBinding("blueprint.engineer.pinnable.tooltip")).build();
                Tooltip.install(this, tooltip);
                this.getStyleClass().add("engineer-label-pinnable");
            }
        } else if (unlocked) {
            styleClass = "engineer-lowgrade";
        } else {
            styleClass = "engineer-locked";
        }
        this.label.getStyleClass().add(styleClass);
    }

    @Override
    public void destroyInternal() {
        EventService.removeListener(this);
    }

    @Override
    public Map<ObservableValue, List<ChangeListener>> getListenersMap() {
        return Collections.emptyMap();
    }
}
