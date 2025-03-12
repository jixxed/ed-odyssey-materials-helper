package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTotalType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class OdysseyMaterialTotals extends FlowPane implements DestroyableTemplate {

    private static final String FLOW_PANE_STYLE_CLASS = "material-overview-flow-pane";
    private OdysseyMaterialTotal goodsTotal;
    private OdysseyMaterialTotal assetsTotal;
    private OdysseyMaterialTotal dataTotal;

    public OdysseyMaterialTotals() {
        initComponents();
        initEventHandling();
    }

    @Override
    public void initComponents() {
        this.goodsTotal = new OdysseyMaterialTotal(OdysseyStorageType.GOOD, MaterialTotalType.BLUEPRINT, MaterialTotalType.POWERPLAY, MaterialTotalType.IRRELEVANT);
        this.assetsTotal = new OdysseyMaterialTotal(OdysseyStorageType.ASSET, MaterialTotalType.CHEMICAL, MaterialTotalType.CIRCUIT, MaterialTotalType.TECH);
        this.dataTotal = new OdysseyMaterialTotal(OdysseyStorageType.DATA, MaterialTotalType.BLUEPRINT, MaterialTotalType.POWERPLAY, MaterialTotalType.IRRELEVANT);
        this.getChildren().addAll(this.goodsTotal, this.assetsTotal, this.dataTotal);
        this.setOrientation(Orientation.HORIZONTAL);
        this.getStyleClass().add(FLOW_PANE_STYLE_CLASS);

    }

    @Override
    public void initEventHandling() {
        //NOOP
    }
}
