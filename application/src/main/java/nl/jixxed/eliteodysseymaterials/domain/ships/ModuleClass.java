package nl.jixxed.eliteodysseymaterials.domain.ships;

public enum ModuleClass {
    I,
    H,
    G,
    F,
    E,
    D,
    C,
    B,
    A;
    public boolean isHigher(ModuleClass moduleClass) {
        return this.ordinal() > moduleClass.ordinal();
    }
    public boolean isLower(ModuleClass moduleClass) {
        return this.ordinal() < moduleClass.ordinal();
    }
}
