package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.event.ColonisationSelectedEvent;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableEventTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;

import java.util.Comparator;

public class BillOfMaterials extends DestroyableFlowPane implements DestroyableEventTemplate, DestroyableComponent {

    ColonisationItem colonisationItem;

    public BillOfMaterials() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bill-of-materials");
    }

    @Override
    public void initEventHandling() {
        register(EventService.addListener(true, this, ColonisationSelectedEvent.class, event -> {
            setBuildable(event.getColonisationItem());
        }));
    }

    public void setBuildable(ColonisationItem colonisationItem) {
        this.colonisationItem = colonisationItem;
        update();

    }


    private void update() {
        clear();
        colonisationItem.getConstructionRequirements().entrySet().stream()
                .sorted(Comparator.comparing(commodityIntegerEntry -> LocaleService.getLocalizedStringForCurrentLocale(commodityIntegerEntry.getKey().getLocalizationKey())))
                .forEach(entry -> this.getNodes().add(new BillOfMaterialsEntry(colonisationItem, entry.getKey(), entry.getValue())));
    }

    public void clear() {
        this.getNodes().clear();
    }
}
