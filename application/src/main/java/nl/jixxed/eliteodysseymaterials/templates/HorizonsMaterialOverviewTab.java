package nl.jixxed.eliteodysseymaterials.templates;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;

public class HorizonsMaterialOverviewTab extends Tab implements Template {
    private ScrollPane scrollPane;
    private HorizonsMaterialOverview materialOverview;

    HorizonsMaterialOverviewTab() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.materialOverview = new HorizonsMaterialOverview();
        this.setText("Horizons");
        this.setClosable(false);
        this.scrollPane = ScrollPaneBuilder.builder()
                .withContent(this.materialOverview)
                .build();
        this.setContent(this.scrollPane);

    }

    @Override
    public void initEventHandling() {
        //NOOP
    }

}
