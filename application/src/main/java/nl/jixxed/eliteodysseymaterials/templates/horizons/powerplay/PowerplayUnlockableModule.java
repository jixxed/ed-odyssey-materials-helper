package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import javafx.scene.layout.HBox;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class PowerplayUnlockableModule extends HBox implements DestroyableTemplate {


    private Integer unlockRank;
    private ShipModule unlockable;
    private Power power;

    public PowerplayUnlockableModule(Power power, int unlockRank, ShipModule unlockable) {
        this.unlockRank = unlockRank;
        this.unlockable = unlockable;
        this.power = power;
        this.initComponents();
        this.initEventHandling();
    }

    @Override
    public void initComponents() {
        final DestroyableLabel rank = LabelBuilder.builder()
                .withStyleClass("power-rank")
                .withNonLocalizedText(unlockRank.toString())
                .build();
        final DestroyableLabel module = LabelBuilder.builder()
                .withStyleClass("power-module")
                .withText(LocaleService.getStringBinding(unlockable.getName().getLocalizationKey()))
                .build();
        this.getChildren().addAll(rank, module);
        update(ApplicationState.getInstance().getPower(), ApplicationState.getInstance().getPowerRank());
    }

    @Override
    public void initEventHandling() {

        register(EventService.addListener(true, this, PowerplayEvent.class, powerplayEvent ->
                this.update(powerplayEvent.getPower(), powerplayEvent.getRank())));
    }

    public void update(Power power, long rank) {
        this.getChildren().forEach(node -> node.getStyleClass().remove("power-rank-unlocked"));

        if (this.power.equals(power) &&  unlockRank <= rank) {
            this.getChildren().forEach(node -> node.getStyleClass().add("power-rank-unlocked"));
        }

    }
}
