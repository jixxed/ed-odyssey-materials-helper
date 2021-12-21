package nl.jixxed.eliteodysseymaterials.templates;

import javafx.event.EventHandler;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

class LoadoutModification extends VBox implements Template {
    private ResizableImageView imageView;
    private Label label;
    private final Equipment equipment;

    LoadoutModification(final Equipment equipment) {
        this.equipment = equipment;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout-modification");
        this.imageView = ResizableImageViewBuilder.builder().withStyleClass("loadout-modification-image").withImage("/images/modification/empty.png").build();
        final Rectangle value = new Rectangle(80, 67);
        value.setArcWidth(10);
        value.setArcHeight(10);
        this.imageView.setClip(value);
        this.label = LabelBuilder.builder().withStyleClass("loadout-modification-label").withText(LocaleService.getStringBinding("loadout.modification.name.none")).build();
        this.imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                clear();
                return;
            }
            if (!event.getButton().equals(MouseButton.PRIMARY)) {
                return;
            }
            final Dialog<Modification> dialog = new Dialog<>();
            final DialogPane dialogPane = dialog.getDialogPane();
            final JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(dialogPane.getScene());
            dialogPane.getScene().getStylesheets().add(getClass().getResource("/nl/jixxed/eliteodysseymaterials/style/style.css").toExternalForm());
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.getDialogPane().getStyleClass().add("loadout-modification-select-dialogpane");

            final GridPane gridPane = createModificationOptionsGrid(dialog);


