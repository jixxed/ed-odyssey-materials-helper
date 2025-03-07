package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.domain.ColonisationItem;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.Comparator;

public class BillOfMaterials extends FlowPane implements Template {

    ColonisationItem colonisationItem ;

    public BillOfMaterials() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("bill-of-materials");
        //NOOP
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }

    public void setBuildable(ColonisationItem colonisationItem ) {
        this.colonisationItem = colonisationItem;
        update();

    }

    private void update() {
        clear();
        colonisationItem.getConstructionRequirements().entrySet().stream()
                .sorted(Comparator.comparing(commodityIntegerEntry -> LocaleService.getLocalizedStringForCurrentLocale(commodityIntegerEntry.getKey().getLocalizationKey())))
                .forEach(entry -> this.getChildren().add(new BillOfMaterialsEntry(colonisationItem, entry.getKey(), entry.getValue())));
    }

    public void clear() {
        this.getChildren().forEach(entry -> ((BillOfMaterialsEntry) entry).onDestroy());
        this.getChildren().clear();
    }
}
