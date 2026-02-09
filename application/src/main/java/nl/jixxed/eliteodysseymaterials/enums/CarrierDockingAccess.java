package nl.jixxed.eliteodysseymaterials.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum CarrierDockingAccess {

    ALL("all"),
    FRIENDS("friends"),
    SQUADRON_FRIENDS("squadronfriends"),
    SQUADRON("squadron"),
    NONE("none");
    private final String key;

    public static CarrierDockingAccess forKey(final String name) {
        return Arrays.stream(CarrierDockingAccess.values()).filter(carrierDockingAccess -> carrierDockingAccess.key.equalsIgnoreCase(name)).findFirst().orElse(CarrierDockingAccess.NONE);
    }


    public String getLocalizationKey() {
        return "carrier.access." + this.name().toLowerCase();
    }
}
