package nl.jixxed.eliteodysseymaterials.parser;

public class FleetCarrierAssetParser extends AssetParser {

    protected String getAmountField() {
        return "quantity";
    }

    protected String getNameField() {
        return "name";
    }
}
