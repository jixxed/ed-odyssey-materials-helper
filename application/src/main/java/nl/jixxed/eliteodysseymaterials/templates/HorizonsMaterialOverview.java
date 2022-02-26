package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.enums.Encoded;
import nl.jixxed.eliteodysseymaterials.enums.HorizonsMaterialType;
import nl.jixxed.eliteodysseymaterials.enums.Manufactured;
import nl.jixxed.eliteodysseymaterials.enums.Raw;
import nl.jixxed.eliteodysseymaterials.helper.ScalingHelper;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;

import java.util.Arrays;

public class HorizonsMaterialOverview extends VBox implements Template {
    HorizonsMaterialOverview() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("horizons-material-overview");
        Arrays.stream(HorizonsMaterialType.getRawTypes()).forEach(type -> {
            final HorizonsMaterialCard[] array = Arrays.stream(Raw.materialsForTypeSorted(type)).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);
            createMaterialCardRow(type, array);
        });
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        Arrays.stream(HorizonsMaterialType.getEncodedTypes()).forEach(type -> {
            final HorizonsMaterialCard[] array = Arrays.stream(Encoded.materialsForTypeSorted(type)).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);
            createMaterialCardRow(type, array);
        });
        this.getChildren().add(new Separator(Orientation.HORIZONTAL));
        Arrays.stream(HorizonsMaterialType.getManufacturedTypes()).forEach(type -> {
            final HorizonsMaterialCard[] array = Arrays.stream(Manufactured.materialsForTypeSorted(type)).map(HorizonsMaterialCard::new).toList().toArray(HorizonsMaterialCard[]::new);
            createMaterialCardRow(type, array);
        });
        this.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.5));
    }

    private void createMaterialCardRow(final HorizonsMaterialType type, final HorizonsMaterialCard[] array) {
        final DestroyableLabel category = LabelBuilder.builder().withStyleClass("horizons-material-overview-row-name").withText(LocaleService.getStringBinding(type.getLocalizationKey())).build();
        final FlowPane materials = FlowPaneBuilder.builder().withNodes(array).build();
        materials.vgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        materials.hgapProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        HBox.setHgrow(materials, Priority.ALWAYS);
        final HBox row = BoxBuilder.builder().withNodes(category, materials).buildHBox();
        row.spacingProperty().bind(ScalingHelper.getPixelDoubleBindingFromEm(0.25));
        this.getChildren().add(row);
    }

    @Override
    public void initEventHandling() {
        //NOOP
    }
}
