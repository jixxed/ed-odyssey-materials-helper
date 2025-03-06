package nl.jixxed.eliteodysseymaterials.templates.horizons.colonisation;

import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.enums.ColonisationBuildable;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.Template;

import java.util.Comparator;

public class BillOfMaterials extends FlowPane implements Template {

    ColonisationBuildable buildable;

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

    public void setBuildable(ColonisationBuildable buildable) {
        this.buildable = buildable;
        update();

    }

    private void update() {
        this.getChildren().forEach(entry -> ((BillOfMaterialsEntry) entry).onDestroy());
        this.getChildren().clear();
        buildable.getBlueprintCost().entrySet().stream()
                .sorted(Comparator.comparing(commodityIntegerEntry -> LocaleService.getLocalizedStringForCurrentLocale(commodityIntegerEntry.getKey().getLocalizationKey())))
                .forEach(entry -> this.getChildren().add(new BillOfMaterialsEntry(entry.getKey(), entry.getValue())));
    }
}
