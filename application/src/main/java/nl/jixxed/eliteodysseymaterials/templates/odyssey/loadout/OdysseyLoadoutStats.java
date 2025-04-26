package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ToggleSwitchBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.domain.LoadoutSet;
import nl.jixxed.eliteodysseymaterials.enums.Stat;
import nl.jixxed.eliteodysseymaterials.enums.StatGroup;
import nl.jixxed.eliteodysseymaterials.enums.Suit;
import nl.jixxed.eliteodysseymaterials.service.LoadoutService;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OdysseyLoadoutStats extends DestroyableVBox implements DestroyableTemplate {
    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
    private static final String VALUE_STYLE_CLASS = "value";
    LoadoutSet loadoutSet;
    Loadout loadout;
    private DestroyableToggleSwitch statsToggle;
    private List<StatsGroup> statsGroups = new ArrayList<>();
    private DestroyableHBox columnNames;

    public OdysseyLoadoutStats(LoadoutSet loadoutSet, Loadout loadout) {
        this.loadoutSet = loadoutSet;
        this.loadout = loadout;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("stats");
        addTitle();
        addColumnNames();
        addStatsGroups();
    }

    private void addStatsGroups() {
        loadout.getEquipment().getStats().keySet().stream()
                .map(Stat::getStatGroup)
                .sorted(Comparator.comparing(StatGroup::ordinal))
                .distinct()
                .forEach(statGroup -> {
                    final StatsGroup statsGroup = new StatsGroup(statGroup, loadoutSet, loadout);
                    statsGroups.add(statsGroup);
                    this.getNodes().add(statsGroup);
                });
    }

    private void addTitle() {
        //stats
        final DestroyableLabel statsLabel = LabelBuilder.builder()
                .withStyleClass("title")
                .withText("loadout.equipment.stats")
                .build();
        final DestroyableRegion region = new DestroyableRegion();
        HBox.setHgrow(region, Priority.ALWAYS);

        this.statsToggle = register(ToggleSwitchBuilder.builder()
                .withSelected(this.loadout.isShowChanged())
                .withText(this.loadout.isShowChanged() ? "loadout.equipment.stats.toggle.changed" : "loadout.equipment.stats.toggle.all")
                .withSelectedChangeListener((_, _, newValue) -> {
                    final boolean changed = Boolean.TRUE.equals(newValue);
                    final String text = changed ? "loadout.equipment.stats.toggle.changed" : "loadout.equipment.stats.toggle.all";
                    this.statsToggle.addBinding(this.statsToggle.textProperty(), LocaleService.getStringBinding(text));
                    this.loadout.setShowChanged(changed);
                    saveLoadoutSet();
                    update();
                })
                .build());
        final DestroyableHBox header = BoxBuilder.builder()
                .withNodes(statsLabel, region, this.statsToggle).buildHBox();
        this.getNodes().add(header);
    }

    private void addColumnNames() {
//        final DestroyableLabel valueColumn = LabelBuilder.builder()
//                .withStyleClasses("name")
//                .withNonLocalizedText("")
//                .build();
        final DestroyableLabel headerCurrent = LabelBuilder.builder()
                .withStyleClasses(VALUE_STYLE_CLASS)
                .withText("loadout.equipment.stats.header.current")
                .build();
        columnNames = BoxBuilder.builder()
                .withStyleClass("header")
                .withNodes(new GrowingRegion(), headerCurrent)
                .buildHBox();
        if (!Suit.FLIGHTSUIT.equals(this.loadout.getEquipment())) {
            final DestroyableLabel headerTarget = LabelBuilder.builder()
                    .withStyleClasses(VALUE_STYLE_CLASS)
                    .withText("loadout.equipment.stats.header.target")
                    .build();
            final DestroyableLabel headerModded = LabelBuilder.builder()
                    .withStyleClasses(VALUE_STYLE_CLASS)
                    .withText("loadout.equipment.stats.header.modded")
                    .build();
            columnNames.getNodes().addAll(headerTarget, headerModded);
        }
        // else {
//            valueColumn.pseudoClassStateChanged(PseudoClass.getPseudoClass("wide"), Suit.FLIGHTSUIT.equals(loadout.getEquipment()));
        // }
        this.getNodes().add(columnNames);
    }

    public void update() {
        statsGroups.forEach(StatsGroup::update);
        final boolean columnNamesVisible = statsGroups.stream().anyMatch(StatsGroup::isVisible);
        this.columnNames.setVisible(columnNamesVisible);
        this.columnNames.setManaged(columnNamesVisible);
    }

    private void saveLoadoutSet() {
        APPLICATION_STATE.getPreferredCommander().ifPresent(commander ->
                LoadoutService.saveLoadoutSet(commander, this.loadoutSet)
        );
    }
}
