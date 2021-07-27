package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTotalType;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaterialTotal extends VBox {
    private final Map<MaterialTotalType, Label> totals = new HashMap<>();
    private final Map<MaterialTotalType, Label> totalValues = new HashMap<>();
    private Integer totalValue = 0;

    public MaterialTotal(final StorageType storageType, final MaterialTotalType... totalTypes) {
        final Label name = new Label();
        name.getStyleClass().add("material-total-name");
        name.textProperty().bind(LocaleService.getStringBinding(storageType.getLocalizationKey()));
        Arrays.stream(totalTypes).forEach(totalType -> {
            if (MaterialTotalType.TOTAL.equals(totalType)) {
                throw new IllegalArgumentException("TOTAL not supported");
            }
            final Label value = new Label();
            value.textProperty().bind(LocaleService.getStringBinding(totalType.getLocalizationKey()));
            this.totals.put(totalType, value);
            final Label value1 = new Label("0");
            value1.getStyleClass().add("material-total-value-row");

            if (MaterialTotalType.IRRELEVANT.equals(totalType)) {

                value.getStyleClass().add("material-total-value-irrelevant-row");
                value1.getStyleClass().add("material-total-value-irrelevant-row");
            } else {
                value.getStyleClass().add("material-total-value-row");
                value1.getStyleClass().add("material-total-value-row");
            }
            this.totalValues.put(totalType, value1);
        });
        this.getChildren().add(name);
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.totals.keySet().forEach(key ->
        {
            final Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            this.getChildren().add(new HBox(this.totals.get(key), region, this.totalValues.get(key)));
        });
        final Region vRegion = new Region();
        VBox.setVgrow(vRegion, Priority.ALWAYS);
        this.getChildren().add(vRegion);

        final Label total = new Label();
        total.getStyleClass().add("material-total-total-row");
        total.textProperty().bind(LocaleService.getStringBinding(MaterialTotalType.TOTAL.getLocalizationKey()));
        this.totals.put(MaterialTotalType.TOTAL, total);
        final Label totalValueLabel = new Label("0");
        totalValueLabel.getStyleClass().add("material-total-total-row");
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        this.totalValues.put(MaterialTotalType.TOTAL, totalValueLabel);
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        this.getChildren().add(new HBox(total, region, totalValueLabel));

        this.getStyleClass().add("material-total");
        this.getStyleClass().add("material-total-" + storageType.name().toLowerCase());
    }

    public void update(final MaterialTotalType type, final Integer amount) {
        if (this.totalValues.containsKey(type)) {
            final Label label = this.totalValues.get(type);
            final Integer currentAmount = Integer.parseInt(label.getText());
            label.setText(amount.toString());
            final Label totalLabel = this.totalValues.get(MaterialTotalType.TOTAL);
            this.totalValue += (amount - currentAmount);
            totalLabel.setText(this.totalValue + "/1000");
        }
    }
}
