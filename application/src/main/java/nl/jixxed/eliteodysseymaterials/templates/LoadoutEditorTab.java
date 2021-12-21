package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Suit;
import nl.jixxed.eliteodysseymaterials.enums.Tabs;
import nl.jixxed.eliteodysseymaterials.enums.Weapon;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public class LoadoutEditorTab extends EDOTab implements Template {
    private ScrollPane scrollPane;

    LoadoutEditorTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public Tabs getTabType() {
        return Tabs.LOADOUT;
    }

    @Override
    public void initEventHandling() {

    }

    @Override
    public void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.loadout"));
        final FlowPane loadoutItemsFlow = FlowPaneBuilder.builder().withNodes(
                new LoadoutItem(Weapon.KINEMATIC_AR50),
                new LoadoutItem(Weapon.KINEMATIC_C44),
                new LoadoutItem(Weapon.KINEMATIC_L6),
                new LoadoutItem(Weapon.KINEMATIC_P15),
                new LoadoutItem(Weapon.TAKADA_APHELION),
                new LoadoutItem(Weapon.TAKADA_ECLIPSE),
                new LoadoutItem(Weapon.TAKADA_ZENITH),
                new LoadoutItem(Weapon.MANTICORE_EXECUTIONER),
                new LoadoutItem(Weapon.MANTICORE_OPPRESSOR),
                new LoadoutItem(Weapon.MANTICORE_SHOTGUN),
                new LoadoutItem(Weapon.MANTICORE_TORMENTOR),
                new LoadoutItem(Suit.ARTEMIS),
                new LoadoutItem(Suit.MAVERICK),
                new LoadoutItem(Suit.DOMINATOR)
        ).build();
        loadoutItemsFlow.setHgap(5);
        loadoutItemsFlow.setVgap(5);
        final VBox vBox = BoxBuilder.builder().withStyleClass("loadout-box").withNodes(loadoutItemsFlow).buildVBox();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(vBox)
                .build();
        this.setContent(this.scrollPane);
    }
}
