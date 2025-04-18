package nl.jixxed.eliteodysseymaterials.templates.odyssey.loadout;

import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.Loadout;
import nl.jixxed.eliteodysseymaterials.enums.StaticStat;
import nl.jixxed.eliteodysseymaterials.enums.Suit;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableSeparator;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class StaticStatLine extends DestroyableHBox implements DestroyableTemplate {

    private final StaticStat staticStat;
    private Loadout loadout;

    public StaticStatLine(StaticStat staticStat, Loadout loadout) {
        this.staticStat = staticStat;
        this.loadout = loadout;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("static-stat");
        final Object value = loadout.getEquipment().getStats().get(staticStat);
        final String currentLevelValue = staticStat.formatValue(value, loadout.getEquipment(), this.loadout.getCurrentLevel());
        final DestroyableLabel nameColumn = LabelBuilder.builder()
                .withStyleClasses("name")
                .withText(staticStat.getLocalizationKey())
                .build();
        nameColumn.pseudoClassStateChanged(PseudoClass.getPseudoClass("wide"), Suit.FLIGHTSUIT.equals(loadout.getEquipment()));

        final DestroyableLabel currentLevelValueLabel = LabelBuilder.builder()
                .withStyleClasses("value")
                .withNonLocalizedText(currentLevelValue)
                .build();
        final DestroyableSeparator lineLeft = new DestroyableSeparator(Orientation.HORIZONTAL);
        final DestroyableSeparator lineRight = new DestroyableSeparator(Orientation.HORIZONTAL);
        HBox.setHgrow(lineLeft, Priority.ALWAYS);
        HBox.setHgrow(currentLevelValueLabel, Priority.ALWAYS);
        HBox.setHgrow(lineRight, Priority.ALWAYS);

        this.getNodes().addAll(
                nameColumn,
                BoxBuilder.builder()
                        .withStyleClass("value-box")
                        .withNodes(lineLeft,
                                currentLevelValueLabel,
                                lineRight)
                        .buildHBox()
        );
        update();
    }

    public void update() {
        this.setVisible(!loadout.isShowChanged());
        this.setManaged(!loadout.isShowChanged());
    }
}
