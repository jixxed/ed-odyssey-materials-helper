package nl.jixxed.eliteodysseymaterials.enums;

public enum MaterialTotalType {
    BLUEPRINT,
    POWERPLAY,
    IRRELEVANT,
    CHEMICAL,
    CIRCUIT,
    TECH,
    TOTAL,
    SUB_TOTAL;

    public String getLocalizationKey() {
        return "total.type.name." + this.name().toLowerCase();
    }
}
