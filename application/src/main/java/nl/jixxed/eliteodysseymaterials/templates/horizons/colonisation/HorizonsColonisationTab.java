package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.builder.ScrollPaneBuilder;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsTabType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableScrollPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.horizons.HorizonsTab;

public class HorizonsColonisationTab extends HorizonsTab implements DestroyableTemplate {

    public HorizonsColonisationTab() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("colonisation-tab");
        ColonisationView colonisationView = new ColonisationView();
        this.addBinding(this.textProperty(), LocaleService.getStringBinding("tabs.horizons.colonisation"));
        this.setClosable(false);
        DestroyableScrollPane scrollPane = register(ScrollPaneBuilder.builder()
                .withStyleClass("colonisation-tab-content")
                .withContent(colonisationView)
                .build());
        this.setContent(scrollPane);
    }

    @Override
    public HorizonsTabType getTabType() {
        return HorizonsTabType.COLONISATION;
    }
}
