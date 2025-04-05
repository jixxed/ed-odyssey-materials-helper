package nl.jixxed.eliteodysseymaterials.templates.odyssey.materials;

import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyTabs;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.odyssey.OdysseyTab;

public class OdysseyMaterialTab extends OdysseyTab implements DestroyableTemplate {

    private static final double SCROLL_SPEED = 0.01;
    private DestroyableScrollPane scrollPane;
    private OdysseyMaterialOverview materialOverview;

    public OdysseyMaterialTab() {
        initComponents();
    }

    public void initComponents() {
        this.getStyleClass().add("materials-tab");
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.odyssey.materials"));
        this.materialOverview = new OdysseyMaterialOverview();
        this.scrollPane = ScrollPaneBuilder.builder()
                .withStyleClass("materials-tab-content")
                .withContent(this.materialOverview)
                .build();

        this.scrollPane.addChangeListener(this.scrollPane.widthProperty(), this.materialOverview.getResizeListener());
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
