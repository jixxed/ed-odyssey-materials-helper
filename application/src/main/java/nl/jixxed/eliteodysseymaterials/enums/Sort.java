package nl.jixxed.eliteodysseymaterials.enums;

public enum Sort {
    ENGINEER_BLUEPRINT_IRRELEVANT("Engineer unlocking, blueprint, irrelevant"),
    RELEVANT_IRRELEVANT("Relevant, irrelevant"),
    ALPHABETICAL("Alphabetical (default)");

    String name;

    Sort(final String name) {
        this.name = name;
    }

    public String friendlyName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
