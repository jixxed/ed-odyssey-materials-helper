package nl.jixxed.eliteodysseymaterials.domain.ships;

public enum ShipSize {
    S, M, L;

    public String getLocalizationKey() {
        return "ships.size." + this.name().toLowerCase();
    }
}
