package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ComboBoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LoadoutChangedEvent;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

public class LoadoutConfig extends DestroyableVBox implements DestroyableTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    private LoadoutSet loadoutSet;
    private Loadout loadout;
    private DestroyableComboBox<Integer> targetLevel;
    private DestroyableComboBox<Integer> currentLevel;

    public LoadoutConfig(LoadoutSet loadoutSet, Loadout loadout) {
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("config");
        //current level
        initCurrentLevel();
        //target level
        initTargetLevel();
        //auto set correct levels on change
        currentLevel.addChangeListener(currentLevel.valueProperty(), (_, _, newValue) -> {
            if (newValue != null && newValue > this.loadout.getTargetLevel()) {
                targetLevel.getSelectionModel().select(newValue);
            }
        });
        targetLevel.addChangeListener(targetLevel.valueProperty(), (_, _, newValue) -> {
            if (newValue != null && newValue < this.loadout.getCurrentLevel()) {
                currentLevel.getSelectionModel().select(newValue);
            }
        });
        //modifications
        final DestroyableLabel title = LabelBuilder
                .builder()
                .withStyleClass("title")
                .withText("loadout.equipment.modifications")
                .build();
        final OdysseyLoadoutModification loadoutModification1 = createModSlot(0);
        final OdysseyLoadoutModification loadoutModification2 = createModSlot(1);
        final OdysseyLoadoutModification loadoutModification3 = createModSlot(2);
        final OdysseyLoadoutModification loadoutModification4 = createModSlot(3);

        final DestroyableHBox modifications = BoxBuilder.builder()
                .withNodes(loadoutModification1, new GrowingRegion(), loadoutModification2, new GrowingRegion(), loadoutModification3, new GrowingRegion(), loadoutModification4)
                .buildHBox();

        this.getNodes().addAll(title, modifications);
    }

    private OdysseyLoadoutModification createModSlot(int position) {
        return new OdysseyLoadoutModification(this.loadoutSet, this.loadout, position);
    }

    private void initCurrentLevel() {
        final DestroyableLabel currentLevelLabel = LabelBuilder.builder()
                .withStyleClass("config-name")
                .withText("loadout.equipment.level.current")
                .build();
        currentLevel = ComboBoxBuilder.builder(Integer.class)
                .withItemsProperty(FXCollections.observableArrayList(1, 2, 3, 4, 5))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        this.loadout.setCurrentLevel(newValue);
                        saveLoadoutSet();
                        EventService.publish(new LoadoutChangedEvent(this.loadout));
                    }
                })
                .withDisable(this.loadoutSet.equals(LoadoutSet.CURRENT))
                .build();
        currentLevel.getSelectionModel().select(this.loadout.getCurrentLevel());
        final DestroyableRegion regionCurrent = new DestroyableRegion();
        HBox.setHgrow(regionCurrent, Priority.ALWAYS);
        currentLevelLabel.setAlignment(Pos.CENTER_LEFT);
        final DestroyableHBox currentLevelBox = BoxBuilder.builder()
                .withNodes(currentLevelLabel, regionCurrent, currentLevel)
                .buildHBox();
        this.getNodes().addAll(currentLevelBox);
    }

    private void initTargetLevel() {
        final DestroyableLabel targetLevelLabel = LabelBuilder.builder()
                .withStyleClass("config-name")
                .withText("loadout.equipment.level.target")
                .build();
        targetLevel = ComboBoxBuilder.builder(Integer.class)
                .withItemsProperty(FXCollections.observableArrayList(1, 2, 3, 4, 5))
                .withValueChangeListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        this.loadout.setTargetLevel(newValue);
                        EventService.publish(new LoadoutChangedEvent(this.loadout));
                        saveLoadoutSet();
                    }
                })
                .withDisable(this.loadoutSet.equals(LoadoutSet.CURRENT))
                .build();
        targetLevel.getSelectionModel().select(this.loadout.getTargetLevel());
        final DestroyableRegion regionTarget = new DestroyableRegion();
        HBox.setHgrow(regionTarget, Priority.ALWAYS);
        targetLevelLabel.setAlignment(Pos.CENTER_LEFT);
        final DestroyableHBox targetLevelBox = BoxBuilder.builder()
                .withNodes(targetLevelLabel, regionTarget, targetLevel)
                .buildHBox();
        this.getNodes().addAll(targetLevelBox);
    }

    private void saveLoadoutSet() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                LoadoutService.saveLoadoutSet(commander, this.loadoutSet)
        );
    }
}
