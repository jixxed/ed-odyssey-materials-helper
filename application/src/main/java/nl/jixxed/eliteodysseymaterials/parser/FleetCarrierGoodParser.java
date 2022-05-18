package nl.jixxed.eliteodysseymaterials.parser;

public class FleetCarrierGoodParser extends GoodParser {

    protected String getAmountField() {
        return "quantity";
    }

    protected String getNameField() {
        return "name";
    }
}