            dialog.setX(event.getScreenX());
            dialog.setY(event.getScreenY());
            dialogPane.setContent(gridPane);
            dialog.show();
        });
        this.getChildren().addAll(this.imageView, this.label);
    }

    private GridPane createModificationOptionsGrid(final Dialog<Modification> dialog) {
        final GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("loadout-modification-grid");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        if (this.equipment instanceof Weapon) {
            createGridPaneCell(getHeadshotDamageModification(), 0, 0, dialog, gridPane);
            createGridPaneCell(WeaponModification.MAGAZINE_SIZE, 1, 0, dialog, gridPane);
            createGridPaneCell(WeaponModification.STOWED_RELOADING, 2, 0, dialog, gridPane);
            createGridPaneCell(WeaponModification.SCOPE, 3, 0, dialog, gridPane);

            createGridPaneCell(WeaponModification.NOISE_SUPPRESSOR, 0, 1, dialog, gridPane);
            createGridPaneCell(WeaponModification.AUDIO_MASKING, 1, 1, dialog, gridPane);
            createGridPaneCell(WeaponModification.FASTER_HANDLING, 2, 1, dialog, gridPane);
            createGridPaneCell(getGreaterRangeModification(), 3, 1, dialog, gridPane);

            createGridPaneCell(WeaponModification.RELOAD_SPEED, 0, 2, dialog, gridPane);
            createGridPaneCell(WeaponModification.STABILITY, 1, 2, dialog, gridPane);
            createGridPaneCell(getHigherAccuracyModification(), 2, 2, dialog, gridPane);
            createCancelCell(3, 2, dialog, gridPane);
        } else if (this.equipment instanceof Suit) {
            createGridPaneCell(SuitModification.ADDED_MELEE_DAMAGE, 0, 0, dialog, gridPane);
            createGridPaneCell(SuitModification.COMBAT_MOVEMENT_SPEED, 1, 0, dialog, gridPane);
            createGridPaneCell(SuitModification.DAMAGE_RESISTANCE, 2, 0, dialog, gridPane);
            createGridPaneCell(SuitModification.ENHANCED_TRACKING, 3, 0, dialog, gridPane);
            createGridPaneCell(SuitModification.QUIETER_FOOTSTEPS, 4, 0, dialog, gridPane);

            createGridPaneCell(SuitModification.EXTRA_AMMO_CAPACITY, 0, 1, dialog, gridPane);
            createGridPaneCell(SuitModification.EXTRA_BACKPACK_CAPACITY, 1, 1, dialog, gridPane);
            createGridPaneCell(SuitModification.IMPROVED_BATTERY_CAPACITY, 2, 1, dialog, gridPane);
            createGridPaneCell(SuitModification.FASTER_SHIELD_REGEN, 3, 1, dialog, gridPane);
            createGridPaneCell(SuitModification.REDUCED_TOOL_BATTERY_CONSUMPTION, 4, 1, dialog, gridPane);

            createGridPaneCell(SuitModification.IMPROVED_JUMP_ASSIST, 0, 2, dialog, gridPane);
            createGridPaneCell(SuitModification.INCREASED_AIR_RESERVES, 1, 2, dialog, gridPane);
            createGridPaneCell(SuitModification.INCREASED_SPRINT_DURATION, 2, 2, dialog, gridPane);
            createGridPaneCell(SuitModification.NIGHT_VISION, 3, 2, dialog, gridPane);
            createCancelCell(4, 2, dialog, gridPane);

        }
        return gridPane;
    }

    private WeaponModification getHigherAccuracyModification() {
        final Weapon weapon = (Weapon) this.equipment;
        if (weapon.isKinetic()) {
            return WeaponModification.HIGHER_ACCURACY_KINETIC;
        } else if (weapon.isLaser()) {
            return WeaponModification.HIGHER_ACCURACY_LASER;
        } else {
            return WeaponModification.HIGHER_ACCURACY_PLASMA;
        }
    }

    private WeaponModification getGreaterRangeModification() {
        final Weapon weapon = (Weapon) this.equipment;
        if (weapon.isKinetic()) {
            return WeaponModification.GREATER_RANGE_KINETIC;
        } else if (weapon.isLaser()) {
            return WeaponModification.GREATER_RANGE_LASER;
        } else {
            return WeaponModification.GREATER_RANGE_PLASMA;
        }
    }

    private WeaponModification getHeadshotDamageModification() {
        final Weapon weapon = (Weapon) this.equipment;
        if (weapon.isKinetic()) {
            return WeaponModification.HEADSHOT_DAMAGE_KINETIC;
        } else if (weapon.isLaser()) {
            return WeaponModification.HEADSHOT_DAMAGE_LASER;
        } else {
            return WeaponModification.HEADSHOT_DAMAGE_PLASMA;
        }
    }

    private void createGridPaneCell(final Modification modification, final int col, final int row, final Dialog<Modification> dialog, final GridPane gridPane) {
        final ResizableImageView modSelectImage = ResizableImageViewBuilder.builder().withStyleClass("loadout-modification-image").withImage(modification.getImage()).withOnMouseClicked(getModificationSelectedEventHandler(dialog, modification)).build();
        final Rectangle value = new Rectangle(80, 67);
        value.setArcWidth(10);
        value.setArcHeight(10);
        modSelectImage.setClip(value);
        final Label modLabel = LabelBuilder.builder().withStyleClass("loadout-modification-label").withText(LocaleService.getStringBinding(modification.getLocalizationKey())).build();
        final VBox modVBox = BoxBuilder.builder().withStyleClass("loadout-modification").withNodes(modSelectImage, modLabel).buildVBox();
        gridPane.add(modVBox, col, row);
    }

    private void createCancelCell(final int col, final int row, final Dialog<Modification> dialog, final GridPane gridPane) {
        final ResizableImageView modSelectImage = ResizableImageViewBuilder.builder().withStyleClass("loadout-modification-image").withImage("/images/modification/cancel.png").withOnMouseClicked(getClearEventHandler(dialog)).build();
        final Rectangle value = new Rectangle(80, 67);
        value.setArcWidth(10);
        value.setArcHeight(10);
        modSelectImage.setClip(value);
        final Label modLabel = LabelBuilder.builder().withStyleClass("loadout-modification-label").withText(LocaleService.getStringBinding("loadout.modification.clear")).build();
        final VBox modVBox = BoxBuilder.builder().withStyleClass("loadout-modification").withNodes(modSelectImage, modLabel).buildVBox();
        gridPane.add(modVBox, col, row);
    }

    private EventHandler<MouseEvent> getClearEventHandler(final Dialog<Modification> dialog) {
        return e -> {
            dialog.setResult(WeaponModification.NONE);
            clear();
            dialog.close();
        };
    }

    private void clear() {
        this.imageView.setImage(new Image(getClass().getResourceAsStream("/images/modification/empty.png")));
        this.label.textProperty().bind(LocaleService.getStringBinding("loadout.modification.name.none"));
    }

    private EventHandler<MouseEvent> getModificationSelectedEventHandler(final Dialog<Modification> dialog, final Modification modification) {
        return e -> {
            dialog.setResult(modification);
            this.imageView.setImage(new Image(getClass().getResourceAsStream(modification.getImage())));
            this.label.textProperty().bind(LocaleService.getStringBinding(modification.getLocalizationKey()));
            this.label.setWrapText(true);
            dialog.close();
        };
    }

    @Override
    public void initEventHandling() {

    }
}
