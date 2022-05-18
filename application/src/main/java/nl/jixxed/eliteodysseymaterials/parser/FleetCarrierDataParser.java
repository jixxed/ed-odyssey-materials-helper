package nl.jixxed.eliteodysseymaterials.parser;

public class FleetCarrierDataParser extends DataParser {

    protected String getAmountField() {
        return "quantity";
    }

    protected String getNameField() {
        return "name";
    }
}
