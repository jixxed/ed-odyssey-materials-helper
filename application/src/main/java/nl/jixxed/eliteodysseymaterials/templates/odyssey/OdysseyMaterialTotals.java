/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        this.getStyleClass().add("totals-flow-pane");

    }
}
