package nl.jixxed.eliteodysseymaterials.templates;

import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTotalType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;

public class OdysseyMaterialTotals extends FlowPane implements Template {

    private static final String FLOW_PANE_STYLE_CLASS = "material-overview-flow-pane";
    private MaterialTotal goodsTotal;
    private MaterialTotal assetsTotal;
    private MaterialTotal dataTotal;

    public OdysseyMaterialTotals() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.goodsTotal = new MaterialTotal(OdysseyStorageType.GOOD, MaterialTotalType.BLUEPRINT, MaterialTotalType.IRRELEVANT);
        this.assetsTotal = new MaterialTotal(OdysseyStorageType.ASSET, MaterialTotalType.CHEMICAL, MaterialTotalType.CIRCUIT, MaterialTotalType.TECH);
        this.dataTotal = new MaterialTotal(OdysseyStorageType.DATA, MaterialTotalType.BLUEPRINT, MaterialTotalType.IRRELEVANT);
        this.getChildren().addAll(this.goodsTotal, this.assetsTotal, this.dataTotal);
        this.setOrientation(Orientation.HORIZONTAL);
        this.getStyleClass().add(FLOW_PANE_STYLE_CLASS);

    }

    @Override
    public void initEventHandling() {

    }
}
