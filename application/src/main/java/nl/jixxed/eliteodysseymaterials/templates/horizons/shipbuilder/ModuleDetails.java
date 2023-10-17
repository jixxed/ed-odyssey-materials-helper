package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder;

import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.ModuleHighlightEvent;
import nl.jixxed.eliteodysseymaterials.templates.Template;
import nl.jixxed.eliteodysseymaterials.templates.components.GrowingRegion;

import java.util.ArrayList;
import java.util.List;

public class ModuleDetails extends VBox implements Template {

    private VBox attributes;
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public ModuleDetails() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("stats-values");
        this.getChildren().add(LabelBuilder.builder().withNonLocalizedText("test").build());
        attributes = BoxBuilder.builder().buildVBox();
        this.getChildren().add(attributes);
        this.getChildren().add(new GrowingRegion());
    }

    @Override
    public void initEventHandling() {
        EVENT_LISTENERS.add(EventService.addListener(this, ModuleHighlightEvent.class,(event)->{
            ShipModule shipModule = event.getShipModule();
            attributes.getChildren().clear();
            event.getShipModule().getAttibutes().forEach(horizonsModifier -> {
                final Object originalAttributeValue = shipModule.getOriginalAttributeValue(horizonsModifier);
                final Object attributeValue = shipModule.getAttributeValue(horizonsModifier);
                attributes.getChildren().add(BoxBuilder.builder().withNodes(
                        LabelBuilder.builder().withText(LocaleService.getStringBinding(horizonsModifier.getLocalizationKey())).build(),
                        LabelBuilder.builder().withNonLocalizedText(horizonsModifier.format(originalAttributeValue)).build(),
                        LabelBuilder.builder().withNonLocalizedText(" -> ").build(),
                        LabelBuilder.builder().withNonLocalizedText(horizonsModifier.format(attributeValue)).build()
                ).buildHBox());
            });
        }));
    }
}
