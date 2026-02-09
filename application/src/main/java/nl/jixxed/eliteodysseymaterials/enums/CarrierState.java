package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum CarrierState {
    NORMAL_OPERATION("normalOperation"),
    UNKNOWN("unknown$");

    private final String key;

    public static CarrierState forKey(final String name) {
        return Arrays.stream(CarrierState.values()).filter(carrierState -> carrierState.key.equalsIgnoreCase(name)).findFirst().orElse(CarrierState.UNKNOWN);
    }


    public String getLocalizationKey() {
        return "carrier.state." + this.name().toLowerCase();
    }
}
