package nl.jixxed.eliteodysseymaterials.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ImportResult {
    private final ResultType resultType;
    private String message;

    public enum ResultType {
        SUCCESS_ODYSSEY_WISHLIST, ERROR_ODYSSEY_WISHLIST, SUCCESS_HORIZONS_WISHLIST, ERROR_HORIZONS_WISHLIST, SUCCESS_LOADOUT, ERROR_LOADOUT, SUCCESS_EDSY_WISHLIST, ERROR_EDSY_WISHLIST, SUCCESS_CORIOLIS_WISHLIST, ERROR_CORIOLIS_WISHLIST, UNKNOWN_TYPE, OTHER_ERROR, CAPI_OAUTH_TOKEN
    }
}
