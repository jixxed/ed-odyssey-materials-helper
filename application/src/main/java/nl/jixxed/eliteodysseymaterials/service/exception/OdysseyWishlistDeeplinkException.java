package nl.jixxed.eliteodysseymaterials.service.exception;

public class OdysseyWishlistDeeplinkException extends RuntimeException {
    public OdysseyWishlistDeeplinkException() {
    }

    public OdysseyWishlistDeeplinkException(final String message) {
        super(message);
    }

    public OdysseyWishlistDeeplinkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OdysseyWishlistDeeplinkException(final Throwable cause) {
        super(cause);
    }

    public OdysseyWishlistDeeplinkException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
