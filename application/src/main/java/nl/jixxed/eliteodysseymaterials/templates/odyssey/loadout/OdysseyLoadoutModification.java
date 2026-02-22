package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.*;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.domain.ModificationChange;
import nl.jixxed.eliteodysseymaterials.domain.SelectedModification;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.ImageService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModificationChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;
import org.controlsfx.control.PopOver;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Slf4j
class OdysseyLoadoutModification extends DestroyableVBox implements DestroyableTemplate {
    private static final String LOADOUT_MODIFICATION_STYLE_CLASS = "modification";
    private static final String LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS = "mod-image";
    private static final String LOADOUT_MODIFICATION_LABEL_STYLE_CLASS = "name";
    private DestroyableResizableImageView imageView;
    private DestroyableLabel label;
    private final LoadoutSet loadoutSet;
    private Loadout loadout;
    private final int position;
    private DestroyablePopOver popOver;

    private DestroyableLabel setEquiped;
    private DestroyableLabel changeMod;

    OdysseyLoadoutModification(final LoadoutSet loadoutSet, final Loadout loadout, final Integer position) {
        this.loadout = loadout;
        this.loadoutSet = loadoutSet;
        this.position = position;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add(LOADOUT_MODIFICATION_STYLE_CLASS);
        this.imageView = ResizableImageViewBuilder.builder()
                .withStyleClasses(LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS)
                .withImage((this.loadout.getModifications()[this.position] != null) ? this.loadout.getModifications()[this.position].getImage() : "/images/modification/empty.png")
                .build();
        final boolean hasModification = this.loadout.getModifications()[this.position] != null && this.loadout.getModifications()[this.position].getModification() != null;
        this.label = LabelBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_LABEL_STYLE_CLASS)
                .withText(hasModification ? this.loadout.getModifications()[this.position].getModification().getLocalizationKey() : "loadout.modification.name.none")
                .build();
        if (!this.loadoutSet.equals(LoadoutSet.CURRENT)) {
            this.imageView.pseudoClassStateChanged(PseudoClass.getPseudoClass("modifiable"), true);
            setEquiped = LabelBuilder.builder()
                    .withStyleClass("control")
                    .withNonLocalizedText(hasModification && !this.loadout.getModifications()[this.position].isPresent() ? "Set equiped" : "Set Unequiped")
                    .withOnMouseClicked(_ -> togglePresent())
                    .withVisibility(hasModification)
                    .withManaged(hasModification)
                    .build();
            changeMod = LabelBuilder.builder()
                    .withStyleClass("control")
                    .withNonLocalizedText(hasModification ? "Change mod" : "Select mod")
                    .withOnMouseClicked(event -> {
                        if (event.getButton().equals(MouseButton.SECONDARY)) {
                            clear();
                            return;
                        }
                        processSingleClick(event);
                    })
                    .build();
            final DestroyableVBox controls = BoxBuilder.builder()
                    .withStyleClass("controls")
                    .withNodes(setEquiped, changeMod)
                    .buildVBox();
            VBox.setVgrow(setEquiped, Priority.ALWAYS);
            VBox.setVgrow(changeMod, Priority.ALWAYS);
            DestroyableStackPane stackPane = StackPaneBuilder.builder()
                    .withStyleClass("layers")
                    .withNodes(this.imageView, controls)
                    .build();
            VBox.setVgrow(stackPane, Priority.ALWAYS);
            this.getNodes().addAll(stackPane, this.label);
        } else {
            this.getNodes().addAll(this.imageView, this.label);
        }


    }


    private void processSingleClick(final MouseEvent event) {
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
            try {
                final Screen screen = Screen.getScreensForRectangle(event.getScreenX(), event.getScreenY(), 1, 1).getFirst();
                this.popOver = PopOverBuilder.builder()
                        .withStyleClass("loadout-modification-popover")
                        .withContent(createModificationOptionsGrid())
                        .withDetachable(false)
                        .withHeaderAlwaysVisible(false)
                        .withArrowIndent(0)
                        .withArrowSize(0)
                        .withCornerRadius(0)
                        .withDestroyOnHide(true)
                        .build();
                final Rectangle2D currentScreen = screen.getBounds();
                final double mouseXOnScreen = event.getScreenX() - currentScreen.getMinX();
                final double mouseYOnScreen = event.getScreenY() - currentScreen.getMinY();
                if (mouseXOnScreen < currentScreen.getWidth() / 2 && mouseYOnScreen < currentScreen.getHeight() / 2) {
                    popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
                } else if (mouseXOnScreen < currentScreen.getWidth() / 2 && mouseYOnScreen > currentScreen.getHeight() / 2) {
                    popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_BOTTOM);
                } else if (mouseXOnScreen > currentScreen.getWidth() / 2 && mouseYOnScreen < currentScreen.getHeight() / 2) {
                    popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
                } else {
                    popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_BOTTOM);
                }
                this.popOver.show(this, event.getScreenX(), event.getScreenY());
            } catch (NoSuchElementException ex) {
                log.error(ex.getMessage(), ex);
            }
        });
    }

    private void togglePresent() {
        if (this.loadout.getModifications()[this.position] != null) {
            this.loadout.getModifications()[this.position].setPresent(!this.loadout.getModifications()[this.position].isPresent());
            this.imageView.setImage(ImageService.getImage(this.loadout.getModifications()[this.position].getImage()));
            final ModificationChange modificationChange = new ModificationChange(this.loadout.getModifications()[this.position], this.loadout.getModifications()[this.position]);
            EventService.publish(new ModificationChangedEvent(this.loadout, modificationChange));
        }
        setEquiped.setText(this.loadout.getModifications()[this.position] != null && !this.loadout.getModifications()[this.position].isPresent() ? "Set equiped" : "Set Unequiped");
    }


    private DestroyableFlowPane createModificationOptionsGrid() {
        final var flowPane = FlowPaneBuilder.builder().build();
//        final DestroyablePane pane = PaneBuilder.builder().withNode(flowPane).build();

        if (this.loadout.getEquipment() instanceof Weapon) {
            flowPane.getStyleClass().add("grid-weapon");
            addMod(getHeadshotDamageModification(), flowPane);
            addMod(WeaponModification.MAGAZINE_SIZE, flowPane);
            addMod(WeaponModification.STOWED_RELOADING, flowPane);
            addMod(WeaponModification.SCOPE, flowPane);

            addMod(WeaponModification.NOISE_SUPPRESSOR, flowPane);
            addMod(WeaponModification.AUDIO_MASKING, flowPane);
            addMod(WeaponModification.FASTER_HANDLING, flowPane);
            addMod(getGreaterRangeModification(), flowPane);

            addMod(WeaponModification.RELOAD_SPEED, flowPane);
            addMod(WeaponModification.STABILITY, flowPane);
            addMod(getHigherAccuracyModification(), flowPane);
            createCancelCell(flowPane);
        } else if (this.loadout.getEquipment() instanceof Suit) {
            flowPane.getStyleClass().add("grid-suit");
            addMod(SuitModification.ADDED_MELEE_DAMAGE, flowPane);
            addMod(SuitModification.COMBAT_MOVEMENT_SPEED, flowPane);
            addMod(SuitModification.DAMAGE_RESISTANCE, flowPane);
            addMod(SuitModification.ENHANCED_TRACKING, flowPane);
            addMod(SuitModification.QUIETER_FOOTSTEPS, flowPane);

            addMod(SuitModification.EXTRA_AMMO_CAPACITY, flowPane);
            addMod(SuitModification.EXTRA_BACKPACK_CAPACITY, flowPane);
            addMod(SuitModification.IMPROVED_BATTERY_CAPACITY, flowPane);
            addMod(SuitModification.FASTER_SHIELD_REGEN, flowPane);
            addMod(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION, flowPane);

            addMod(SuitModification.IMPROVED_JUMP_ASSIST, flowPane);
            addMod(SuitModification.INCREASED_AIR_RESERVES, flowPane);
            addMod(SuitModification.INCREASED_SPRINT_DURATION, flowPane);
            addMod(SuitModification.NIGHT_VISION, flowPane);
            createCancelCell(flowPane);

        }
        return flowPane;
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

    private void addMod(@NonNull final Modification modification, final DestroyableFlowPane flowPane) {
        final DestroyableResizableImageView modSelectImage = ResizableImageViewBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS)
                .withImage(modification.getImage(false))
                .build();
        modSelectImage.pseudoClassStateChanged(PseudoClass.getPseudoClass("modifiable"), true);
        if (Arrays.stream(this.loadout.getModifications()).map(SelectedModification::getModification).anyMatch(modification::equals)) {
            modSelectImage.pseudoClassStateChanged(PseudoClass.getPseudoClass("consumed"), true);
        } else {
            modSelectImage.addEventBinding(modSelectImage.onMouseClickedProperty(), getModificationSelectedEventHandler(modification));
        }
        final DestroyableLabel modLabel = LabelBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_LABEL_STYLE_CLASS)
                .withText(modification.getLocalizationKey())
                .build();
        final DestroyableVBox modVBox = BoxBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_STYLE_CLASS)
                .withNodes(modSelectImage, modLabel).buildVBox();
        flowPane.getNodes().add(modVBox);
    }

    private void createCancelCell(final DestroyableFlowPane flowPane) {
        final DestroyableResizableImageView modSelectImage = ResizableImageViewBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_IMAGE_STYLE_CLASS)
                .withImage("/images/modification/cancel.png")
                .withOnMouseClicked(getClearEventHandler())
                .build();
        modSelectImage.pseudoClassStateChanged(PseudoClass.getPseudoClass("modifiable"), true);
        final DestroyableLabel modLabel = LabelBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_LABEL_STYLE_CLASS)
                .withText("loadout.modification.clear")
                .build();
        final DestroyableVBox modVBox = BoxBuilder.builder()
                .withStyleClass(LOADOUT_MODIFICATION_STYLE_CLASS)
                .withNodes(modSelectImage, modLabel).buildVBox();
        flowPane.getNodes().add(modVBox);
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
        this.label.addBinding(this.label.textProperty(), LocaleService.getStringBinding("loadout.modification.name.none"));
        final SelectedModification newModification = new SelectedModification(null, false);
        final ModificationChange modificationChange = new ModificationChange(this.loadout.getModifications()[this.position], newModification);
        this.loadout.getModifications()[this.position] = newModification;
        EventService.publish(new ModificationChangedEvent(this.loadout, modificationChange));
        updateControls(false);
    }

    private EventHandler<MouseEvent> getModificationSelectedEventHandler(final Modification modification) {
        return e -> {
            this.imageView.setImage(ImageService.getImage(modification.getImage(false)));
            this.label.addBinding(this.label.textProperty(), LocaleService.getStringBinding(modification.getLocalizationKey()));
            final SelectedModification newModification = new SelectedModification(modification, false);
            final ModificationChange modificationChange = new ModificationChange(this.loadout.getModifications()[this.position], newModification);
            this.loadout.getModifications()[this.position] = newModification;
            EventService.publish(new ModificationChangedEvent(this.loadout, modificationChange));
            this.popOver.hide(Duration.ZERO);
            this.popOver.setContentNode(null);
            this.popOver = null;
            updateControls(true);
        };
    }

    private void updateControls(boolean equiped) {
        setEquiped.setVisible(equiped);
        setEquiped.setManaged(equiped);
        setEquiped.setText("Set equiped");
        changeMod.setText(equiped ? "Change mod" : "Select mod");
    }

    @Override
    public void destroyInternal() {
        super.destroyInternal();
        if (this.popOver != null) {
            this.popOver.destroy();
            this.popOver.setContentNode(null);
            this.popOver = null;
        }
        this.loadout = null;
        this.imageView = null;
        this.label = null;
    }
}
