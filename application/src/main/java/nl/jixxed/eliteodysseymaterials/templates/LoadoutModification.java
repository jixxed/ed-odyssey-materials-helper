package nl.jixxed.eliteodysseymaterials.templates;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.NonNull;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.ModificationChange;
import nl.jixxed.eliteodysseymaterials.domain.SelectedModification;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModificationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.Destroyable;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableResizableImageView;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class LoadoutModification extends VBox implements DestroyableTemplate {
    private static final String LOADOUT_MODIFICATION_STYLE_CLASS = "loadout-modification";
    private static final String LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS = "loadout-modification-image";
    private static final String LOADOUT_MODIFICATION_IMAGE_CONSUMED_STYLE_CLASS = "loadout-modification-image-consumed";
    private static final String LOADOUT_MODIFICATION_LABEL_STYLE_CLASS = "loadout-modification-label";
    private DestroyableResizableImageView imageView;
    private DestroyableLabel label;
    private Loadout loadout;
    private final int position;
    private PopOver popOver;
    private final List<Destroyable> destroyables = new ArrayList<>();
    private LoadoutItem loadoutItem;

    private boolean dragFlag = false;

    private ScheduledThreadPoolExecutor executor;

    private ScheduledFuture<?> scheduledFuture;

    LoadoutModification(final Loadout loadout, final Integer position, final LoadoutItem loadoutItem) {
        this.loadout = loadout;
        this.loadoutItem = loadoutItem;
        this.position = position;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.executor = new ScheduledThreadPoolExecutor(1);
        this.executor.setRemoveOnCancelPolicy(true);
        this.getStyleClass().add(LOADOUT_MODIFICATION_STYLE_CLASS);
        this.imageView = ResizableImageViewBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS)
                .withImage((this.loadout.getModifications()[this.position] != null) ? this.loadout.getModifications()[this.position].getImage() : "/images/modification/empty.png")
                .build();
        this.label = LabelBuilder.builder().withStyleClass(LOADOUT_MODIFICATION_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding((this.loadout.getModifications()[this.position] != null && this.loadout.getModifications()[this.position].getModification() != null) ? this.loadout.getModifications()[this.position].getModification().getLocalizationKey() : "loadout.modification.name.none")).build();
        this.destroyables.add(this.label);
        this.destroyables.add(this.imageView);

        final EventHandler<MouseEvent> imageDragMouseEventHandler = event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                this.dragFlag = true;
            }
        };

        final EventHandler<MouseEvent> imageClickMouseEventHandler = event -> {

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                clear();
                return;
            }
            if (!this.dragFlag) {
                if (event.getClickCount() == 1) {
                    this.scheduledFuture = this.executor.schedule(() -> {
                        Platform.runLater(() -> {
                            if (this.popOver != null && this.popOver.isShowing()) {
                                this.popOver.hide(Duration.ZERO);
                                this.popOver.setContentNode(null);
                                this.popOver = null;
                                return;
                            }
                            if (!event.getButton().equals(MouseButton.PRIMARY)) {
                                return;
                            }
                            this.popOver = new PopOver();
                            this.popOver.getStyleClass().add("loadout-modification-popover");
                            this.popOver.setContentNode(createModificationOptionsGrid());
                            this.popOver.setDetachable(false);
                            this.popOver.show(this, event.getScreenX(), event.getScreenY());
                        });
                    }, 300, TimeUnit.MILLISECONDS);
                } else if (event.getClickCount() > 1 && this.scheduledFuture != null && !this.scheduledFuture.isCancelled() && !this.scheduledFuture.isDone()) {
                    this.scheduledFuture.cancel(false);
                    setPresent();
                }

            }
            this.dragFlag = false;

        };

        this.imageView.addDestroyableEventHandler(MouseEvent.MOUSE_CLICKED, imageClickMouseEventHandler);
        this.imageView.addDestroyableEventHandler(MouseEvent.MOUSE_DRAGGED, imageDragMouseEventHandler);
        VBox.setVgrow(this.imageView, Priority.ALWAYS);
        this.getChildren().addAll(this.imageView, this.label);

    }

    private void setPresent() {
        if (this.loadout.getModifications()[this.position] != null) {
            this.loadout.getModifications()[this.position].setPresent(!this.loadout.getModifications()[this.position].isPresent());
            this.imageView.setImage(ImageService.getImage(this.loadout.getModifications()[this.position].getImage()));
            final ModificationChange modificationChange = new ModificationChange(this.loadout.getModifications()[this.position], this.loadout.getModifications()[this.position]);
            EventService.publish(new ModificationChangedEvent(this.loadoutItem, modificationChange));
        }
    }


    private Pane createModificationOptionsGrid() {
        final GridPane gridPane = new GridPane();
        final Pane pane = new Pane(gridPane);
        gridPane.getStyleClass().add("loadout-modification-grid");
        gridPane.hgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.71));
        gridPane.vgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.71));

        if (this.loadout.getEquipment() instanceof Weapon) {
            createGridPaneCell(getHeadshotDamageModification(), 0, 0, gridPane);
            createGridPaneCell(WeaponModification.MAGAZINE_SIZE, 1, 0, gridPane);
            createGridPaneCell(WeaponModification.STOWED_RELOADING, 2, 0, gridPane);
            createGridPaneCell(WeaponModification.SCOPE, 3, 0, gridPane);

            createGridPaneCell(WeaponModification.NOISE_SUPPRESSOR, 0, 1, gridPane);
            createGridPaneCell(WeaponModification.AUDIO_MASKING, 1, 1, gridPane);
            createGridPaneCell(WeaponModification.FASTER_HANDLING, 2, 1, gridPane);
            createGridPaneCell(getGreaterRangeModification(), 3, 1, gridPane);

            createGridPaneCell(WeaponModification.RELOAD_SPEED, 0, 2, gridPane);
            createGridPaneCell(WeaponModification.STABILITY, 1, 2, gridPane);
            createGridPaneCell(getHigherAccuracyModification(), 2, 2, gridPane);
            createCancelCell(3, 2, gridPane);
        } else if (this.loadout.getEquipment() instanceof Suit) {
            createGridPaneCell(SuitModification.ADDED_MELEE_DAMAGE, 0, 0, gridPane);
            createGridPaneCell(SuitModification.COMBAT_MOVEMENT_SPEED, 1, 0, gridPane);
            createGridPaneCell(SuitModification.DAMAGE_RESISTANCE, 2, 0, gridPane);
            createGridPaneCell(SuitModification.ENHANCED_TRACKING, 3, 0, gridPane);
            createGridPaneCell(SuitModification.QUIETER_FOOTSTEPS, 4, 0, gridPane);

            createGridPaneCell(SuitModification.EXTRA_AMMO_CAPACITY, 0, 1, gridPane);
            createGridPaneCell(SuitModification.EXTRA_BACKPACK_CAPACITY, 1, 1, gridPane);
            createGridPaneCell(SuitModification.IMPROVED_BATTERY_CAPACITY, 2, 1, gridPane);
            createGridPaneCell(SuitModification.FASTER_SHIELD_REGEN, 3, 1, gridPane);
            createGridPaneCell(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION, 4, 1, gridPane);

            createGridPaneCell(SuitModification.IMPROVED_JUMP_ASSIST, 0, 2, gridPane);
            createGridPaneCell(SuitModification.INCREASED_AIR_RESERVES, 1, 2, gridPane);
            createGridPaneCell(SuitModification.INCREASED_SPRINT_DURATION, 2, 2, gridPane);
            createGridPaneCell(SuitModification.NIGHT_VISION, 3, 2, gridPane);
            createCancelCell(4, 2, gridPane);

        }
        return pane;
    }

    private WeaponModification getHigherAccuracyModification() {
        final Weapon weapon = (Weapon) this.loadout.getEquipment();
        if (weapon.isKinetic()) {
            return WeaponModification.HIGHER_ACCURACY_KINETIC;
        } else if (weapon.isLaser()) {
            return WeaponModification.HIGHER_ACCURACY_LASER;
        } else {
            return WeaponModification.HIGHER_ACCURACY_PLASMA;
        }
    }

    private WeaponModification getGreaterRangeModification() {
        final Weapon weapon = (Weapon) this.loadout.getEquipment();
        if (weapon.isKinetic()) {
            return WeaponModification.GREATER_RANGE_KINETIC;
        } else if (weapon.isLaser()) {
            return WeaponModification.GREATER_RANGE_LASER;
        } else {
            return WeaponModification.GREATER_RANGE_PLASMA;
        }
    }

    private WeaponModification getHeadshotDamageModification() {
        final Weapon weapon = (Weapon) this.loadout.getEquipment();
        if (weapon.isKinetic()) {
            return WeaponModification.HEADSHOT_DAMAGE_KINETIC;
        } else if (weapon.isLaser()) {
            return WeaponModification.HEADSHOT_DAMAGE_LASER;
        } else {
            return WeaponModification.HEADSHOT_DAMAGE_PLASMA;
        }
    }

    private void createGridPaneCell(@NonNull final Modification modification, final int col, final int row, final GridPane gridPane) {
        final DestroyableResizableImageView modSelectImage = ResizableImageViewBuilder.builder().withStyleClass(LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS).withImage(modification.getImage(false)).build();
        this.destroyables.add(modSelectImage);
        if (Arrays.stream(this.loadout.getModifications()).map(SelectedModification::getModification).anyMatch(modification::equals)) {
            modSelectImage.getStyleClass().add(LOADOUT_MODIFICATION_IMAGE_CONSUMED_STYLE_CLASS);
        } else {
            modSelectImage.addDestroyableEventHandler(MouseEvent.MOUSE_CLICKED, getModificationSelectedEventHandler(modification));
        }
        final DestroyableLabel modLabel = LabelBuilder.builder().withStyleClass(LOADOUT_MODIFICATION_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding(modification.getLocalizationKey())).build();
        this.destroyables.add(modLabel);
        final VBox modVBox = BoxBuilder.builder().withStyleClass(LOADOUT_MODIFICATION_STYLE_CLASS).withNodes(modSelectImage, modLabel).buildVBox();
        gridPane.add(modVBox, col, row);
    }

    private void createCancelCell(final int col, final int row, final GridPane gridPane) {
        final DestroyableResizableImageView modSelectImage = ResizableImageViewBuilder.builder().withStyleClass(LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS).withImage("/images/modification/cancel.png").withOnMouseClicked(getClearEventHandler()).build();
        final DestroyableLabel modLabel = LabelBuilder.builder().withStyleClass(LOADOUT_MODIFICATION_LABEL_STYLE_CLASS).withText(LocaleService.getStringBinding("loadout.modification.clear")).build();
        this.destroyables.add(modSelectImage);
        this.destroyables.add(modLabel);
        final VBox modVBox = BoxBuilder.builder().withStyleClass(LOADOUT_MODIFICATION_STYLE_CLASS).withNodes(modSelectImage, modLabel).buildVBox();
        gridPane.add(modVBox, col, row);
    }

    private EventHandler<MouseEvent> getClearEventHandler() {
        return e -> {
            this.popOver.hide(Duration.ZERO);
            this.popOver.setContentNode(null);
            this.popOver = null;
            clear();
        };
    }

    private void clear() {
        this.imageView.setImage(ImageService.getImage("/images/modification/empty.png"));
        this.label.textProperty().bind(LocaleService.getStringBinding("loadout.modification.name.none"));
        final SelectedModification newModification = new SelectedModification(null, false);
        final ModificationChange modificationChange = new ModificationChange(this.loadout.getModifications()[this.position], newModification);
        this.loadout.getModifications()[this.position] = newModification;
        EventService.publish(new ModificationChangedEvent(this.loadoutItem, modificationChange));
    }

    private EventHandler<MouseEvent> getModificationSelectedEventHandler(final Modification modification) {
        return e -> {
            this.imageView.setImage(ImageService.getImage(modification.getImage(false)));
            this.label.textProperty().bind(LocaleService.getStringBinding(modification.getLocalizationKey()));
            this.label.setWrapText(true);
            final SelectedModification newModification = new SelectedModification(modification, false);
            final ModificationChange modificationChange = new ModificationChange(this.loadout.getModifications()[this.position], newModification);
            this.loadout.getModifications()[this.position] = newModification;
            EventService.publish(new ModificationChangedEvent(this.loadoutItem, modificationChange));
            this.popOver.hide(Duration.ZERO);
            this.popOver.setContentNode(null);
            this.popOver = null;
        };
    }


    @Override
    public void initEventHandling() {
        //NOOP
    }

    @Override
    public void destroyInternal() {
        if (this.popOver != null) {
            this.popOver.setContentNode(null);
            this.popOver = null;
        }
        this.loadout = null;
        this.loadoutItem = null;
        this.imageView = null;
        this.label = null;
    }

    @Override
    public List<Destroyable> getDestroyablesList() {
        return this.destroyables;
    }
}
