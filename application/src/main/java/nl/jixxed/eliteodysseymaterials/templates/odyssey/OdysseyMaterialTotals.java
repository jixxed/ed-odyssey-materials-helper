package nl.jixxed.eliteodysseymaterials.templates.odyssey;

import javafx.geometry.Orientation;
import nl.jixxed.eliteodysseymaterials.enums.MaterialTotalType;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyStorageType;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;

public class OdysseyMaterialTotals extends DestroyableFlowPane implements DestroyableTemplate {

    public OdysseyMaterialTotals() {
        initComponents();
    }

    @Override
    public void initComponents() {
        OdysseyMaterialTotal goodsTotal = new OdysseyMaterialTotal(OdysseyStorageType.GOOD, MaterialTotalType.BLUEPRINT, MaterialTotalType.POWERPLAY, MaterialTotalType.IRRELEVANT);
        OdysseyMaterialTotal assetsTotal = new OdysseyMaterialTotal(OdysseyStorageType.ASSET, MaterialTotalType.CHEMICAL, MaterialTotalType.CIRCUIT, MaterialTotalType.TECH);
        OdysseyMaterialTotal dataTotal = new OdysseyMaterialTotal(OdysseyStorageType.DATA, MaterialTotalType.BLUEPRINT, MaterialTotalType.POWERPLAY, MaterialTotalType.IRRELEVANT);
        this.getNodes().addAll(goodsTotal, assetsTotal, dataTotal);
        this.setOrientation(Orientation.HORIZONTAL);
        this.getStyleClass().add("material-overview-flow-pane");

    }
}
