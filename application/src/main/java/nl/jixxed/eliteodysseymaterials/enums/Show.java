package nl.jixxed.eliteodysseymaterials.enums;

public enum Show {
    ALL("All (default)"),
    ALL_WITH_STOCK("All with stock"),
    ALL_ENGINEER_BLUEPRINT("All engineer unlocking + blueprint"),
    REQUIRED_ENGINEER_BLUEPRINT("Only required engineer unlocking + blueprint"),
    ALL_ENGINEER("All engineer unlocking"),
    REQUIRED_ENGINEER("Only required engineer unlocking"),
    BLUEPRINT("Blueprint"),
    IRRELEVANT("Irrelevant"),
    IRRELEVANT_WITH_STOCK("Irrelevant with stock"),
    FAVOURITES("Favourites");

    String name;

    Show(final String name) {
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
