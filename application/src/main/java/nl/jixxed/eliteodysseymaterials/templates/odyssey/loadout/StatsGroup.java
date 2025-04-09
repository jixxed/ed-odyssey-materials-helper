package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.service.event.AmmoCapacityModEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatsGroup extends DestroyableVBox implements DestroyableEventTemplate {
    private final StatGroup statGroup;
    private final LoadoutSet loadoutSet;
    private final Loadout loadout;
    private final List<DynamicStatLine> dynamicStats = new ArrayList<>();
    private List<StaticStatLine> staticStats = new ArrayList<>();

    public StatsGroup(StatGroup statGroup, LoadoutSet loadoutSet, Loadout loadout) {
        this.statGroup = statGroup;
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        initComponents();
        initEventHandling();
    }

    @Override
    public void initEventHandling() {

        if (this.loadout.getEquipment() instanceof Weapon) {
            register(EventService.addListener(true, this, AmmoCapacityModEvent.class, _ ->
                    update()
            ));
        }
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("stats-group");

        //line
        final DestroyableSeparator separator = new DestroyableSeparator(Orientation.HORIZONTAL);
        HBox.setHgrow(separator, Priority.ALWAYS);
        this.getNodes().add(BoxBuilder.builder().withNode(separator).buildHBox());
        //group name
        final DestroyableLabel nameColumn = LabelBuilder.builder()
                .withStyleClasses("group-name")
                .withText(statGroup.getLocalizationKey())
                .build();
        this.getNodes().add(nameColumn);
        //stats
        loadout.getEquipment().getStats().keySet().stream()
                .filter((Stat stat) -> stat.getStatGroup().equals(statGroup))
                .sorted(Comparator.comparing(Stat::getOrder))
                .forEach(statObjectEntry ->
                {
                    if (statObjectEntry instanceof DynamicStat stat) {
                        final DynamicStatLine dynamicStatLine = new DynamicStatLine(stat, loadoutSet, loadout);
                        dynamicStats.add(dynamicStatLine);
                        this.getNodes().add(dynamicStatLine);
                    } else if (statObjectEntry instanceof StaticStat stat) {
                        final StaticStatLine staticStatLine = new StaticStatLine(stat, loadout);
                        staticStats.add(staticStatLine);
                        this.getNodes().add(staticStatLine);
                    }
                });
        setVisibility();
    }

    private boolean hasChangedValues() {
        return dynamicStats.stream().anyMatch(DynamicStatLine::isVisible);
    }

    public void update() {
        dynamicStats.forEach(DynamicStatLine::update);
        staticStats.forEach(StaticStatLine::update);
        setVisibility();

    }

    private void setVisibility() {
        final boolean showGroup = !loadout.isShowChanged() || hasChangedValues();
        this.setVisible(showGroup);
        this.setManaged(showGroup);
    }
}
