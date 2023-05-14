package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import javafx.scene.control.ScrollPane;
import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

public class OdysseyMaterialTab extends OdysseyTab {

    private static final double SCROLL_SPEED = 0.01;
    private ScrollPane scrollPane;
    private OdysseyMaterialOverview materialOverview;

    public OdysseyMaterialTab() {
        initComponents();
    }

    private void initComponents() {
        this.textProperty().bind(LocaleService.getStringBinding("tabs.odyssey.materials"));
        this.scrollPane = ScrollPaneBuilder.builder().build();
        this.materialOverview = new OdysseyMaterialOverview(this.scrollPane);
        this.scrollPane.setContent(this.materialOverview);
        this.setContent(this.scrollPane);
        this.scrollPane.getContent().setOnScroll(scrollEvent -> {
            final double speed = this.scrollPane.getHeight() / this.scrollPane.getContent().getLayoutBounds().getHeight();
            final double deltaY = scrollEvent.getDeltaY() * speed * SCROLL_SPEED;
            this.scrollPane.setVvalue(this.scrollPane.getVvalue() - deltaY);
        });
    }


    @Override
    public OdysseyTabs getTabType() {
        return OdysseyTabs.OVERVIEW;
    }
}
