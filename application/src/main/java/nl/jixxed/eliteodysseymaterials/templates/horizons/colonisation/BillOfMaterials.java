package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableComponent;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

import java.util.Comparator;

public class BillOfMaterials extends DestroyableFlowPane implements DestroyableTemplate, DestroyableComponent {

    ColonisationItem colonisationItem ;

    public BillOfMaterials() {
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bill-of-materials");
    }

    public void setBuildable(ColonisationItem colonisationItem ) {
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
