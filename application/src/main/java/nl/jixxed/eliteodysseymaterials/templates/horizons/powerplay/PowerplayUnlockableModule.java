package nl.jixxed.eliteodysseymaterials.templates.horizons.powerplay;

import javafx.css.PseudoClass;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.Power;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayEvent;
import nl.jixxed.eliteodysseymaterials.service.event.PowerplayLeaveEvent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

public class PowerplayUnlockableModule extends DestroyableHBox implements DestroyableEventTemplate {


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
        this.getStyleClass().addAll("powerplay-unlockable");
        final DestroyableLabel rank = LabelBuilder.builder()
                .withStyleClass("rank")
                .withNonLocalizedText(unlockRank.toString())
                .build();
        final DestroyableLabel module = LabelBuilder.builder()
                .withStyleClass("module")
                .withText(unlockable.getName().getLocalizationKey())
                .build();
        this.getNodes().addAll(rank, module);
        update(ApplicationState.getInstance().getPower(), ApplicationState.getInstance().getPowerRank());
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, PowerplayEvent.class, powerplayEvent ->
                powerplayEvent.getRank().ifPresent(rank ->
                        this.update(powerplayEvent.getPower(), rank))));

        register(EventService.addListener(true, this, PowerplayLeaveEvent.class, powerplayLeaveEvent ->
                this.update(powerplayLeaveEvent.getPower(), 0L)));
    }

    public void update(Power power, long rank) {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("unlocked"), this.power.equals(power) && unlockRank <= rank);
    }
}
