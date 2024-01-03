package nl.jixxed.eliteodysseymaterials.domain.ships;

public enum ModuleSize {
    SIZE_0("0"),
    SIZE_1("1"),
    SIZE_2("2"),
    SIZE_3("3"),
    SIZE_4("4"),
    SIZE_5("5"),
    SIZE_6("6"),
    SIZE_7("7"),
    SIZE_8("8");

    private final String displayName;

    ModuleSize(final String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public boolean isHigher(ModuleSize moduleSize) {
        return this.ordinal() > moduleSize.ordinal();
    }
    public boolean isLower(ModuleSize moduleSize) {
        return this.ordinal() < moduleSize.ordinal();
    }
    public boolean isLowerOrEqual(int size) {
        return this.ordinal() <= size;
    }

    public int intValue() {
        return ordinal();
    }
}
